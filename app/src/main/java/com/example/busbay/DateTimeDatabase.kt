package com.example.busbay

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(
    entities = [DateTimeEntity::class],
    version = 1
)
abstract class DateTimeDatabase:RoomDatabase() {

    abstract fun getDateTimeDao():DateTimeDao


    companion object {


        @Volatile
        private var INSTANCE: DateTimeDatabase? = null

        fun getDatabase(context: Context): DateTimeDatabase {
            return INSTANCE?:synchronized(this) {
                val instance =   Room.databaseBuilder(
                    context.applicationContext,
                    DateTimeDatabase::class.java,
                    "note_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }

        }
    }

}