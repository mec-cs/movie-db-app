package com.movie_application.database

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow

class MovieRepository (private val movieDAO: MovieDAO) {
    val readAllData: LiveData<List<Movie>> = movieDAO.getAllMovies()

    fun insertMovie(movie:Movie){
        movieDAO.insertMovie(movie)
    }
    fun insertMovies(movies:ArrayList<Movie>){
        movieDAO.insertAllMovies(movies)
    }

    fun updateMovie(movie:Movie){
        movieDAO.updateMovie(movie)
    }

    fun deleteMovie(movie:Movie){
        movieDAO.deleteMovie(movie)
    }

    fun deleteAllMovies(){
        movieDAO.deleteAllMovies()
    }

    fun getAllMovies(): LiveData<List<Movie>> {
        return movieDAO.getAllMovies()
    }

    fun getMoviesBySearchKey(searchKey:String): Flow<List<Movie>> {
        return movieDAO.getMoviesBySearchKey(searchKey)
    }

    fun getMoviesByGenre(genre: String): Flow<List<Movie>> {
        return movieDAO.getMoviesByGenre(genre)
    }
}