package com.movie_application.view

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.movie_application.R
import com.movie_application.apiPackage.ApiClient
import com.movie_application.database.Movie
import com.movie_application.databinding.ActivityMainBinding
import com.movie_application.service.MovieService
import com.movie_application.adapter.MovieRecyclerViewAdapter
import com.movie_application.database.FavoriteMovieSys
import com.movie_application.soundPlayer.SoundPlayer
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.random.Random

class MainActivity : AppCompatActivity(), MovieRecyclerViewAdapter.RecyclerAdapterInterface {
    lateinit var movieService: MovieService
    lateinit var movieList: MutableList<Movie>
    lateinit var adapter: MovieRecyclerViewAdapter
    private lateinit var binding: ActivityMainBinding
    private lateinit var randomMovieDialog: Dialog
    private val NIGHT_MODE = "night_mode"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Restore night mode state
        val isNightMode = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
            .getBoolean(NIGHT_MODE, false)
        val nightMode = if (isNightMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO

        AppCompatDelegate.setDefaultNightMode(nightMode)
        delegate.applyDayNight()

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


        binding.fabRandom.setOnClickListener {
            // in this line get a random movie from database and insert it into the createDialog function
            createRandomMovieDialog()
            randomMovieDialog.show()
        }

        binding.fabFavs.setOnClickListener {
            startActivity(Intent(this, FavoriteActivity::class.java))
        }

        adapter.setOnItemClickListener {
            FavoriteMovieSys.addFav(it)
            Toast.makeText(this, "Succesfully added to fav", Toast.LENGTH_LONG).show()
        }
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
    fun createRandomMovieDialog() {
        val movie: Movie = movieList[Random.nextInt(0,movieList.size)]
        randomMovieDialog = Dialog(this)
        randomMovieDialog.setContentView(R.layout.random_movie_dialog)

        val tickButton = randomMovieDialog.findViewById<AppCompatImageView>(R.id.random_dialog_movie_action_btn)
        val randomMovieTitle = randomMovieDialog.findViewById<AppCompatTextView>(R.id.random_dialog_movie_title)
        val randomMovieDesc = randomMovieDialog.findViewById<AppCompatTextView>(R.id.random_dialog_movie_desc)
        val backgroundImg = randomMovieDialog.findViewById<AppCompatImageView>(R.id.random_dialog_movie_image)

        randomMovieTitle.text = movie.title
        randomMovieDesc.text = "${movie.releaseDate}, ${movie.genre}"
        Picasso.get().load(movie.posterImgLink)
            .resize(800,0) //optional, Transform images to better fit into layouts and to reduce memory size.
            .error(R.drawable.ic_launcher_background)//optional, Picasso supports both download and error placeholders as optional features
            .into(backgroundImg) //taken image will be displayed on imgItemRecipe view.

        tickButton.setOnClickListener {
            randomMovieDialog.dismiss()
        }
    }

    fun onToggleNightModeClick(view: View) {
        toggleNightMode()
    }

    private fun toggleNightMode() {
        val currentNightMode = AppCompatDelegate.getDefaultNightMode()
        val newMode = if (currentNightMode == AppCompatDelegate.MODE_NIGHT_YES) AppCompatDelegate.MODE_NIGHT_NO else AppCompatDelegate.MODE_NIGHT_YES

        // Save the new night mode state
        getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
            .edit()
            .putBoolean(NIGHT_MODE, newMode == AppCompatDelegate.MODE_NIGHT_YES)
            .apply()

        // Set and apply the new night mode
        AppCompatDelegate.setDefaultNightMode(newMode)
        delegate.applyDayNight()
    }
}