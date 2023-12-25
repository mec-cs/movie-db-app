package com.movie_application.view

import android.content.Intent
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GestureDetectorCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.movie_application.comments.CommentSys
import com.movie_application.comments.CommentViewModel
import com.movie_application.database.Movie
import com.movie_application.R
import com.movie_application.adapter.CommentRecyclerViewAdapter
import com.movie_application.databinding.ActivityMovieDetailsBinding
import kotlin.math.abs


class MovieDetailsActivity : AppCompatActivity() {
    lateinit var binding: ActivityMovieDetailsBinding
    lateinit var smallImageViews: List<ImageView>
    lateinit var selectedMovie: Movie

    lateinit var adapter: CommentRecyclerViewAdapter

    private lateinit var commentViewModel: CommentViewModel
    private var gDetector: GestureDetectorCompat? = null

    private val SIZE_FOR_SMALL_IMAGEVIEW:Int = 89
    private val SIZE_FOR_BIG_IMAGEVIEW: Int = 377
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        @Suppress("DEPRECATION")
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        selectedMovie = (intent.getSerializableExtra("selectedMovie") as? Movie)!!
        smallImageViews = listOf(binding.imageViewBig, binding.imageViewSmall1, binding.imageViewSmall2, binding.imageViewSmall3, binding.imageViewSmall4)
        setInvisibleImageView(smallImageViews)

        setImages()

        binding.movieDetailsText.text = selectedMovie.toString()
        CommentSys.movieName = selectedMovie.title

        adapter = CommentRecyclerViewAdapter(this)
        binding.commentRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.commentRecyclerView.adapter = adapter


        commentViewModel = ViewModelProvider(this)[CommentViewModel::class.java]
        getData()

        //deleting comments on click owner name
        adapter.setOnItemClickListener {
            commentViewModel.deleteComment(it)
            Toast.makeText(this, "Your comment is deleted", Toast.LENGTH_SHORT).show()
        }

        gDetector =  GestureDetectorCompat(this, CustomGesture())
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        gDetector?.onTouchEvent(event)
        return super.onTouchEvent(event)
    }

    private fun setInvisibleImageView(smallImageViews: List<ImageView>) {
        smallImageViews.forEach {
            it.visibility = View.INVISIBLE
        }
    }

    @OptIn(ExperimentalStdlibApi::class)
    fun setImages(){
        for (i in 0..<selectedMovie.imagesList.size) {
            smallImageViews[i].visibility = View.VISIBLE
            val img = selectedMovie.imagesList[i].toString()
            if(i == 0)
                setImageView(smallImageViews[i], img, SIZE_FOR_BIG_IMAGEVIEW)
            else {
                setImageView(smallImageViews[i], img, SIZE_FOR_SMALL_IMAGEVIEW)
                smallImageViews[i].setOnClickListener {
                    swapImage(i)
                }
            }
        }
    }

    private fun swapImage(position:Int) {
        var temp = selectedMovie.imagesList[position].toString()
        selectedMovie.imagesList[position] = selectedMovie.imagesList[0].toString()
        selectedMovie.imagesList[0] = temp

        setImageView(smallImageViews[position], selectedMovie.imagesList[position], SIZE_FOR_SMALL_IMAGEVIEW)
        setImageView(smallImageViews[0], selectedMovie.imagesList[0], SIZE_FOR_BIG_IMAGEVIEW)
    }

    private fun setImageView(imageview:ImageView, imgLink:String, size:Int){
        Glide.with(this)
            .load(imgLink)
            .override(size)
            .error(R.drawable.ic_launcher_background)
            .into(imageview)
    }
    private fun getData() {
        //Whenever data is changed that change will refresh the recyclerview
        commentViewModel.readAllData.observe(this, Observer { comments ->
            //comments = commentViewModel.searchComment(selectedMovie.title)
            adapter.setData(comments)
        })
    }

    inner class CustomGesture: GestureDetector.SimpleOnGestureListener() {
        override fun onFling(e1: MotionEvent?, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
            val distanceX = e2.x - e1!!.x
            val distanceY = e2.y - e1!!.y
            if (abs(distanceX) > abs(distanceY) && abs(distanceX) > 100 && abs(velocityX) > 100)
                if (distanceX > 0) {
                    val intent = Intent(this@MovieDetailsActivity, MainActivity::class.java)
                    startActivity(intent)
                    return true
                }
            return false
        }
    }
}

