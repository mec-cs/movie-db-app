package com.movie_application.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import com.movie_application.constants.Constants

@Dao
interface MovieDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMovie(movie: Movie)

    @Update
    fun updateMovie(movie: Movie)

    @Delete
    fun deleteMovie(movie: Movie)

    @Query("DELETE FROM ${Constants.TABLE_NAME}")
    fun deleteAllMovies()

    @Query("SELECT * FROM ${Constants.TABLE_NAME} ORDER BY title ASC")
    fun getAllMovies(): LiveData<List<Movie>>

    @Query("SELECT * FROM ${Constants.TABLE_NAME} WHERE title LIKE :searchKey")
    fun getMoviesBySearchKey(searchKey:String): Flow<List<Movie>>

    @Query("SELECT * FROM ${Constants.TABLE_NAME} WHERE genre LIKE :searchType")
    fun getMoviesByGenre(searchType: String): Flow<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAllMovies(movies: ArrayList<Movie>){
        movies.forEach{
            insertMovie(it)
        }
    }
}