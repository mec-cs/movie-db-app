package com.movie_application.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieViewModel(application: Application): AndroidViewModel(application) {
    val readAllData: LiveData<List<Movie>>
    private val repository:MovieRepository

    init {
        val movieDAO = MovieRoomDatabase.getDatabase(application).movieDao()
        repository = MovieRepository(movieDAO)
        readAllData = repository.readAllData
    }

    fun addMovie(movie:Movie) {
        viewModelScope.launch(Dispatchers.IO) { // that code will be run in background thread, coroutine scope
            repository.insertMovie(movie)
        }
    }

    fun addMovies(movies: List<Movie>) {
        viewModelScope.launch(Dispatchers.IO) { // that code will be run in background thread, coroutine scope
            movies.forEach{
                repository.insertMovie(it)
            }
        }
    }

    fun deleteMovie(movie:Movie) {
        viewModelScope.launch(Dispatchers.IO) { // that code will be run in background thread, coroutine scope
            repository.deleteMovie(movie)
        }
    }

    fun deleteAllMovies() {
        viewModelScope.launch(Dispatchers.IO) { // that code will be run in background thread, coroutine scope
            repository.deleteAllMovies()
        }

    }

    fun updateMovie(movie:Movie) {
        viewModelScope.launch(Dispatchers.IO){ // that code will be run in background thread, coroutine scope
            repository.updateMovie(movie)
        }
    }

    fun searchMovieByTitle(searchkey:String): LiveData<List<Movie>> {
        return repository.getMoviesBySearchKey(searchkey).asLiveData()
    }

    fun searchMoviesByGenre(genre:String): LiveData<List<Movie>> {
        return repository.getMoviesByGenre(genre).asLiveData()
    }

    fun getAllMoviesInArray(): LiveData<List<Movie>> {
        return repository.getAllMovies()
    }
}