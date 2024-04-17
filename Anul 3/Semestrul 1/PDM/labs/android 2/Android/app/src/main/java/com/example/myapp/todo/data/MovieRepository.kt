package com.example.myapp.todo.data

import android.util.Log
import com.example.myapp.core.TAG
import com.example.myapp.core.data.remote.Api
import com.example.myapp.todo.data.local.MovieDao
import com.example.myapp.todo.data.remote.MovieEvent
import com.example.myapp.todo.data.remote.MovieService
import com.example.myapp.todo.data.remote.MovieWsClient
import com.example.myapp.util.showSimpleNotificationWithTapAction
import kotlinx.coroutines.flow.collect

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext

class MovieRepository(
    private val itemService: MovieService,
    private val movieWsClient: MovieWsClient,
    val movieDao: MovieDao,
) {
    val itemStream by lazy { movieDao.getAll() }


    init {
        Log.d(TAG, "init")
    }

    private fun getBearerToken(): String {
        Log.d(TAG, "getBearerToken ${Api.tokenInterceptor.token}")
        return "Bearer ${Api.tokenInterceptor.token}"
    }

    suspend fun refresh() {
        Log.d(TAG, "refresh started")
        try {
            val items = itemService.find(authorization = getBearerToken())
            movieDao.deleteAll()
            items.forEach { movieDao.insert(it) }
            Log.d(TAG, "refresh succeeded")
        } catch (e: Exception) {
            Log.w(TAG, "refresh failed", e)
        }
    }

    suspend fun sync() {
        try{
            val items=movieDao.getAll().firstOrNull() ?: emptyList()
            Log.d(TAG, "repo sync $items...")

            val updatedItems = itemService.sync(authorization = getBearerToken(), movies = items)
            //movieDao.deleteAll()
            //updatedItems.forEach { movieDao.insert(it) }
            Log.d(TAG, "sync $items succeeded")
        }
        catch (e: Exception) {
            Log.w(TAG, "sync failed", e)
        }


    }

    suspend fun openWsClient() {
        Log.d(TAG, "openWsClient")
        withContext(Dispatchers.IO) {
            getItemEvents().collect {
                Log.d(TAG, "Item event collected $it")
                if (it.isSuccess) {
                    val itemEvent = it.getOrNull();
                    when (itemEvent?.type) {
                        "created" -> handleItemCreated(itemEvent.payload)
                        "updated" -> handleItemUpdated(itemEvent.payload)
                        "deleted" -> handleItemDeleted(itemEvent.payload)
                    }
                }
            }
        }
    }

    suspend fun closeWsClient() {
        Log.d(TAG, "closeWsClient")
        withContext(Dispatchers.IO) {
            movieWsClient.closeSocket()
        }
    }

    suspend fun getItemEvents(): Flow<kotlin.Result<MovieEvent>> = callbackFlow {
        Log.d(TAG, "getItemEvents started")
        movieWsClient.openSocket(
            onEvent = {
                Log.d(TAG, "onEvent $it")
                if (it != null) {
                    trySend(kotlin.Result.success(it))
                }
            },
            onClosed = { close() },
            onFailure = { close() });
        awaitClose { movieWsClient.closeSocket() }
    }

    suspend fun update(movie: Movie, isOnline: Boolean): Movie {
        Log.d(TAG, "update $movie...")
        if (isOnline){
            val updatedItem =
                itemService.update(itemId = movie._id, movie = movie, authorization = getBearerToken())
            Log.d(TAG, "update $movie succeeded")
            handleItemUpdated(updatedItem)
            return updatedItem
        } else {
            movieDao.update(movie)

            Log.d(TAG, "update $movie succeeded")
            return movie
        }
    }


    suspend fun save(movie: Movie, isOnline : Boolean): Movie {
        Log.d(TAG, "save $movie...")
        if (isOnline){
            val createdItem = itemService.create(movie = movie, authorization = getBearerToken())
            Log.d(TAG, "save $movie succeeded")
            handleItemCreated(createdItem)
            return createdItem
        } else {
            movieDao.insert(movie)
            Log.d(TAG, "save $movie succeeded")
            return movie
        }

    }

    private suspend fun handleItemDeleted(movie: Movie) {
        Log.d(TAG, "handleItemDeleted - todo $movie")
    }

    private suspend fun handleItemUpdated(movie: Movie) {
        Log.d(TAG, "handleItemUpdated...")
        movieDao.update(movie)
    }

    private suspend fun handleItemCreated(movie: Movie) {
        Log.d(TAG, "handleItemCreated...")
        movieDao.insert(movie)
    }

    suspend fun deleteAll() {
        movieDao.deleteAll()
    }

    fun setToken(token: String) {
        movieWsClient.authorize(token)
    }
}