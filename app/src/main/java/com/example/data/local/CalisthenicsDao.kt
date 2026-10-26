package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Delete
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutRecordDao {
    @Query("SELECT * FROM workout_history ORDER BY timestampMillis DESC")
    fun getAllHistory(): Flow<List<WorkoutRecordEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkout(record: WorkoutRecordEntity): Long

    @Query("SELECT COUNT(*) FROM workout_history")
    fun getTotalWorkoutsCount(): Flow<Int>

    @Query("SELECT SUM(durationSeconds) FROM workout_history")
    fun getTotalWorkoutSeconds(): Flow<Int?>

    @Query("SELECT SUM(caloriesBurned) FROM workout_history")
    fun getTotalCaloriesBurned(): Flow<Int?>
}

@Dao
interface SkillProgressDao {
    @Query("SELECT * FROM skill_progress")
    fun getAllSkillProgress(): Flow<List<SkillProgressEntity>>

    @Query("SELECT * FROM skill_progress WHERE skillId = :skillId LIMIT 1")
    suspend fun getProgressForSkill(skillId: String): SkillProgressEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSkillProgress(progress: SkillProgressEntity)
}

@Dao
interface PersonalRecordDao {
    @Query("SELECT * FROM personal_records")
    fun getAllRecords(): Flow<List<PersonalRecordEntity>>

    @Query("SELECT * FROM personal_records WHERE recordId = :recordId LIMIT 1")
    suspend fun getRecordById(recordId: String): PersonalRecordEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveRecord(record: PersonalRecordEntity)
}

@Dao
interface CustomWorkoutDao {
    @Query("SELECT * FROM custom_workouts ORDER BY createdAtTimestamp DESC")
    fun getAllCustomWorkouts(): Flow<List<CustomWorkoutEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCustomWorkout(workout: CustomWorkoutEntity)

    @Query("DELETE FROM custom_workouts WHERE id = :workoutId")
    suspend fun deleteCustomWorkout(workoutId: String)
}
