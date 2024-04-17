package com.example.myapp.todo.data.remote

import com.example.myapp.todo.data.Movie
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface MovieService {
    @GET("/api/movie")
    suspend fun find(@Header("Authorization") authorization: String): List<Movie>

    @GET("/api/movie/{id}")
    suspend fun read(
        @Header("Authorization") authorization: String,
        @Path("id") itemId: String?
    ): Movie

    @Headers("Content-Type: application/json")
    @POST("/api/movie")
    suspend fun create(@Header("Authorization") authorization: String, @Body movie: Movie): Movie

    @Headers("Content-Type: application/json")
    @PUT("/api/movie/{id}")
    suspend fun update(
        @Header("Authorization") authorization: String,
        @Path("id") itemId: String?,
        @Body movie: Movie
    ): Movie
    @Headers("Content-Type: application/json")
    @PUT("/api/movie/sync")
    suspend fun sync(
        @Header("Authorization") authorization: String,
        @Body movies: List<Movie>
    ): List<Movie>


}
