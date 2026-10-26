package com.example.data.model

data class SkillStep(
    val stepIndex: Int,
    val title: String,
    val requirementToAdvance: String,
    val description: String,
    val keyTips: List<String>,
    val targetHoldSecondsOrReps: String
)

data class SkillTree(
    val id: String,
    val name: String,
    val portugueseName: String,
    val category: String,
    val targetMuscles: String,
    val summary: String,
    val steps: List<SkillStep>,
    val prerequisiteInfo: String
)

data class UserSkillProgress(
    val skillId: String,
    val currentStepIndex: Int,
    val isCompleted: Boolean,
    val personalBestRecord: String = ""
)
