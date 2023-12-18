package com.movie_application

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.movie_application.apiPackage.ApiClient
import com.movie_application.database.Movie
import com.movie_application.service.MovieService
import com.movie_application.recyclerView.CustomRecyclerViewAdapter
import com.movie_application.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var movieService: MovieService
    lateinit var movieList: MutableList<Movie>
    lateinit var adapter: CustomRecyclerViewAdapter
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        movieService = ApiClient.getClient().create(MovieService::class.java) // By that reference retrofit understands which requests will be sent to server
        var request = movieService.getMovies()

        binding.recyclerMovies.layoutManager = LinearLayoutManager(this)
        adapter = CustomRecyclerViewAdapter(this)
        binding.recyclerMovies.adapter=adapter

        Log.d("JSONARRAYPARSE", "Before Request")
        request.enqueue(object : Callback<List<Movie>> {
            override fun onFailure(call: Call<List<Movie>>, t: Throwable) {
                Toast.makeText(applicationContext, t.message.toString(), Toast.LENGTH_LONG).show()
            }
            override fun onResponse(call: Call<List<Movie>>, response: Response<List<Movie>>) {
                Log.d("JSONARRAYPARSE", "Response taken")
                if (response.isSuccessful) {
                    movieList = (response.body() as MutableList<Movie>?)!!
                    Log.d("JSONARRAYPARSE", "Recipes taken from server"+movieList.toString())
                    adapter.setData(movieList)
                }
                else{
                    Log.d("JSONARRAYPARSE", "Recipes are not taken from server")
                }
            }
        })
        Log.d("JSONARRAYPARSE", "After Request")
    }
}