package com.movie_application.service

import com.movie_application.database.Movie
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {
    @GET("posts")
    fun getMovies(): Call<List<Movie>>

    // it will find and get the movies by the result of specific query
    @GET("posts")
    fun getMoviesIncludesGivenName(@Query("MovieSearchKey") movieSearchKey: String): Call<List<Movie>>

}