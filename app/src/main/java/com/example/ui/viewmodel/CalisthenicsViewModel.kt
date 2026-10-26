package com.example.ui.viewmodel

import android.app.Application
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.CalisthenicsDatabase
import com.example.data.local.SkillProgressEntity
import com.example.data.local.WorkoutRecordEntity
import com.example.data.model.Difficulty
import com.example.data.model.Exercise
import com.example.data.model.MuscleGroup
import com.example.data.model.PersonalRecordItem
import com.example.data.model.SkillTree
import com.example.data.model.WorkoutCategory
import com.example.data.model.WorkoutExercise
import com.example.data.model.WorkoutRoutine
import com.example.data.repository.CalisthenicsRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed class ScreenDestination {
    object Home : ScreenDestination()
    object Workouts : ScreenDestination()
    object ActiveWorkout : ScreenDestination()
    object Skills : ScreenDestination()
    object Exercises : ScreenDestination()
    object Timers : ScreenDestination()
    object Profile : ScreenDestination()
}

enum class ActiveWorkoutPhase {
    EXERCISE,
    RESTING,
    COMPLETED
}

data class ActiveWorkoutState(
    val routine: WorkoutRoutine? = null,
    val currentExerciseIndex: Int = 0,
    val currentSet: Int = 1,
    val phase: ActiveWorkoutPhase = ActiveWorkoutPhase.EXERCISE,
    val secondsLeftInPhase: Int = 0,
    val targetPhaseSeconds: Int = 0,
    val isPaused: Boolean = false,
    val totalElapsedSeconds: Int = 0,
    val completedSetsCount: Int = 0
)

enum class StandaloneTimerMode {
    TABATA,
    EMOM,
    AMRAP,
    CRONOMETRO
}

enum class StandaloneTimerPhase {
    PREPARAR,
    WORK,
    REST,
    FINISHED
}

data class StandaloneTimerState(
    val mode: StandaloneTimerMode = StandaloneTimerMode.TABATA,
    val isRunning: Boolean = false,
    val isPaused: Boolean = false,
    val phase: StandaloneTimerPhase = StandaloneTimerPhase.PREPARAR,
    val currentRound: Int = 1,
    val totalRounds: Int = 8,
    val workSeconds: Int = 20,
    val restSeconds: Int = 10,
    val secondsLeft: Int = 5,
    val elapsedTotalSeconds: Int = 0
)

class CalisthenicsViewModel(application: Application) : AndroidViewModel(application) {

    private val db = CalisthenicsDatabase.getDatabase(application)
    private val repository = CalisthenicsRepository(
        workoutRecordDao = db.workoutRecordDao(),
        skillProgressDao = db.skillProgressDao(),
        personalRecordDao = db.personalRecordDao(),
        customWorkoutDao = db.customWorkoutDao()
    )

    // Navigation BackStack
    private val _currentScreen = MutableStateFlow<ScreenDestination>(ScreenDestination.Home)
    val currentScreen: StateFlow<ScreenDestination> = _currentScreen.asStateFlow()

    private val screenBackStack = mutableListOf<ScreenDestination>(ScreenDestination.Home)

    fun navigateTo(destination: ScreenDestination) {
        if (_currentScreen.value != destination) {
            screenBackStack.add(destination)
            _currentScreen.value = destination
        }
    }

    fun handleBackPress(): Boolean {
        if (screenBackStack.size > 1) {
            screenBackStack.removeAt(screenBackStack.size - 1)
            _currentScreen.value = screenBackStack.last()
            return true
        } else if (_currentScreen.value != ScreenDestination.Home) {
            _currentScreen.value = ScreenDestination.Home
            screenBackStack.clear()
            screenBackStack.add(ScreenDestination.Home)
            return true
        }
        return false
    }

    // Exercises & Skills
    val allExercises: List<Exercise> = repository.getAllExercises()
    val allSkills: List<SkillTree> = repository.getAllSkills()

    // Exercise Library Filtering State
    val exerciseSearchQuery = MutableStateFlow("")
    val selectedMuscleFilter = MutableStateFlow<MuscleGroup?>(null)
    val selectedDifficultyFilter = MutableStateFlow<Difficulty?>(null)

    // Room Flows
    val workoutHistory: StateFlow<List<WorkoutRecordEntity>> = repository.workoutHistoryFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val totalWorkoutsCount: StateFlow<Int> = repository.totalWorkoutsCountFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val totalWorkoutSeconds: StateFlow<Int?> = repository.totalWorkoutSecondsFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val totalCaloriesBurned: StateFlow<Int?> = repository.totalCaloriesBurnedFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val skillProgressList: StateFlow<List<SkillProgressEntity>> = repository.skillProgressFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val personalRecords: StateFlow<List<PersonalRecordItem>> = repository.personalRecordsFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), repository.standardPRTemplates)

    val allRoutines: StateFlow<List<WorkoutRoutine>> = repository.allRoutinesFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Active Workout State & Coroutine Job
    private val _activeWorkoutState = MutableStateFlow(ActiveWorkoutState())
    val activeWorkoutState: StateFlow<ActiveWorkoutState> = _activeWorkoutState.asStateFlow()

    private var workoutTickerJob: Job? = null

    fun startWorkout(routine: WorkoutRoutine) {
        val firstExercise = routine.exercises.firstOrNull()
        val initialSeconds = if (firstExercise?.isIsometric == true) firstExercise.targetRepsOrSeconds else 0

        _activeWorkoutState.value = ActiveWorkoutState(
            routine = routine,
            currentExerciseIndex = 0,
            currentSet = 1,
            phase = ActiveWorkoutPhase.EXERCISE,
            secondsLeftInPhase = initialSeconds,
            targetPhaseSeconds = initialSeconds,
            isPaused = false,
            totalElapsedSeconds = 0,
            completedSetsCount = 0
        )
        navigateTo(ScreenDestination.ActiveWorkout)
        startWorkoutTicker()
    }

    private fun startWorkoutTicker() {
        workoutTickerJob?.cancel()
        workoutTickerJob = viewModelScope.launch {
            while (true) {
                delay(1000)
                val current = _activeWorkoutState.value
                if (current.routine == null || current.isPaused || current.phase == ActiveWorkoutPhase.COMPLETED) {
                    continue
                }

                val newTotalElapsed = current.totalElapsedSeconds + 1

                when (current.phase) {
                    ActiveWorkoutPhase.EXERCISE -> {
                        val exercise = current.routine.exercises.getOrNull(current.currentExerciseIndex)
                        if (exercise?.isIsometric == true && current.secondsLeftInPhase > 0) {
                            val newSeconds = current.secondsLeftInPhase - 1
                            if (newSeconds <= 0) {
                                vibrateNotice()
                                advanceSetOrRest()
                            } else {
                                _activeWorkoutState.update {
                                    it.copy(
                                        secondsLeftInPhase = newSeconds,
                                        totalElapsedSeconds = newTotalElapsed
                                    )
                                }
                            }
                        } else {
                            _activeWorkoutState.update { it.copy(totalElapsedSeconds = newTotalElapsed) }
                        }
                    }
                    ActiveWorkoutPhase.RESTING -> {
                        val newSeconds = current.secondsLeftInPhase - 1
                        if (newSeconds <= 0) {
                            vibrateNotice()
                            startNextSetAfterRest()
                        } else {
                            _activeWorkoutState.update {
                                it.copy(
                                    secondsLeftInPhase = newSeconds,
                                    totalElapsedSeconds = newTotalElapsed
                                )
                            }
                        }
                    }
                    ActiveWorkoutPhase.COMPLETED -> Unit
                }
            }
        }
    }

    fun completeCurrentSetManually() {
        advanceSetOrRest()
    }

    private fun advanceSetOrRest() {
        val current = _activeWorkoutState.value
        val routine = current.routine ?: return
        val exercise = routine.exercises.getOrNull(current.currentExerciseIndex) ?: return

        val isLastSetOfExercise = current.currentSet >= exercise.sets
        val isLastExerciseOfRoutine = current.currentExerciseIndex >= routine.exercises.size - 1

        if (isLastSetOfExercise && isLastExerciseOfRoutine) {
            // Entire workout finished!
            finishWorkout()
        } else {
            // Enter rest period
            val restTime = exercise.restSeconds.coerceAtLeast(15)
            _activeWorkoutState.update {
                it.copy(
                    phase = ActiveWorkoutPhase.RESTING,
                    secondsLeftInPhase = restTime,
                    targetPhaseSeconds = restTime,
                    completedSetsCount = it.completedSetsCount + 1
                )
            }
        }
    }

    fun skipRest() {
        startNextSetAfterRest()
    }

    fun addRestSeconds(seconds: Int = 30) {
        _activeWorkoutState.update {
            it.copy(
                secondsLeftInPhase = it.secondsLeftInPhase + seconds,
                targetPhaseSeconds = it.targetPhaseSeconds + seconds
            )
        }
    }

    private fun startNextSetAfterRest() {
        val current = _activeWorkoutState.value
        val routine = current.routine ?: return
        val currentEx = routine.exercises.getOrNull(current.currentExerciseIndex) ?: return

        if (current.currentSet < currentEx.sets) {
            // Next set of same exercise
            val nextSet = current.currentSet + 1
            val isoSeconds = if (currentEx.isIsometric) currentEx.targetRepsOrSeconds else 0
            _activeWorkoutState.update {
                it.copy(
                    currentSet = nextSet,
                    phase = ActiveWorkoutPhase.EXERCISE,
                    secondsLeftInPhase = isoSeconds,
                    targetPhaseSeconds = isoSeconds
                )
            }
        } else {
            // Move to next exercise
            val nextExIndex = current.currentExerciseIndex + 1
            val nextEx = routine.exercises.getOrNull(nextExIndex)
            val isoSeconds = if (nextEx?.isIsometric == true) nextEx.targetRepsOrSeconds else 0
            _activeWorkoutState.update {
                it.copy(
                    currentExerciseIndex = nextExIndex,
                    currentSet = 1,
                    phase = ActiveWorkoutPhase.EXERCISE,
                    secondsLeftInPhase = isoSeconds,
                    targetPhaseSeconds = isoSeconds
                )
            }
        }
    }

    fun toggleWorkoutPause() {
        _activeWorkoutState.update { it.copy(isPaused = !it.isPaused) }
    }

    fun finishWorkout() {
        val current = _activeWorkoutState.value
        val routine = current.routine ?: return

        _activeWorkoutState.update { it.copy(phase = ActiveWorkoutPhase.COMPLETED) }
        viewModelScope.launch {
            repository.recordCompletedWorkout(
                workoutName = routine.title,
                durationSeconds = current.totalElapsedSeconds,
                completedExercisesCount = current.currentExerciseIndex + 1,
                totalSetsCount = current.completedSetsCount + 1
            )
        }
    }

    fun exitActiveWorkout() {
        workoutTickerJob?.cancel()
        _activeWorkoutState.value = ActiveWorkoutState()
        navigateTo(ScreenDestination.Home)
    }

    // --- SKILLS PROGRESS ---
    fun updateSkillLevel(skillId: String, stepIndex: Int, isMastered: Boolean = false) {
        viewModelScope.launch {
            repository.updateSkillProgress(
                skillId = skillId,
                stepIndex = stepIndex,
                isMastered = isMastered
            )
        }
    }

    // --- PERSONAL RECORDS ---
    fun updatePersonalRecord(recordId: String, newValue: String) {
        viewModelScope.launch {
            repository.savePersonalRecord(recordId, newValue.trim())
        }
    }

    // --- CUSTOM WORKOUT CREATION ---
    fun saveCustomWorkout(
        title: String,
        subtitle: String,
        difficulty: Difficulty,
        estimatedMinutes: Int,
        description: String,
        exercises: List<WorkoutExercise>
    ) {
        viewModelScope.launch {
            repository.createCustomWorkout(
                title = title.ifBlank { "Treino Personalizado" },
                subtitle = subtitle.ifBlank { "Rotina criada pelo atleta" },
                difficulty = difficulty,
                estimatedMinutes = estimatedMinutes.coerceAtLeast(10),
                description = description,
                exercises = exercises
            )
        }
    }

    fun deleteCustomWorkout(id: String) {
        viewModelScope.launch {
            repository.deleteCustomWorkout(id)
        }
    }

    // --- MULTIFUNCTIONAL TIMERS ---
    private val _timerState = MutableStateFlow(StandaloneTimerState())
    val timerState: StateFlow<StandaloneTimerState> = _timerState.asStateFlow()
    private var standaloneTimerJob: Job? = null

    fun setTimerMode(mode: StandaloneTimerMode) {
        standaloneTimerJob?.cancel()
        val defaultState = when (mode) {
            StandaloneTimerMode.TABATA -> StandaloneTimerState(
                mode = mode,
                workSeconds = 20,
                restSeconds = 10,
                totalRounds = 8,
                secondsLeft = 5, // 5s prep
                phase = StandaloneTimerPhase.PREPARAR
            )
            StandaloneTimerMode.EMOM -> StandaloneTimerState(
                mode = mode,
                workSeconds = 60,
                restSeconds = 0,
                totalRounds = 10,
                secondsLeft = 5,
                phase = StandaloneTimerPhase.PREPARAR
            )
            StandaloneTimerMode.AMRAP -> StandaloneTimerState(
                mode = mode,
                workSeconds = 12 * 60, // 12 min
                restSeconds = 0,
                totalRounds = 1,
                secondsLeft = 5,
                phase = StandaloneTimerPhase.PREPARAR
            )
            StandaloneTimerMode.CRONOMETRO -> StandaloneTimerState(
                mode = mode,
                secondsLeft = 0,
                phase = StandaloneTimerPhase.WORK
            )
        }
        _timerState.value = defaultState
    }

    fun startPauseTimer() {
        val current = _timerState.value
        if (current.isRunning) {
            _timerState.update { it.copy(isRunning = false, isPaused = true) }
        } else {
            _timerState.update { it.copy(isRunning = true, isPaused = false) }
            runStandaloneTimerLoop()
        }
    }

    fun resetTimer() {
        standaloneTimerJob?.cancel()
        setTimerMode(_timerState.value.mode)
    }

    private fun runStandaloneTimerLoop() {
        standaloneTimerJob?.cancel()
        standaloneTimerJob = viewModelScope.launch {
            while (true) {
                delay(1000)
                val current = _timerState.value
                if (!current.isRunning || current.phase == StandaloneTimerPhase.FINISHED) break

                if (current.mode == StandaloneTimerMode.CRONOMETRO) {
                    _timerState.update {
                        it.copy(
                            secondsLeft = it.secondsLeft + 1,
                            elapsedTotalSeconds = it.elapsedTotalSeconds + 1
                        )
                    }
                    continue
                }

                val newSec = current.secondsLeft - 1
                val newElapsed = current.elapsedTotalSeconds + 1

                if (newSec <= 0) {
                    vibrateNotice()
                    when (current.phase) {
                        StandaloneTimerPhase.PREPARAR -> {
                            _timerState.update {
                                it.copy(
                                    phase = StandaloneTimerPhase.WORK,
                                    secondsLeft = it.workSeconds,
                                    elapsedTotalSeconds = newElapsed
                                )
                            }
                        }
                        StandaloneTimerPhase.WORK -> {
                            if (current.mode == StandaloneTimerMode.AMRAP) {
                                _timerState.update {
                                    it.copy(
                                        phase = StandaloneTimerPhase.FINISHED,
                                        isRunning = false,
                                        secondsLeft = 0
                                    )
                                }
                            } else if (current.restSeconds > 0) {
                                _timerState.update {
                                    it.copy(
                                        phase = StandaloneTimerPhase.REST,
                                        secondsLeft = it.restSeconds,
                                        elapsedTotalSeconds = newElapsed
                                    )
                                }
                            } else {
                                advanceTimerRound(newElapsed)
                            }
                        }
                        StandaloneTimerPhase.REST -> {
                            advanceTimerRound(newElapsed)
                        }
                        StandaloneTimerPhase.FINISHED -> break
                    }
                } else {
                    _timerState.update {
                        it.copy(secondsLeft = newSec, elapsedTotalSeconds = newElapsed)
                    }
                }
            }
        }
    }

    private fun advanceTimerRound(elapsed: Int) {
        val current = _timerState.value
        if (current.currentRound >= current.totalRounds) {
            _timerState.update {
                it.copy(
                    phase = StandaloneTimerPhase.FINISHED,
                    isRunning = false,
                    secondsLeft = 0,
                    elapsedTotalSeconds = elapsed
                )
            }
        } else {
            _timerState.update {
                it.copy(
                    currentRound = it.currentRound + 1,
                    phase = StandaloneTimerPhase.WORK,
                    secondsLeft = it.workSeconds,
                    elapsedTotalSeconds = elapsed
                )
            }
        }
    }

    private fun vibrateNotice() {
        try {
            val context = getApplication<Application>()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager
                vibratorManager?.defaultVibrator?.vibrate(
                    VibrationEffect.createOneShot(350, VibrationEffect.DEFAULT_AMPLITUDE)
                )
            } else {
                @Suppress("DEPRECATION")
                val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
                @Suppress("DEPRECATION")
                vibrator?.vibrate(350)
            }
        } catch (e: Exception) {
            // Safe fallback if vibration hardware unavailable
        }
    }
}
