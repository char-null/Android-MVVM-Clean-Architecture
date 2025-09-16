package com.example.todoapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.todoapp.data.model.TodoEntity
import java.util.Date

@Database(entities = [TodoEntity::class], version = 1, exportSchema = false)

@TypeConverters(Converters::class)
abstract class TodoDatabase : RoomDatabase() {
    abstract val todoDao: TodoDao
}

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}
