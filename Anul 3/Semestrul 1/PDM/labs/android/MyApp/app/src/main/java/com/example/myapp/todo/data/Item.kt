package com.example.myapp.todo.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items")
data class Item(
    @PrimaryKey val _id: String = "", val title: String = "", val genre: String = "",
    val releaseDate: String = "", val rating: Int = 0, val watched: Boolean = false
)
