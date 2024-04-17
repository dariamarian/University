package com.example.myapp.todo.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.myapp.todo.data.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * FROM Items")
    fun getAll(): Flow<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: Movie)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movies: List<Movie>)

    @Update
    suspend fun update(movie: Movie): Int

    @Query("DELETE FROM Items WHERE _id = :id")
    suspend fun deleteById(id: String): Int

    @Query("DELETE FROM Items")
    suspend fun deleteAll()
}
