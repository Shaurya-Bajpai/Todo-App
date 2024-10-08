package com.example.to_dolist.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.to_dolist.data.Todo


@Database(
    entities = [Todo::class],
    version = 1,
    exportSchema = false
)
abstract class TodoDatabase: RoomDatabase() {

    abstract fun todoDao(): TodoDao

}