package com.movie_application.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.movie_application.adapter.FavMovieItemDecoration
import com.movie_application.adapter.FavMovieRecyclerViewAdapter
import com.movie_application.databinding.ActivityFavoriteBinding

class FavoriteActivity : AppCompatActivity() {

    private lateinit var favBinding: ActivityFavoriteBinding
    lateinit var favAdapter: FavMovieRecyclerViewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        favBinding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(favBinding.root)

        favAdapter = FavMovieRecyclerViewAdapter(this)

        favBinding.favMovieRecyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        favBinding.favMovieRecyclerView.adapter = favAdapter
        favBinding.favMovieRecyclerView.addItemDecoration(FavMovieItemDecoration(10))

    }
}