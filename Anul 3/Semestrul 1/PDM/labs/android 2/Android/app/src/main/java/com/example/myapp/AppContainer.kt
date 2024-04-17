package com.example.myapp.core

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.preferencesDataStore
import com.example.myapp.MyAppDatabase
import com.example.myapp.auth.data.AuthRepository
import com.example.myapp.auth.data.remote.AuthDataSource
import com.example.myapp.core.data.UserPreferencesRepository
import com.example.myapp.core.data.remote.Api
import com.example.myapp.todo.data.MovieRepository
import com.example.myapp.todo.data.remote.MovieService
import com.example.myapp.todo.data.remote.MovieWsClient

val Context.userPreferencesDataStore by preferencesDataStore(
    name = "user_preferences"
)

class AppContainer(val context: Context) {
    init {
        Log.d(TAG, "init")
    }

    val itemService: MovieService = Api.retrofit.create(MovieService::class.java)
    private val movieWsClient: MovieWsClient = MovieWsClient(Api.okHttpClient)
    private val authDataSource: AuthDataSource = AuthDataSource()



    private val database: MyAppDatabase by lazy { MyAppDatabase.getDatabase(context) }

    val movieRepository: MovieRepository by lazy {
        MovieRepository(itemService, movieWsClient, database.itemDao())
    }

    val authRepository: AuthRepository by lazy {
        AuthRepository(authDataSource)
    }

    val userPreferencesRepository: UserPreferencesRepository by lazy {
        UserPreferencesRepository(context.userPreferencesDataStore)
    }




}
