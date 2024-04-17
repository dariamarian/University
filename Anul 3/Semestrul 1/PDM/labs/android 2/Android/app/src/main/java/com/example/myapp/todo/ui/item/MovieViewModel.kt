package com.example.myapp.todo.ui.item

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.myapp.MyApplication
import com.example.myapp.core.Result
import com.example.myapp.core.TAG
import com.example.myapp.todo.data.Movie
import com.example.myapp.todo.data.MovieRepository
import kotlinx.coroutines.launch

data class ItemUiState(
    val itemId: String? = null,
    val movie: Movie = Movie(),
    var loadResult: Result<Movie>? = null,
    var submitResult: Result<Movie>? = null,
)

class ItemViewModel(private val itemId: String?, private val movieRepository: MovieRepository) :
        ViewModel() {

    var uiState: ItemUiState by mutableStateOf(ItemUiState(loadResult = Result.Loading))
        private set

    init {
        Log.d(TAG, "init")
        if (itemId != null) {
            Log.d(TAG, "itemId was not null")
            loadItem()
        } else {
            uiState = uiState.copy(loadResult = Result.Success(Movie()))
        }
    }

    fun loadItem() {
        viewModelScope.launch {
            movieRepository.itemStream.collect { items ->
                if (!(uiState.loadResult is Result.Loading)) {
                    return@collect
                }
                val movie = items.find { it._id == itemId } ?: Movie()

                Log.d(TAG, "loadItem now {${movie.long}, ${movie.lat}")
                uiState = uiState.copy(movie = movie, loadResult = Result.Success(movie))
            }
        }
    }


    fun saveOrUpdateItem(name: String, year: String, age: Int, explicit: Boolean, lat: Double, long: Double , isOnline: Boolean) {
        viewModelScope.launch {
            Log.d(TAG, "saveOrUpdateItem...");
            try {
                uiState = uiState.copy(submitResult = Result.Loading)
                val item = uiState.movie.copy(name = name, year = year, age = age, explicit = explicit, lat = lat, long = long)
                Log.d(TAG, "saveOrUpdateItem item = $item")

                val savedMovie: Movie
                if (itemId == null) {
                    Log.d(TAG, "itemId was null")
                    savedMovie = movieRepository.save(item, isOnline)
                } else {
                    savedMovie = movieRepository.update(item, isOnline)
                }
                Log.d(TAG, "saveOrUpdateItem succeeeded");
                uiState = uiState.copy(submitResult = Result.Success(savedMovie))
            } catch (e: Exception) {
                Log.d(TAG, "saveOrUpdateItem failed");
                uiState = uiState.copy(submitResult = Result.Error(e))
            }
        }
    }

    companion object {
        fun Factory(itemId: String?): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app =
                        (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MyApplication)
                ItemViewModel(itemId, app.container.movieRepository)
            }
        }
    }
}
