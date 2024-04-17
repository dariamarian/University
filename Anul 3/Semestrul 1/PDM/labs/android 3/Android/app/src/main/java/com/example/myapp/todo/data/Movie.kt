package com.example.myapp.todo.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items")
data class Movie(@PrimaryKey val _id: String = "",
                 val name: String = "",
                 val year: String = "",
                 val age: Int = 0,
                 val explicit: Boolean = false,
                 val long: Double = 15.0,
                 val lat: Double = 15.0)

