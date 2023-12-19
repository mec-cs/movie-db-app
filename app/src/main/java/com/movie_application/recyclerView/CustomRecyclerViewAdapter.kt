package com.movie_application.recyclerView


import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.movie_application.database.Movie
import com.movie_application.R


class CustomRecyclerViewAdapter(private val context: Context):RecyclerView.Adapter<CustomRecyclerViewAdapter.RecyclerViewItemHolder>() {
    private var recyclerItemValues = emptyList<Movie>()

    fun setData(items:MutableList<Movie>){
        recyclerItemValues = items
        notifyDataSetChanged()
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

        /*
        A powerful image downloading and caching library for Android
        https://square.github.io/picasso/
         */
        /*
        Picasso.get().load(imgUrlAddress)
            .resize(100,100) //optional, Transform images to better fit into layouts and to reduce memory size.
            .centerCrop() //optional, Transform images to better fit into layouts and to reduce memory size.
            .error(R.drawable.ic_launcher_background)//optional, Picasso supports both download and error placeholders as optional features
            .into(myRecyclerViewItemHolder.imgItemRecipe) //taken image will be displayed on imgItemRecipe view.
        */
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
        Glide.with(context)
            .load(imgUrlAddress)
            .override(400)
            .error(R.drawable.ic_launcher_background)
            .into(myRecyclerViewItemHolder.imgItemMovie)
    }

    override fun getItemCount(): Int {
        //Toast.makeText(context, recyclerItemValues.size.toString(), Toast.LENGTH_LONG).show()
        return recyclerItemValues.size
    }

    inner class RecyclerViewItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var itemLayout: LinearLayout
        lateinit var tvItemMovieName: TextView
        lateinit var btnItemDetail: TextView
        lateinit var imgItemMovie: ImageView
        init {
            itemLayout = itemView.findViewById(R.id.moviesLayout)
            tvItemMovieName = itemView.findViewById(R.id.filmNameTextView)
            imgItemMovie = itemView.findViewById(R.id.filmPosterImageView)

        }
    }
}
