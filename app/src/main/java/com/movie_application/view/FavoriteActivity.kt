package com.movie_application.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.Window
import android.view.WindowManager
import androidx.core.view.GestureDetectorCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.movie_application.adapter.FavMovieItemDecoration
import com.movie_application.adapter.FavMovieRecyclerViewAdapter
import com.movie_application.database.Movie
import com.movie_application.databinding.ActivityFavoriteBinding
import kotlin.math.abs

class FavoriteActivity : AppCompatActivity(), FavMovieRecyclerViewAdapter.FavRecyclerViewInterface {

    private lateinit var favBinding: ActivityFavoriteBinding
    lateinit var favAdapter: FavMovieRecyclerViewAdapter
    var gDetector: GestureDetectorCompat? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        @Suppress("DEPRECATION")
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        favBinding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(favBinding.root)

        favAdapter = FavMovieRecyclerViewAdapter(this)

        favBinding.favMovieRecyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        favBinding.favMovieRecyclerView.adapter = favAdapter
        favBinding.favMovieRecyclerView.addItemDecoration(FavMovieItemDecoration(10))

        gDetector =  GestureDetectorCompat(this, CustomGesture())
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        gDetector?.onTouchEvent(event)
        return super.onTouchEvent(event)
    }

    override fun showFavMovie(movie: Movie) {
        favBinding.favMovieDetailsTv.text = movie.toString()
    }

    inner class CustomGesture: GestureDetector.SimpleOnGestureListener() {
        override fun onFling(e1: MotionEvent?, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
            val distanceX = e2.x - e1!!.x
            val distanceY = e2.y - e1!!.y
            if (abs(distanceX) > abs(distanceY) && abs(distanceX) > 100 && abs(velocityX) > 100)
                if (distanceX > 0) {
                    val intent = Intent(this@FavoriteActivity, MainActivity::class.java)
                    startActivity(intent)
                    return true
                }

            return false
        }
    }
}