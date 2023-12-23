package com.movie_application.view

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.movie_application.R
import com.movie_application.apiPackage.ApiClient
import com.movie_application.database.Movie
import com.movie_application.databinding.ActivityMainBinding
import com.movie_application.service.MovieService
import com.movie_application.recyclerView.MovieRecyclerViewAdapter
import com.movie_application.soundPlayer.SoundPlayer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), MovieRecyclerViewAdapter.RecyclerAdapterInterface {
    lateinit var movieService: MovieService
    lateinit var movieList: MutableList<Movie>
    lateinit var adapter: MovieRecyclerViewAdapter
    private lateinit var binding: ActivityMainBinding
    private lateinit var randomMovieDialog: Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        movieService = ApiClient.getClient().create(MovieService::class.java) // By that reference retrofit understands which requests will be sent to server
        var request = movieService.getMovies()

        binding.recyclerMovies.layoutManager = LinearLayoutManager(this)
        adapter = MovieRecyclerViewAdapter(this)
        binding.recyclerMovies.adapter = adapter

        Log.d("JSON_ARRAY_PARSE", "Before Request")
        request.enqueue(object : Callback<List<Movie>> {
            override fun onFailure(call: Call<List<Movie>>, t: Throwable) {
                Toast.makeText(applicationContext, t.message.toString(), Toast.LENGTH_LONG).show()
            }
            override fun onResponse(call: Call<List<Movie>>, response: Response<List<Movie>>) {
                Log.d("JSON_ARRAY_PARSE", "Response taken")
                if (response.isSuccessful) {
                    movieList = (response.body() as MutableList<Movie>?)!!
                    Log.d("JSON_ARRAY_PARSE", "Recipes taken from server"+movieList.toString())
                    adapter.setData(movieList)
                } else {
                    Log.d("JSON_ARRAY_PARSE", "Recipes are not taken from server")
                }
            }
        })

        Log.d("JSON_ARRAY_PARSE", "After Request")




        /*

        // random dialog layout : partly complete
        // random movie from database : None
        // !! image size should be 1280-720

        binding.randomMovieButton.setOnClickListener {
            // in this line get a random movie from database and insert it into the createDialog function
            createRandomMovieDialog(Movie(title = "Test Title", writer = "MenesCakir", releaseDate = "2020", genre = "Comedy", posterImgLink = R.drawable.ic_launcher_background.toString()))
            randomMovieDialog.show()
        }

        */


    }

    override fun displayItem(movie: Movie) {
        val intent = Intent(this, MovieDetailsActivity::class.java)
        intent.putExtra("selectedMovie", movie)
        playClickSound()
        startActivity(intent)
    }

    fun playClickSound(){
        val soundPlayer = SoundPlayer(this)
        soundPlayer.playSound(R.raw.soundeffect)
    }

    @SuppressLint("SetTextI18n")
    fun createRandomMovieDialog(movie: Movie) {
        randomMovieDialog = Dialog(this)
        randomMovieDialog.setContentView(R.layout.random_movie_dialog)

        val tickButton = randomMovieDialog.findViewById<AppCompatImageView>(R.id.random_dialog_movie_action_btn)
        val randomMovieTitle = randomMovieDialog.findViewById<AppCompatTextView>(R.id.random_dialog_movie_title)
        val randomMovieDesc = randomMovieDialog.findViewById<AppCompatTextView>(R.id.random_dialog_movie_desc)
        val backgroundImg = randomMovieDialog.findViewById<AppCompatImageView>(R.id.random_dialog_movie_image)

        randomMovieTitle.text = movie.title
        randomMovieDesc.text = "${movie.writer}, (${movie.releaseDate}), ${movie.genre}"
        backgroundImg.setBackgroundResource(movie.posterImgLink.toInt())

        tickButton.setOnClickListener {
            randomMovieDialog.dismiss()
        }
    }

}