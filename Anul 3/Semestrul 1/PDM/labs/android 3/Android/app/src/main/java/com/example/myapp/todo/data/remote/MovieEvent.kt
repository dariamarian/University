package com.example.myapp.todo.data.remote

import com.example.myapp.todo.data.Movie

data class MovieEvent(val type: String, val payload: Movie)
