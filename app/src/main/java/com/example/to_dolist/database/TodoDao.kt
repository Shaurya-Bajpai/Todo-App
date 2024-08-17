package com.example.to_dolist.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.to_dolist.data.Todo
import kotlinx.coroutines.flow.Flow


@Dao
abstract class TodoDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun addTask(conEntity: Todo)

    @Query("Select * from `todo-table`")
    abstract fun getAllTask(): Flow<List<Todo>>

    @Update
    abstract fun updateTask(conEntity: Todo)

    @Delete
    abstract fun deleteTask(conEntity: Todo)

    @Query("Select * from `todo-table` where id=:id")
    abstract fun getTaskById(id: Long): Flow<Todo>


}