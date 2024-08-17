package com.example.to_dolist.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity( tableName = "todo-table")
data class Todo(
    @PrimaryKey( autoGenerate = true)
    val id: Long = 0L,
    @ColumnInfo( name = "todo-title")
    val title: String = "",
)

object DummyData{
    val todoData = listOf(
        Todo(title = "Hello"),
        Todo(title = "Hi"),
        Todo(title = "Good"),
        Todo(title = "Bye")
    )
}