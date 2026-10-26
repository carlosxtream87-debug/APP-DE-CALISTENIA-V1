package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.dp
import com.example.data.model.Difficulty
import com.example.data.model.Exercise
import com.example.data.model.MuscleGroup
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
fun ExercisesScreen(
    viewModel: CalisthenicsViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler { onBack() }

    var searchQuery by remember { mutableStateOf("") }
    var selectedMuscle by remember { mutableStateOf<MuscleGroup?>(null) }
    var selectedExerciseForDetail by remember { mutableStateOf<Exercise?>(null) }

    val muscleFilters = listOf(
        null to "Todos",
        MuscleGroup.PEITO to "Peitoral",
        MuscleGroup.COSTAS to "Costas",
        MuscleGroup.OMBROS to "Ombros",
        MuscleGroup.BRACOS to "Braços",
        MuscleGroup.CORE to "Core / Abdômen",
        MuscleGroup.PERNAS to "Pernas",
        MuscleGroup.CORPO_TODO to "Corpo Todo"
    )

    val filteredExercises = viewModel.allExercises.filter { ex ->
        val matchesQuery = searchQuery.isBlank() ||
                ex.name.contains(searchQuery, ignoreCase = true) ||
                ex.description.contains(searchQuery, ignoreCase = true)
        val matchesMuscle = selectedMuscle == null || ex.muscleGroup == selectedMuscle
        matchesQuery && matchesMuscle
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
                Column {
                    Text(
                        text = "Enciclopédia de Calistenia",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "Guia completo de execução, postura e variações",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF94A3B8)
                    )
                }
            }

            // Search Bar
            item {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Buscar flexão, barra, planche...") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Buscar",
                            tint = Color(0xFF94A3B8)
                        )
                    },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { searchQuery = "" }) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Limpar busca",
                                    tint = Color(0xFF94A3B8)
                                )
                            }
                        }
                    },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = AmberGold,
                        unfocusedBorderColor = DarkOutline,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedContainerColor = DarkSurfaceCard,
                        unfocusedContainerColor = DarkSurfaceCard
                    ),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("exercise_search_input")
                )
            }

            // Muscle Group Filter Chips
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(vertical = 4.dp)
                ) {
                    items(muscleFilters) { (muscle, label) ->
                        val isSelected = selectedMuscle == muscle
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = if (isSelected) AmberGold else DarkSurfaceCard,
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp,
                                if (isSelected) AmberGold else DarkOutline
                            ),
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .clickable { selectedMuscle = muscle }
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

            // Exercise items list
            items(filteredExercises) { exercise ->
                ExerciseCard(
                    exercise = exercise,
                    onClick = { selectedExerciseForDetail = exercise }
                )
            }
        }

        // Exercise Detail Modal
        selectedExerciseForDetail?.let { exercise ->
            ExerciseDetailDialog(
                exercise = exercise,
                onDismiss = { selectedExerciseForDetail = null }
            )
        }
    }
}

@Composable
private fun ExerciseCard(
    exercise: Exercise,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = DarkSurfaceCard),
        border = androidx.compose.foundation.BorderStroke(1.dp, DarkOutline),
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .testTag("exercise_item_${exercise.id}")
    ) {
        Row(
            modifier = Modifier
                .padding(14.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = when (exercise.difficulty) {
                            Difficulty.INICIANTE -> EmeraldSuccess.copy(alpha = 0.2f)
                            Difficulty.INTERMEDIARIO -> CyanAccent.copy(alpha = 0.2f)
                            Difficulty.AVANCADO -> AmberGold.copy(alpha = 0.2f)
                            Difficulty.ELITE -> CoralFlame.copy(alpha = 0.2f)
                        }
                    ) {
                        Text(
                            text = exercise.difficulty.label.uppercase(),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = when (exercise.difficulty) {
                                Difficulty.INICIANTE -> EmeraldSuccess
                                Difficulty.INTERMEDIARIO -> CyanAccent
                                Difficulty.AVANCADO -> AmberGold
                                Difficulty.ELITE -> CoralFlame
                            },
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }

                    Text(
                        text = "• ${exercise.muscleGroup.displayName}",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF94A3B8)
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = exercise.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Equipamento: ${exercise.equipment.displayName}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFFCBD5E1)
                )
            }

            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "Ver detalhes",
                tint = AmberGold,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
private fun ExerciseDetailDialog(
    exercise: Exercise,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = DarkSurface,
        title = {
            Column {
                Text(
                    text = exercise.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "${exercise.muscleGroup.displayName} • ${exercise.equipment.displayName}",
                    style = MaterialTheme.typography.bodySmall,
                    color = AmberGold
                )
            }
        },
        text = {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                item {
                    Text(
                        text = exercise.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFFCBD5E1)
                    )
                }

                item {
                    Text(
                        text = "Como Executar com Perfeição:",
                        style = MaterialTheme.typography.labelLarge,
                        color = CyanAccent,
                        fontWeight = FontWeight.Bold
                    )
                }

                items(exercise.executionSteps) { step ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Top
                    ) {
                        Text(text = "✓", color = EmeraldSuccess, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = step, color = Color.White, style = MaterialTheme.typography.bodySmall)
                    }
                }

                item {
                    Text(
                        text = "Dicas de Mestre:",
                        style = MaterialTheme.typography.labelLarge,
                        color = AmberGold,
                        fontWeight = FontWeight.Bold
                    )
                }

                items(exercise.tips) { tip ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Top
                    ) {
                        Text(text = "★", color = AmberGold, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = tip, color = Color(0xFFCBD5E1), style = MaterialTheme.typography.bodySmall)
                    }
                }

                if (exercise.commonMistakes.isNotEmpty()) {
                    item {
                        Text(
                            text = "Erros Comuns a Evitar:",
                            style = MaterialTheme.typography.labelLarge,
                            color = CoralFlame,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    items(exercise.commonMistakes) { mistake ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.Top
                        ) {
                            Text(text = "✕", color = CoralFlame, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = mistake, color = Color(0xFFFCA5A5), style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }

                if (exercise.regression != null || exercise.progression != null) {
                    item {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = DarkSurfaceCard,
                            border = androidx.compose.foundation.BorderStroke(1.dp, DarkOutline),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                if (exercise.regression != null) {
                                    Text(
                                        text = "Mais fácil: ${exercise.regression}",
                                        color = EmeraldSuccess,
                                        style = MaterialTheme.typography.labelSmall
                                    )
                                }
                                if (exercise.progression != null) {
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "Mais difícil: ${exercise.progression}",
                                        color = AmberGold,
                                        style = MaterialTheme.typography.labelSmall
                                    )
                                }
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = AmberGold)
            ) {
                Text("Entendido", color = Color.Black, fontWeight = FontWeight.Bold)
            }
        }
    )
}
