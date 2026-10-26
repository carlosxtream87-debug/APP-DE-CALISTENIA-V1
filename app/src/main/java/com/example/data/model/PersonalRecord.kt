package com.example.data.model

data class PersonalRecordItem(
    val id: String,
    val title: String,
    val currentValue: String,
    val unit: String,
    val standardBenchmarkBeginner: String,
    val standardBenchmarkIntermediate: String,
    val standardBenchmarkAdvanced: String,
    val standardBenchmarkElite: String
)
