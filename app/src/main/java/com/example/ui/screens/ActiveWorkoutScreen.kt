package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.FastForward
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.AmberGold
import com.example.ui.theme.AmberGoldLight
import com.example.ui.theme.CoralFlame
import com.example.ui.theme.CyanAccent
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DarkOutline
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceCard
import com.example.ui.theme.DarkSurfaceElevated
import com.example.ui.theme.EmeraldSuccess
import com.example.ui.viewmodel.ActiveWorkoutPhase
import com.example.ui.viewmodel.CalisthenicsViewModel

@Composable
fun ActiveWorkoutScreen(
    viewModel: CalisthenicsViewModel,
    onFinish: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showExitConfirmDialog by remember { mutableStateOf(false) }
    BackHandler { showExitConfirmDialog = true }

    val workoutState by viewModel.activeWorkoutState.collectAsState()
    val routine = workoutState.routine

    if (routine == null) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(DarkBackground),
            contentAlignment = Alignment.Center
        ) {
            Text("Nenhum treino ativo no momento", color = Color.White)
        }
        return
    }

    if (workoutState.phase == ActiveWorkoutPhase.COMPLETED) {
        WorkoutCompletionView(
            workoutName = routine.title,
            elapsedSeconds = workoutState.totalElapsedSeconds,
            totalSets = workoutState.completedSetsCount,
            onClose = {
                viewModel.exitActiveWorkout()
                onFinish()
            }
        )
        return
    }

    val currentExercise = routine.exercises.getOrNull(workoutState.currentExerciseIndex)

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBackground)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { showExitConfirmDialog = true },
                    modifier = Modifier.testTag("exit_workout_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Sair do treino",
                        tint = Color.White
                    )
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = routine.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    val elapsedMin = workoutState.totalElapsedSeconds / 60
                    val elapsedSec = workoutState.totalElapsedSeconds % 60
                    Text(
                        text = "%02d:%02d".format(elapsedMin, elapsedSec),
                        style = MaterialTheme.typography.labelMedium,
                        color = AmberGold
                    )
                }

                IconButton(
                    onClick = { viewModel.toggleWorkoutPause() },
                    modifier = Modifier.testTag("pause_resume_button")
                ) {
                    Icon(
                        imageVector = if (workoutState.isPaused) Icons.Default.PlayArrow else Icons.Default.Pause,
                        contentDescription = if (workoutState.isPaused) "Continuar" else "Pausar",
                        tint = if (workoutState.isPaused) EmeraldSuccess else AmberGold
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Exercise index header
            Text(
                text = "Exercício ${workoutState.currentExerciseIndex + 1} de ${routine.exercises.size}",
                style = MaterialTheme.typography.labelMedium,
                color = Color(0xFF94A3B8)
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (currentExercise != null) {
                // Exercise Title Card
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = DarkSurfaceCard),
                    border = androidx.compose.foundation.BorderStroke(1.dp, DarkOutline),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = CyanAccent.copy(alpha = 0.2f)
                        ) {
                            Text(
                                text = currentExercise.muscleGroup.displayName.uppercase(),
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = CyanAccent,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = currentExercise.exerciseName,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Black,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        // Sets pills indicator
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            for (setIdx in 1..currentExercise.sets) {
                                val isDone = setIdx < workoutState.currentSet
                                val isCurrent = setIdx == workoutState.currentSet
                                Surface(
                                    shape = RoundedCornerShape(12.dp),
                                    color = when {
                                        isDone -> EmeraldSuccess
                                        isCurrent -> AmberGold
                                        else -> DarkSurfaceElevated
                                    },
                                    modifier = Modifier.padding(2.dp)
                                ) {
                                    Text(
                                        text = "Série $setIdx",
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isDone || isCurrent) Color.Black else Color(0xFF94A3B8),
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Phase Mode: Active Exercise OR Resting
                AnimatedContent(
                    targetState = workoutState.phase,
                    label = "phase_animation"
                ) { phase ->
                    when (phase) {
                        ActiveWorkoutPhase.EXERCISE -> {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(20.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                if (currentExercise.isIsometric) {
                                    // Isometric Timer Circle
                                    Box(
                                        contentAlignment = Alignment.Center,
                                        modifier = Modifier.size(200.dp)
                                    ) {
                                        val totalIso = currentExercise.targetRepsOrSeconds.coerceAtLeast(1)
                                        val progress = (workoutState.secondsLeftInPhase.toFloat() / totalIso).coerceIn(0f, 1f)
                                        CircularProgressIndicator(
                                            progress = { progress },
                                            modifier = Modifier.fillMaxSize(),
                                            color = AmberGold,
                                            trackColor = DarkOutline,
                                            strokeWidth = 12.dp
                                        )
                                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                            Text(
                                                text = "${workoutState.secondsLeftInPhase}s",
                                                style = MaterialTheme.typography.headlineLarge,
                                                fontWeight = FontWeight.Black,
                                                color = Color.White
                                            )
                                            Text(
                                                text = "ISOMETRIA",
                                                style = MaterialTheme.typography.labelSmall,
                                                color = AmberGoldLight
                                            )
                                        }
                                    }
                                } else {
                                    // Repetitions Counter Display
                                    Box(
                                        contentAlignment = Alignment.Center,
                                        modifier = Modifier
                                            .size(200.dp)
                                            .clip(CircleShape)
                                            .background(DarkSurfaceElevated)
                                            .border(3.dp, AmberGold, CircleShape)
                                    ) {
                                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                            Text(
                                                text = "${currentExercise.targetRepsOrSeconds}",
                                                style = MaterialTheme.typography.headlineLarge.copy(fontSize = 54.sp),
                                                fontWeight = FontWeight.Black,
                                                color = AmberGold
                                            )
                                            Text(
                                                text = "REPETIÇÕES",
                                                style = MaterialTheme.typography.labelSmall,
                                                color = Color(0xFFCBD5E1),
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                    }
                                }

                                Button(
                                    onClick = { viewModel.completeCurrentSetManually() },
                                    colors = ButtonDefaults.buttonColors(containerColor = AmberGold),
                                    shape = RoundedCornerShape(16.dp),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(56.dp)
                                        .testTag("complete_set_button")
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = "Concluir Série",
                                        tint = Color.Black
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "CONCLUIR SÉRIE",
                                        color = Color.Black,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }

                        ActiveWorkoutPhase.RESTING -> {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(16.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Surface(
                                    shape = RoundedCornerShape(12.dp),
                                    color = CyanAccent.copy(alpha = 0.15f)
                                ) {
                                    Text(
                                        text = "DESCANSO",
                                        color = CyanAccent,
                                        style = MaterialTheme.typography.labelLarge,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                                    )
                                }

                                // Rest Countdown Circle
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier.size(190.dp)
                                ) {
                                    val totalRest = workoutState.targetPhaseSeconds.coerceAtLeast(1)
                                    val progress = (workoutState.secondsLeftInPhase.toFloat() / totalRest).coerceIn(0f, 1f)
                                    CircularProgressIndicator(
                                        progress = { progress },
                                        modifier = Modifier.fillMaxSize(),
                                        color = CyanAccent,
                                        trackColor = DarkOutline,
                                        strokeWidth = 10.dp
                                    )
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        val restMin = workoutState.secondsLeftInPhase / 60
                                        val restSec = workoutState.secondsLeftInPhase % 60
                                        Text(
                                            text = "%02d:%02d".format(restMin, restSec),
                                            style = MaterialTheme.typography.headlineLarge,
                                            fontWeight = FontWeight.Black,
                                            color = Color.White
                                        )
                                        Text(
                                            text = "Respire fundo",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = Color(0xFF94A3B8)
                                        )
                                    }
                                }

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    OutlinedButton(
                                        onClick = { viewModel.addRestSeconds(30) },
                                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                                        border = androidx.compose.foundation.BorderStroke(1.dp, DarkOutline),
                                        shape = RoundedCornerShape(12.dp),
                                        modifier = Modifier
                                            .weight(1f)
                                            .height(48.dp)
                                    ) {
                                        Icon(imageVector = Icons.Default.Add, contentDescription = "+30s")
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("+30s")
                                    }

                                    Button(
                                        onClick = { viewModel.skipRest() },
                                        colors = ButtonDefaults.buttonColors(containerColor = CyanAccent),
                                        shape = RoundedCornerShape(12.dp),
                                        modifier = Modifier
                                            .weight(1f)
                                            .height(48.dp)
                                            .testTag("skip_rest_button")
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.FastForward,
                                            contentDescription = "Pular Descanso",
                                            tint = Color.Black
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("Pular", color = Color.Black, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                        }

                        ActiveWorkoutPhase.COMPLETED -> Unit
                    }
                }
            }
        }
    }

    if (showExitConfirmDialog) {
        AlertDialog(
            onDismissRequest = { showExitConfirmDialog = false },
            containerColor = DarkSurface,
            title = {
                Text(
                    text = "Encerrar Treino?",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    text = "Se sair agora, o progresso desta sessão não será totalmente salvo. Deseja mesmo sair?",
                    color = Color(0xFFCBD5E1)
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        showExitConfirmDialog = false
                        viewModel.exitActiveWorkout()
                        onFinish()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = CoralFlame)
                ) {
                    Text("Sair do Treino", color = Color.White, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showExitConfirmDialog = false }) {
                    Text("Continuar Treinando", color = AmberGold)
                }
            }
        )
    }
}

@Composable
private fun WorkoutCompletionView(
    workoutName: String,
    elapsedSeconds: Int,
    totalSets: Int,
    onClose: () -> Unit
) {
    val minutes = elapsedSeconds / 60
    val seconds = elapsedSeconds % 60
    val calories = ((elapsedSeconds / 60.0) * 8.5).toInt().coerceAtLeast(15)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = DarkSurfaceCard),
            border = androidx.compose.foundation.BorderStroke(1.5.dp, AmberGold),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Surface(
                    shape = CircleShape,
                    color = AmberGold.copy(alpha = 0.2f),
                    modifier = Modifier.size(80.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.EmojiEvents,
                            contentDescription = "Vitória",
                            tint = AmberGold,
                            modifier = Modifier.size(48.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "TREINO CONCLUÍDO!",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Black,
                    color = AmberGold
                )
                Text(
                    text = workoutName,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "%02d:%02d".format(minutes, seconds),
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(text = "Tempo Total", style = MaterialTheme.typography.bodySmall, color = Color(0xFF94A3B8))
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "$totalSets",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(text = "Séries Feitas", style = MaterialTheme.typography.bodySmall, color = Color(0xFF94A3B8))
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "$calories kcal",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = CoralFlame
                        )
                        Text(text = "Gasto Calórico", style = MaterialTheme.typography.bodySmall, color = Color(0xFF94A3B8))
                    }
                }

                Spacer(modifier = Modifier.height(28.dp))

                Button(
                    onClick = onClose,
                    colors = ButtonDefaults.buttonColors(containerColor = AmberGold),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag("finish_and_save_button")
                ) {
                    Text(
                        text = "Salvar e Concluir",
                        color = Color.Black,
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
