package com.movie_application.database

object FavoriteMovieSys {
        var favMovieList: MutableList<Movie> = mutableListOf<Movie>()
        fun addFav(movie: Movie): Boolean {
                if (!favMovieList.contains(movie)) {
                        favMovieList.add(movie)
                        return true
                }
                return false
        }
}