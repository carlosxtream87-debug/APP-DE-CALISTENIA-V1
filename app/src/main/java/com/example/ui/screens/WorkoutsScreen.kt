package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Difficulty
import com.example.data.model.WorkoutCategory
import com.example.data.model.WorkoutExercise
import com.example.data.model.WorkoutRoutine
import com.example.ui.theme.AmberGold
import com.example.ui.theme.AmberGoldLight
import com.example.ui.theme.CoralFlame
import com.example.ui.theme.CyanAccent
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DarkOutline
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceCard
import com.example.ui.theme.EmeraldSuccess
import com.example.ui.viewmodel.CalisthenicsViewModel

@Composable
fun WorkoutsScreen(
    viewModel: CalisthenicsViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler { onBack() }

    val routines by viewModel.allRoutines.collectAsState()
    var selectedCategoryFilter by remember { mutableStateOf<WorkoutCategory?>(null) }
    var selectedRoutineForPreview by remember { mutableStateOf<WorkoutRoutine?>(null) }
    var showCreateDialog by remember { mutableStateOf(false) }

    val categories = listOf(
        null to "Todos",
        WorkoutCategory.CORPO_TODO to "Full Body",
        WorkoutCategory.SUPERIOR_PUSH to "Push",
        WorkoutCategory.SUPERIOR_PULL to "Pull",
        WorkoutCategory.INFERIOR_LEGS to "Pernas",
        WorkoutCategory.CORE_ABS to "Core",
        WorkoutCategory.HABILIDADES to "Skills",
        WorkoutCategory.PERSONALIZADO to "Meus Treinos"
    )

    val filteredRoutines = routines.filter { routine ->
        if (selectedCategoryFilter == null) true
        else routine.category == selectedCategoryFilter
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBackground)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(top = 16.dp, bottom = 100.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Treinos Calistênicos",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "Rotinas estruturadas por objetivo e nível",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF94A3B8)
                        )
                    }

                    Button(
                        onClick = { showCreateDialog = true },
                        colors = ButtonDefaults.buttonColors(containerColor = AmberGold),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.testTag("create_custom_workout_button"),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Criar Treino",
                            tint = Color.Black,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Criar",
                            color = Color.Black,
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                }
            }

            // Categories Filter Tabs
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(vertical = 4.dp)
                ) {
                    items(categories) { (category, label) ->
                        val isSelected = selectedCategoryFilter == category
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = if (isSelected) AmberGold else DarkSurfaceCard,
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp,
                                if (isSelected) AmberGold else DarkOutline
                            ),
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .clickable { selectedCategoryFilter = category }
                        ) {
                            Text(
                                text = label,
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                color = if (isSelected) Color.Black else Color.White,
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                            )
                        }
                    }
                }
            }

            if (filteredRoutines.isEmpty()) {
                item {
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = DarkSurfaceCard),
                        border = androidx.compose.foundation.BorderStroke(1.dp, DarkOutline),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 24.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Default.FitnessCenter,
                                contentDescription = "Sem treinos",
                                tint = AmberGold,
                                modifier = Modifier.size(40.dp)
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = "Nenhum treino nesta categoria",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "Toque em 'Criar' no topo para montar seu treino exclusivo!",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFF94A3B8)
                            )
                        }
                    }
                }
            } else {
                items(filteredRoutines) { routine ->
                    WorkoutRoutineCard(
                        routine = routine,
                        onPreview = { selectedRoutineForPreview = routine },
                        onStart = { viewModel.startWorkout(routine) },
                        onDelete = if (routine.isCustom) { { viewModel.deleteCustomWorkout(routine.id) } } else null
                    )
                }
            }
        }

        // Preview Routine Dialog
        selectedRoutineForPreview?.let { routine ->
            WorkoutDetailDialog(
                routine = routine,
                onDismiss = { selectedRoutineForPreview = null },
                onStart = {
                    selectedRoutineForPreview = null
                    viewModel.startWorkout(routine)
                }
            )
        }

        // Create Custom Routine Dialog
        if (showCreateDialog) {
            CreateCustomWorkoutDialog(
                viewModel = viewModel,
                onDismiss = { showCreateDialog = false },
                onSave = { title, subtitle, diff, minutes, desc, exercises ->
                    viewModel.saveCustomWorkout(title, subtitle, diff, minutes, desc, exercises)
                    showCreateDialog = false
                }
            )
        }
    }
}

@Composable
private fun WorkoutRoutineCard(
    routine: WorkoutRoutine,
    onPreview: () -> Unit,
    onStart: () -> Unit,
    onDelete: (() -> Unit)? = null
) {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = DarkSurfaceCard),
        border = androidx.compose.foundation.BorderStroke(1.dp, DarkOutline),
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .clickable(onClick = onPreview)
            .testTag("workout_card_${routine.id}")
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = when (routine.difficulty) {
                        Difficulty.INICIANTE -> EmeraldSuccess.copy(alpha = 0.2f)
                        Difficulty.INTERMEDIARIO -> CyanAccent.copy(alpha = 0.2f)
                        Difficulty.AVANCADO -> AmberGold.copy(alpha = 0.2f)
                        Difficulty.ELITE -> CoralFlame.copy(alpha = 0.2f)
                    }
                ) {
                    Text(
                        text = routine.difficulty.label.uppercase(),
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = when (routine.difficulty) {
                            Difficulty.INICIANTE -> EmeraldSuccess
                            Difficulty.INTERMEDIARIO -> CyanAccent
                            Difficulty.AVANCADO -> AmberGold
                            Difficulty.ELITE -> CoralFlame
                        },
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Schedule,
                        contentDescription = "Tempo estimado",
                        tint = Color(0xFF94A3B8),
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${routine.estimatedDurationMinutes} min",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF94A3B8)
                    )

                    if (onDelete != null) {
                        Spacer(modifier = Modifier.width(8.dp))
                        IconButton(
                            onClick = onDelete,
                            modifier = Modifier.size(24.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Excluir treino",
                                tint = CoralFlame,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = routine.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = routine.subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFFCBD5E1)
            )

            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${routine.exercises.size} Exercícios • ${routine.exercises.sumOf { it.sets }} Séries",
                    style = MaterialTheme.typography.labelSmall,
                    color = AmberGoldLight
                )

                Button(
                    onClick = onStart,
                    colors = ButtonDefaults.buttonColors(containerColor = AmberGold),
                    shape = RoundedCornerShape(10.dp),
                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Começar",
                        tint = Color.Black,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Iniciar",
                        color = Color.Black,
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
private fun WorkoutDetailDialog(
    routine: WorkoutRoutine,
    onDismiss: () -> Unit,
    onStart: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = DarkSurface,
        title = {
            Column {
                Text(
                    text = routine.title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "${routine.difficulty.label} • ${routine.estimatedDurationMinutes} minutos",
                    style = MaterialTheme.typography.bodySmall,
                    color = AmberGold
                )
            }
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = routine.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFFCBD5E1)
                )

                Text(
                    text = "Sequência de Exercícios:",
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )

                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    routine.exercises.forEachIndexed { index, ex ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(DarkSurfaceCard, RoundedCornerShape(8.dp))
                                .padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                shape = CircleShape,
                                color = AmberGold,
                                modifier = Modifier.size(24.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(
                                        text = "${index + 1}",
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Black
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = ex.exerciseName,
                                    style = MaterialTheme.typography.labelMedium,
                                    color = Color.White
                                )
                                Text(
                                    text = "${ex.sets} séries × ${ex.targetRepsOrSeconds}${if (ex.isIsometric) "s isometria" else " reps"} (descanso ${ex.restSeconds}s)",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color(0xFF94A3B8)
                                )
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = onStart,
                colors = ButtonDefaults.buttonColors(containerColor = AmberGold)
            ) {
                Text("Iniciar Agora", color = Color.Black, fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Fechar", color = Color(0xFF94A3B8))
            }
        }
    )
}

@Composable
private fun CreateCustomWorkoutDialog(
    viewModel: CalisthenicsViewModel,
    onDismiss: () -> Unit,
    onSave: (String, String, Difficulty, Int, String, List<WorkoutExercise>) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var subtitle by remember { mutableStateOf("") }
    var minutesText by remember { mutableStateOf("30") }
    var selectedDifficulty by remember { mutableStateOf(Difficulty.INTERMEDIARIO) }
    val selectedExercises = remember { mutableStateListOf<WorkoutExercise>() }

    // Dialog state to pick exercise from library
    var showExercisePicker by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = DarkSurface,
        title = {
            Text(
                text = "Criar Treino Personalizado",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        },
        text = {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                item {
                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text("Nome do Treino (ex: Treino de Pegada)") },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = AmberGold,
                            unfocusedBorderColor = DarkOutline,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                item {
                    OutlinedTextField(
                        value = subtitle,
                        onValueChange = { subtitle = it },
                        label = { Text("Subtítulo / Objetivo") },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = AmberGold,
                            unfocusedBorderColor = DarkOutline,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                item {
                    OutlinedTextField(
                        value = minutesText,
                        onValueChange = { minutesText = it },
                        label = { Text("Duração estimada (minutos)") },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = AmberGold,
                            unfocusedBorderColor = DarkOutline,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                item {
                    Text(
                        text = "Exercícios Selecionados (${selectedExercises.size}):",
                        style = MaterialTheme.typography.labelLarge,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }

                items(selectedExercises) { ex ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(DarkSurfaceCard, RoundedCornerShape(8.dp))
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = ex.exerciseName, color = Color.White, style = MaterialTheme.typography.labelMedium)
                            Text(
                                text = "${ex.sets} séries × ${ex.targetRepsOrSeconds} reps • ${ex.restSeconds}s descanso",
                                color = Color(0xFF94A3B8),
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                        IconButton(
                            onClick = { selectedExercises.remove(ex) },
                            modifier = Modifier.size(24.dp)
                        ) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = "Remover", tint = CoralFlame)
                        }
                    }
                }

                item {
                    OutlinedButton(
                        onClick = { showExercisePicker = true },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = AmberGold),
                        border = androidx.compose.foundation.BorderStroke(1.dp, AmberGold)
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "Adicionar")
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("+ Adicionar Exercício da Biblioteca")
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (title.isNotBlank() && selectedExercises.isNotEmpty()) {
                        onSave(
                            title,
                            subtitle,
                            selectedDifficulty,
                            minutesText.toIntOrNull() ?: 30,
                            "Rotina personalizada criada por você.",
                            selectedExercises.toList()
                        )
                    }
                },
                enabled = title.isNotBlank() && selectedExercises.isNotEmpty(),
                colors = ButtonDefaults.buttonColors(containerColor = AmberGold)
            ) {
                Text("Salvar Treino", color = Color.Black, fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar", color = Color(0xFF94A3B8))
            }
        }
    )

    if (showExercisePicker) {
        AlertDialog(
            onDismissRequest = { showExercisePicker = false },
            containerColor = DarkSurface,
            title = {
                Text("Selecione um Exercício", color = Color.White, fontWeight = FontWeight.Bold)
            },
            text = {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    items(viewModel.allExercises) { ex ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(DarkSurfaceCard)
                                .clickable {
                                    selectedExercises.add(
                                        WorkoutExercise(
                                            exerciseId = ex.id,
                                            exerciseName = ex.name,
                                            muscleGroup = ex.muscleGroup,
                                            sets = ex.defaultSets,
                                            targetRepsOrSeconds = ex.defaultRepsOrSeconds,
                                            restSeconds = ex.defaultRestSeconds,
                                            isIsometric = ex.isIsometric
                                        )
                                    )
                                    showExercisePicker = false
                                }
                                .padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(text = ex.name, color = Color.White, style = MaterialTheme.typography.labelMedium)
                                Text(
                                    text = "${ex.muscleGroup.displayName} • ${ex.defaultSets}x${ex.defaultRepsOrSeconds}",
                                    color = Color(0xFF94A3B8),
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }
                }
            },
            confirmButton = {},
            dismissButton = {
                TextButton(onClick = { showExercisePicker = false }) {
                    Text("Fechar", color = Color(0xFF94A3B8))
                }
            }
        )
    }
}
