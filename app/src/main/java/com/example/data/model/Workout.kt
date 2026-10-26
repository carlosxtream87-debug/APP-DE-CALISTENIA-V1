package com.example.data.model

enum class WorkoutCategory(val label: String, val iconName: String) {
    CORPO_TODO("Corpo Todo (Full Body)", "fitness_center"),
    SUPERIOR_PUSH("Superior - Empurrar (Push)", "accessibility_new"),
    SUPERIOR_PULL("Superior - Puxar (Pull)", "vertical_align_top"),
    INFERIOR_LEGS("Pernas & Explosão (Legs)", "directions_run"),
    CORE_ABS("Abdômen & Core de Aço", "self_improvement"),
    HABILIDADES("Skills & Isometrias", "emoji_events"),
    PERSONALIZADO("Meus Treinos Criados", "tune")
}

data class WorkoutExercise(
    val exerciseId: String,
    val exerciseName: String,
    val muscleGroup: MuscleGroup,
    val sets: Int,
    val targetRepsOrSeconds: Int,
    val restSeconds: Int,
    val isIsometric: Boolean = false
)

data class WorkoutRoutine(
    val id: String,
    val title: String,
    val subtitle: String,
    val category: WorkoutCategory,
    val difficulty: Difficulty,
    val estimatedDurationMinutes: Int,
    val equipmentRequired: List<Equipment>,
    val description: String,
    val exercises: List<WorkoutExercise>,
    val isCustom: Boolean = false
)
