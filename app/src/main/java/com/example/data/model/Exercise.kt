package com.example.data.model

enum class MuscleGroup(val displayName: String) {
    PEITO("Peitoral"),
    COSTAS("Costas"),
    OMBROS("Ombros"),
    BRACOS("Bíceps e Tríceps"),
    CORE("Abdômen e Core"),
    PERNAS("Pernas e Glúteos"),
    CORPO_TODO("Corpo Todo")
}

enum class Difficulty(val label: String, val levelNumber: Int) {
    INICIANTE("Iniciante", 1),
    INTERMEDIARIO("Intermediário", 2),
    AVANCADO("Avançado", 3),
    ELITE("Elite", 4)
}

enum class Equipment(val displayName: String) {
    NENHUM("Peso Corporal (Chão)"),
    BARRA_FIXA("Barra Fixa"),
    BARRAS_PARALELAS("Barras Paralelas"),
    ARGOLAS("Argolas Olímpicas"),
    PAREDE("Parede")
}

data class Exercise(
    val id: String,
    val name: String,
    val muscleGroup: MuscleGroup,
    val difficulty: Difficulty,
    val equipment: Equipment,
    val description: String,
    val executionSteps: List<String>,
    val tips: List<String>,
    val commonMistakes: List<String>,
    val isIsometric: Boolean = false, // Hold in seconds vs Reps
    val defaultRepsOrSeconds: Int = 10,
    val defaultSets: Int = 3,
    val defaultRestSeconds: Int = 60,
    val regression: String? = null, // Easier version
    val progression: String? = null // Harder version
)
