package com.movie_application.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.movie_application.database.Movie
import com.movie_application.R
import com.squareup.picasso.Picasso


class MovieRecyclerViewAdapter(private val context: Context):RecyclerView.Adapter<MovieRecyclerViewAdapter.RecyclerViewItemHolder>(){
    private var recyclerItemValues = emptyList<Movie>()
    private lateinit var onItemClickListener: (Movie) -> Unit

    fun setOnItemClickListener(listener: (Movie) -> Unit) {
        onItemClickListener = listener
    }

    fun setData(items: List<Movie>){
        recyclerItemValues = items
        notifyDataSetChanged()
    }

    interface RecyclerAdapterInterface {
        fun displayItem(movie: Movie)
    }

    private var adapterInterface: RecyclerAdapterInterface

    init{
        adapterInterface = context as RecyclerAdapterInterface // with that statement activities which will use the adapter are foreced to implement the interface

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerViewItemHolder {
        val inflator = LayoutInflater.from(viewGroup.context)
        val itemView: View = inflator.inflate(R.layout.movies_recycler_layout, viewGroup, false)
        return RecyclerViewItemHolder(itemView)
    }

    override fun onBindViewHolder(myRecyclerViewItemHolder: RecyclerViewItemHolder, position: Int) {
        val item = recyclerItemValues[position]
        myRecyclerViewItemHolder.tvItemMovieName.text = item.title

        val imgUrlAddress = item.posterImgLink
        Log.d("IMG URL", imgUrlAddress)

        myRecyclerViewItemHolder.itemLayout.setOnClickListener{
            adapterInterface.displayItem(item)
        }

        Picasso.get().load(imgUrlAddress)
            .resize(800,0) //optional, Transform images to better fit into layouts and to reduce memory size.
            .error(R.drawable.ic_launcher_background)//optional, Picasso supports both download and error placeholders as optional features
            .into(myRecyclerViewItemHolder.imgItemMovie) //taken image will be displayed on imgItemRecipe view.

        /*
        Picaso uses its own thread  to send request to server and get the response
        If you write statements after picasso, all these statements will be executed by main UI thread.
        It will not wait for the result of picasso
         */

        /*
        Glide is a popular image loading and caching library for Android applications written in Kotlin.
        Glide is an open-source library that simplifies the process of loading images from the internet,
        local storage, or other sources into your Android app
         */
//        Glide.with(context)
//            .load(imgUrlAddress)
//            .override(400)
//            .error(R.drawable.ic_launcher_background)
//            .into(myRecyclerViewItemHolder.imgItemMovie)

        myRecyclerViewItemHolder.fabAddFav.setOnClickListener {
            onItemClickListener.invoke(item)
        }
    }

    override fun getItemCount(): Int {
        return recyclerItemValues.size
    }

    inner class RecyclerViewItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemLayout: LinearLayout
        var tvItemMovieName: TextView
        var imgItemMovie: ImageView
        var fabAddFav: FloatingActionButton
        init {
            itemLayout = itemView.findViewById(R.id.moviesLayout)
            tvItemMovieName = itemView.findViewById(R.id.filmNameTextView)
            imgItemMovie = itemView.findViewById(R.id.filmPosterImageView)
            fabAddFav = itemView.findViewById(R.id.fabAddFav)
        }
    }

}
