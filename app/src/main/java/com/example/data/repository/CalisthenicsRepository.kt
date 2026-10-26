package com.example.data.repository

import com.example.data.local.CustomWorkoutDao
import com.example.data.local.CustomWorkoutEntity
import com.example.data.local.PersonalRecordDao
import com.example.data.local.PersonalRecordEntity
import com.example.data.local.SkillProgressDao
import com.example.data.local.SkillProgressEntity
import com.example.data.local.WorkoutRecordDao
import com.example.data.local.WorkoutRecordEntity
import com.example.data.model.Difficulty
import com.example.data.model.Equipment
import com.example.data.model.Exercise
import com.example.data.model.MuscleGroup
import com.example.data.model.PersonalRecordItem
import com.example.data.model.SkillTree
import com.example.data.model.WorkoutCategory
import com.example.data.model.WorkoutExercise
import com.example.data.model.WorkoutRoutine
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CalisthenicsRepository(
    private val workoutRecordDao: WorkoutRecordDao,
    private val skillProgressDao: SkillProgressDao,
    private val personalRecordDao: PersonalRecordDao,
    private val customWorkoutDao: CustomWorkoutDao
) {
    // --- EXERCISES & SKILLS ---
    fun getAllExercises(): List<Exercise> = ExerciseCatalog.exercises
    fun getExerciseById(id: String): Exercise? = ExerciseCatalog.getById(id)

    fun getAllSkills(): List<SkillTree> = SkillCatalog.skills
    fun getSkillById(id: String): SkillTree? = SkillCatalog.getById(id)

    // --- SKILL PROGRESS (ROOM) ---
    val skillProgressFlow: Flow<List<SkillProgressEntity>> = skillProgressDao.getAllSkillProgress()

    suspend fun updateSkillProgress(skillId: String, stepIndex: Int, isMastered: Boolean = false, personalBest: String = "") {
        skillProgressDao.saveSkillProgress(
            SkillProgressEntity(
                skillId = skillId,
                currentStepIndex = stepIndex,
                isMastered = isMastered,
                personalBestRecord = personalBest,
                lastUpdatedTimestamp = System.currentTimeMillis()
            )
        )
    }

    // --- WORKOUT HISTORY (ROOM) ---
    val workoutHistoryFlow: Flow<List<WorkoutRecordEntity>> = workoutRecordDao.getAllHistory()
    val totalWorkoutsCountFlow: Flow<Int> = workoutRecordDao.getTotalWorkoutsCount()
    val totalWorkoutSecondsFlow: Flow<Int?> = workoutRecordDao.getTotalWorkoutSeconds()
    val totalCaloriesBurnedFlow: Flow<Int?> = workoutRecordDao.getTotalCaloriesBurned()

    suspend fun recordCompletedWorkout(
        workoutName: String,
        durationSeconds: Int,
        completedExercisesCount: Int,
        totalSetsCount: Int,
        notes: String = ""
    ): Long {
        // Calisthenics calorie estimation: ~8-10 kcal per minute of active bodyweight training
        val calculatedCalories = ((durationSeconds / 60.0) * 8.5).toInt().coerceAtLeast(15)
        val entity = WorkoutRecordEntity(
            workoutName = workoutName,
            durationSeconds = durationSeconds,
            completedExercisesCount = completedExercisesCount,
            totalSetsCount = totalSetsCount,
            caloriesBurned = calculatedCalories,
            timestampMillis = System.currentTimeMillis(),
            notes = notes
        )
        return workoutRecordDao.insertWorkout(entity)
    }

    // --- PERSONAL RECORDS (ROOM + BENCHMARKS) ---
    val standardPRTemplates = listOf(
        PersonalRecordItem(
            id = "pr_pullups",
            title = "Barra Fixa Estrita (Pull-ups)",
            currentValue = "12",
            unit = "reps",
            standardBenchmarkBeginner = "3-5",
            standardBenchmarkIntermediate = "10-15",
            standardBenchmarkAdvanced = "18-25",
            standardBenchmarkElite = "30+"
        ),
        PersonalRecordItem(
            id = "pr_dips",
            title = "Mergulho em Paralelas (Dips)",
            currentValue = "18",
            unit = "reps",
            standardBenchmarkBeginner = "5-8",
            standardBenchmarkIntermediate = "15-20",
            standardBenchmarkAdvanced = "25-35",
            standardBenchmarkElite = "40+"
        ),
        PersonalRecordItem(
            id = "pr_pushups",
            title = "Flexões de Braço (Push-ups)",
            currentValue = "30",
            unit = "reps",
            standardBenchmarkBeginner = "15-20",
            standardBenchmarkIntermediate = "30-40",
            standardBenchmarkAdvanced = "50-70",
            standardBenchmarkElite = "80+"
        ),
        PersonalRecordItem(
            id = "pr_muscleups",
            title = "Muscle-Ups Consecutivos",
            currentValue = "3",
            unit = "reps",
            standardBenchmarkBeginner = "0",
            standardBenchmarkIntermediate = "1-3",
            standardBenchmarkAdvanced = "6-10",
            standardBenchmarkElite = "15+"
        ),
        PersonalRecordItem(
            id = "pr_lsit",
            title = "L-Sit Isométrico no Chão",
            currentValue = "15",
            unit = "segundos",
            standardBenchmarkBeginner = "5s",
            standardBenchmarkIntermediate = "15-20s",
            standardBenchmarkAdvanced = "30-45s",
            standardBenchmarkElite = "60s+"
        ),
        PersonalRecordItem(
            id = "pr_handstand",
            title = "Parada de Mão Livre (Handstand)",
            currentValue = "20",
            unit = "segundos",
            standardBenchmarkBeginner = "5s",
            standardBenchmarkIntermediate = "15-25s",
            standardBenchmarkAdvanced = "45-60s",
            standardBenchmarkElite = "90s+"
        ),
        PersonalRecordItem(
            id = "pr_pistol",
            title = "Pistol Squats (Por Perna)",
            currentValue = "8",
            unit = "reps",
            standardBenchmarkBeginner = "1-2",
            standardBenchmarkIntermediate = "6-10",
            standardBenchmarkAdvanced = "12-18",
            standardBenchmarkElite = "25+"
        )
    )

    val personalRecordsFlow: Flow<List<PersonalRecordItem>> = personalRecordDao.getAllRecords().map { entities ->
        val recordMap = entities.associateBy { it.recordId }
        standardPRTemplates.map { template ->
            val saved = recordMap[template.id]
            if (saved != null) {
                template.copy(currentValue = saved.value)
            } else {
                template
            }
        }
    }

    suspend fun savePersonalRecord(recordId: String, value: String) {
        personalRecordDao.saveRecord(
            PersonalRecordEntity(
                recordId = recordId,
                value = value,
                lastUpdatedTimestamp = System.currentTimeMillis()
            )
        )
    }

    // --- WORKOUT ROUTINES (PREDEFINED + CUSTOM FROM ROOM) ---
    val allRoutinesFlow: Flow<List<WorkoutRoutine>> = customWorkoutDao.getAllCustomWorkouts().map { customEntities ->
        val customRoutines = customEntities.map { entity ->
            val parsedExercises = parseExercisesFromJson(entity.exercisesJson)
            WorkoutRoutine(
                id = entity.id,
                title = entity.title,
                subtitle = entity.subtitle,
                category = WorkoutCategory.PERSONALIZADO,
                difficulty = try { Difficulty.valueOf(entity.difficulty) } catch (e: Exception) { Difficulty.INTERMEDIARIO },
                estimatedDurationMinutes = entity.estimatedMinutes,
                equipmentRequired = entity.equipmentCsv.split(",")
                    .filter { it.isNotBlank() }
                    .mapNotNull {
                        try { Equipment.valueOf(it.trim()) } catch (e: Exception) { null }
                    },
                description = entity.description,
                exercises = parsedExercises,
                isCustom = true
            )
        }
        WorkoutCatalog.predefinedRoutines + customRoutines
    }

    suspend fun createCustomWorkout(
        title: String,
        subtitle: String,
        difficulty: Difficulty,
        estimatedMinutes: Int,
        description: String,
        exercises: List<WorkoutExercise>
    ) {
        val serializedExercises = serializeExercisesToJson(exercises)
        val entity = CustomWorkoutEntity(
            id = "custom_${System.currentTimeMillis()}",
            title = title,
            subtitle = subtitle,
            category = WorkoutCategory.PERSONALIZADO.name,
            difficulty = difficulty.name,
            estimatedMinutes = estimatedMinutes,
            equipmentCsv = Equipment.NENHUM.name,
            description = description,
            exercisesJson = serializedExercises
        )
        customWorkoutDao.insertCustomWorkout(entity)
    }

    suspend fun deleteCustomWorkout(workoutId: String) {
        customWorkoutDao.deleteCustomWorkout(workoutId)
    }

    private fun serializeExercisesToJson(exercises: List<WorkoutExercise>): String {
        // Lightweight serial format: id|name|muscle|sets|reps|rest|isIso;...
        return exercises.joinToString(";") {
            "${it.exerciseId}|${it.exerciseName}|${it.muscleGroup.name}|${it.sets}|${it.targetRepsOrSeconds}|${it.restSeconds}|${it.isIsometric}"
        }
    }

    private fun parseExercisesFromJson(data: String): List<WorkoutExercise> {
        if (data.isBlank()) return emptyList()
        return try {
            data.split(";").filter { it.isNotBlank() }.map { part ->
                val tokens = part.split("|")
                WorkoutExercise(
                    exerciseId = tokens[0],
                    exerciseName = tokens.getOrElse(1) { "Exercício" },
                    muscleGroup = try { MuscleGroup.valueOf(tokens.getOrElse(2) { "CORPO_TODO" }) } catch (e: Exception) { MuscleGroup.CORPO_TODO },
                    sets = tokens.getOrElse(3) { "3" }.toIntOrNull() ?: 3,
                    targetRepsOrSeconds = tokens.getOrElse(4) { "10" }.toIntOrNull() ?: 10,
                    restSeconds = tokens.getOrElse(5) { "60" }.toIntOrNull() ?: 60,
                    isIsometric = tokens.getOrElse(6) { "false" }.toBoolean()
                )
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}
