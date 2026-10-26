package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        WorkoutRecordEntity::class,
        SkillProgressEntity::class,
        PersonalRecordEntity::class,
        CustomWorkoutEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class CalisthenicsDatabase : RoomDatabase() {
    abstract fun workoutRecordDao(): WorkoutRecordDao
    abstract fun skillProgressDao(): SkillProgressDao
    abstract fun personalRecordDao(): PersonalRecordDao
    abstract fun customWorkoutDao(): CustomWorkoutDao

    companion object {
        @Volatile
        private var INSTANCE: CalisthenicsDatabase? = null

        fun getDatabase(context: Context): CalisthenicsDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CalisthenicsDatabase::class.java,
                    "calisthenix_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
