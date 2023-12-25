package com.movie_application.database

import android.widget.Toast

object FavoriteMovieSys {
        var favMovieList: MutableList<Movie> = mutableListOf<Movie>()
        fun addFav(movie: Movie){
                favMovieList.add(movie)
        }
}