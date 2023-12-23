package com.movie_application.view

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.movie_application.comments.CommentSys
import com.movie_application.comments.CommentViewModel
import com.movie_application.database.Movie
import com.movie_application.R
import com.movie_application.recyclerView.CommentRecyclerViewAdapter
import com.movie_application.databinding.ActivityMovieDetailsBinding


class MovieDetailsActivity : AppCompatActivity() {
    lateinit var binding: ActivityMovieDetailsBinding
    lateinit var smallImageViews: List<ImageView>
    lateinit var selectedMovie: Movie

    lateinit var adapter: CommentRecyclerViewAdapter

    private lateinit var commentViewModel: CommentViewModel


    val SIZE_FOR_SMALL_IMAGEVIEW:Int = 89
    val SIZE_FOR_BIG_IMAGEVIEW: Int = 377
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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


        commentViewModel = ViewModelProvider(this).get(CommentViewModel::class.java)
        getData()
    }

    fun setInvisibleImageView(smallImageViews: List<ImageView>) {
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

    fun swapImage(position:Int) {
        var temp = selectedMovie.imagesList[position].toString()
        selectedMovie.imagesList[position] = selectedMovie.imagesList[0].toString()
        selectedMovie.imagesList[0] = temp

        setImageView(smallImageViews[position], selectedMovie.imagesList[position], SIZE_FOR_SMALL_IMAGEVIEW)
        setImageView(smallImageViews[0], selectedMovie.imagesList[0], SIZE_FOR_BIG_IMAGEVIEW)
    }

    fun setImageView(imageview:ImageView, imgLink:String, size:Int){
        Glide.with(this)
            .load(imgLink)
            .override(size)
            .error(R.drawable.ic_launcher_background)
            .into(imageview)
    }
    fun getData() {
        //Whenever data is changed that change will refresh the recyclerview
        commentViewModel.readAllData.observe(this, Observer { comments ->
            //comments = commentViewModel.searchComment(selectedMovie.title)
            adapter.setData(comments)
        })
    }

}

