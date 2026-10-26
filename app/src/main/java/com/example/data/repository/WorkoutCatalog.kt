package com.example.data.repository

import com.example.data.model.Difficulty
import com.example.data.model.Equipment
import com.example.data.model.MuscleGroup
import com.example.data.model.WorkoutCategory
import com.example.data.model.WorkoutExercise
import com.example.data.model.WorkoutRoutine

object WorkoutCatalog {
    val predefinedRoutines: List<WorkoutRoutine> = listOf(
        WorkoutRoutine(
            id = "routine_beginner_foundation",
            title = "Fundamentos da Calistenia",
            subtitle = "Construa sua base sólida de força com peso do corpo",
            category = WorkoutCategory.CORPO_TODO,
            difficulty = Difficulty.INICIANTE,
            estimatedDurationMinutes = 25,
            equipmentRequired = listOf(Equipment.NENHUM, Equipment.BARRA_FIXA),
            description = "O ponto de partida perfeito. Trabalha peito, costas, pernas e core de forma equilibrada sem exigir equipamentos complexos.",
            exercises = listOf(
                WorkoutExercise(
                    exerciseId = "pushup_standard",
                    exerciseName = "Flexão Tradicional",
                    muscleGroup = MuscleGroup.PEITO,
                    sets = 3,
                    targetRepsOrSeconds = 10,
                    restSeconds = 60
                ),
                WorkoutExercise(
                    exerciseId = "australian_pullup",
                    exerciseName = "Barra Australiana (Inverted Row)",
                    muscleGroup = MuscleGroup.COSTAS,
                    sets = 3,
                    targetRepsOrSeconds = 10,
                    restSeconds = 60
                ),
                WorkoutExercise(
                    exerciseId = "jump_squats",
                    exerciseName = "Agachamento Dinâmico",
                    muscleGroup = MuscleGroup.PERNAS,
                    sets = 3,
                    targetRepsOrSeconds = 15,
                    restSeconds = 60
                ),
                WorkoutExercise(
                    exerciseId = "hollow_body_hold",
                    exerciseName = "Canoa Isométrica (Hollow Body)",
                    muscleGroup = MuscleGroup.CORE,
                    sets = 3,
                    targetRepsOrSeconds = 25,
                    restSeconds = 45,
                    isIsometric = true
                )
            )
        ),
        WorkoutRoutine(
            id = "routine_push_power",
            title = "Push Power: Peito & Tríceps",
            subtitle = "Volume e hipertrofia para membros superiores",
            category = WorkoutCategory.SUPERIOR_PUSH,
            difficulty = Difficulty.INTERMEDIARIO,
            estimatedDurationMinutes = 35,
            equipmentRequired = listOf(Equipment.BARRAS_PARALELAS, Equipment.NENHUM),
            description = "Foco intenso no padrão de empurrar. Mergulhos em paralelas pesados combinados com flexões anguladas e tríceps diamante.",
            exercises = listOf(
                WorkoutExercise(
                    exerciseId = "dips_parallel",
                    exerciseName = "Mergulho em Paralelas (Dips)",
                    muscleGroup = MuscleGroup.PEITO,
                    sets = 4,
                    targetRepsOrSeconds = 8,
                    restSeconds = 90
                ),
                WorkoutExercise(
                    exerciseId = "pushup_standard",
                    exerciseName = "Flexão Tradicional",
                    muscleGroup = MuscleGroup.PEITO,
                    sets = 3,
                    targetRepsOrSeconds = 12,
                    restSeconds = 60
                ),
                WorkoutExercise(
                    exerciseId = "pushup_diamond",
                    exerciseName = "Flexão Diamante",
                    muscleGroup = MuscleGroup.BRACOS,
                    sets = 3,
                    targetRepsOrSeconds = 10,
                    restSeconds = 75
                ),
                WorkoutExercise(
                    exerciseId = "pike_pushup",
                    exerciseName = "Flexão Pike (Ombros)",
                    muscleGroup = MuscleGroup.OMBROS,
                    sets = 3,
                    targetRepsOrSeconds = 8,
                    restSeconds = 75
                )
            )
        ),
        WorkoutRoutine(
            id = "routine_pull_master",
            title = "Pull Master: Costas Largas & Bíceps",
            subtitle = "Força vertical de tração e asas dorsais",
            category = WorkoutCategory.SUPERIOR_PULL,
            difficulty = Difficulty.INTERMEDIARIO,
            estimatedDurationMinutes = 35,
            equipmentRequired = listOf(Equipment.BARRA_FIXA),
            description = "Construa uma pegada de ferro e dorsais volumosas alternando pegadas pronada, supinada e remadas horizontais.",
            exercises = listOf(
                WorkoutExercise(
                    exerciseId = "pullup_standard",
                    exerciseName = "Barra Fixa Pronada (Pull-Up)",
                    muscleGroup = MuscleGroup.COSTAS,
                    sets = 4,
                    targetRepsOrSeconds = 8,
                    restSeconds = 90
                ),
                WorkoutExercise(
                    exerciseId = "chinup_standard",
                    exerciseName = "Barra Fixa Supinada (Chin-Up)",
                    muscleGroup = MuscleGroup.BRACOS,
                    sets = 3,
                    targetRepsOrSeconds = 8,
                    restSeconds = 75
                ),
                WorkoutExercise(
                    exerciseId = "australian_pullup",
                    exerciseName = "Barra Australiana (Inverted Row)",
                    muscleGroup = MuscleGroup.COSTAS,
                    sets = 3,
                    targetRepsOrSeconds = 12,
                    restSeconds = 60
                ),
                WorkoutExercise(
                    exerciseId = "hanging_leg_raise",
                    exerciseName = "Elevação de Pernas na Barra",
                    muscleGroup = MuscleGroup.CORE,
                    sets = 3,
                    targetRepsOrSeconds = 10,
                    restSeconds = 60
                )
            )
        ),
        WorkoutRoutine(
            id = "routine_core_steel",
            title = "Core de Aço & Compressão",
            subtitle = "Abdômen rasgado e estabilização de coluna",
            category = WorkoutCategory.CORE_ABS,
            difficulty = Difficulty.INTERMEDIARIO,
            estimatedDurationMinutes = 20,
            equipmentRequired = listOf(Equipment.BARRA_FIXA, Equipment.NENHUM),
            description = "Treino voltado para a compressão ginástica. Fortalece o abdômen anterior, oblíquos e flexores de quadril.",
            exercises = listOf(
                WorkoutExercise(
                    exerciseId = "hanging_leg_raise",
                    exerciseName = "Elevação de Pernas na Barra",
                    muscleGroup = MuscleGroup.CORE,
                    sets = 4,
                    targetRepsOrSeconds = 12,
                    restSeconds = 60
                ),
                WorkoutExercise(
                    exerciseId = "lsit_floor",
                    exerciseName = "L-Sit Isométrico",
                    muscleGroup = MuscleGroup.CORE,
                    sets = 4,
                    targetRepsOrSeconds = 15,
                    restSeconds = 60,
                    isIsometric = true
                ),
                WorkoutExercise(
                    exerciseId = "hollow_body_hold",
                    exerciseName = "Canoa Isométrica (Hollow Body)",
                    muscleGroup = MuscleGroup.CORE,
                    sets = 3,
                    targetRepsOrSeconds = 30,
                    restSeconds = 45,
                    isIsometric = true
                ),
                WorkoutExercise(
                    exerciseId = "pushup_standard",
                    exerciseName = "Prancha com Flexão Estrita",
                    muscleGroup = MuscleGroup.CORE,
                    sets = 3,
                    targetRepsOrSeconds = 12,
                    restSeconds = 60
                )
            )
        ),
        WorkoutRoutine(
            id = "routine_legs_power",
            title = "Pernas & Explosão Calistênica",
            subtitle = "Pistol Squats, saltos pliométricos e posteriores",
            category = WorkoutCategory.INFERIOR_LEGS,
            difficulty = Difficulty.AVANCADO,
            estimatedDurationMinutes = 30,
            equipmentRequired = listOf(Equipment.NENHUM),
            description = "Desmistifica que calistenia não treina pernas! Combina unilateralidade máxima com saltos e cadeia posterior.",
            exercises = listOf(
                WorkoutExercise(
                    exerciseId = "pistol_squat",
                    exerciseName = "Pistol Squat (Cada Perna)",
                    muscleGroup = MuscleGroup.PERNAS,
                    sets = 4,
                    targetRepsOrSeconds = 6,
                    restSeconds = 90
                ),
                WorkoutExercise(
                    exerciseId = "jump_squats",
                    exerciseName = "Agachamento com Salto Explosivo",
                    muscleGroup = MuscleGroup.PERNAS,
                    sets = 3,
                    targetRepsOrSeconds = 15,
                    restSeconds = 60
                ),
                WorkoutExercise(
                    exerciseId = "nordic_curl",
                    exerciseName = "Flexão Nórdica (Posteriores)",
                    muscleGroup = MuscleGroup.PERNAS,
                    sets = 3,
                    targetRepsOrSeconds = 6,
                    restSeconds = 90
                )
            )
        ),
        WorkoutRoutine(
            id = "routine_elite_skills",
            title = "Calistenia Elite: Habilidades & Força",
            subtitle = "Treino avançado para atletas experientes",
            category = WorkoutCategory.HABILIDADES,
            difficulty = Difficulty.ELITE,
            estimatedDurationMinutes = 45,
            equipmentRequired = listOf(Equipment.BARRA_FIXA, Equipment.BARRAS_PARALELAS, Equipment.PAREDE),
            description = "Rotina para quem já domina os básicos e quer trabalhar transferências para Muscle-Up, Planche e Handstand.",
            exercises = listOf(
                WorkoutExercise(
                    exerciseId = "muscle_up",
                    exerciseName = "Muscle-Up na Barra",
                    muscleGroup = MuscleGroup.CORPO_TODO,
                    sets = 4,
                    targetRepsOrSeconds = 4,
                    restSeconds = 120
                ),
                WorkoutExercise(
                    exerciseId = "planche_tuck",
                    exerciseName = "Tuck Planche Isométrico",
                    muscleGroup = MuscleGroup.OMBROS,
                    sets = 4,
                    targetRepsOrSeconds = 15,
                    restSeconds = 90,
                    isIsometric = true
                ),
                WorkoutExercise(
                    exerciseId = "front_lever_tuck",
                    exerciseName = "Tuck Front Lever Isométrico",
                    muscleGroup = MuscleGroup.COSTAS,
                    sets = 4,
                    targetRepsOrSeconds = 15,
                    restSeconds = 90,
                    isIsometric = true
                ),
                WorkoutExercise(
                    exerciseId = "handstand_wall",
                    exerciseName = "Parada de Mão na Parede",
                    muscleGroup = MuscleGroup.OMBROS,
                    sets = 3,
                    targetRepsOrSeconds = 30,
                    restSeconds = 60,
                    isIsometric = true
                )
            )
        )
    )

    fun getById(id: String): WorkoutRoutine? = predefinedRoutines.find { it.id == id }
}
