package com.movie_application.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.movie_application.R
import com.movie_application.database.FavoriteMovieSys
import com.movie_application.database.Movie
import com.squareup.picasso.Picasso

class FavMovieRecyclerViewAdapter(private val context: Context): RecyclerView.Adapter<FavMovieRecyclerViewAdapter.RecyclerViewItemHolder>() {

    var favList = FavoriteMovieSys.favMovieList

    fun addFavMovieData(item: Movie){
        if (!favList.contains(item)) {
            favList
        }
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): RecyclerViewItemHolder {
        val inflator = LayoutInflater.from(viewGroup.context)
        val itemView: View = inflator.inflate(R.layout.movie_story_item, viewGroup, false)
        return RecyclerViewItemHolder(itemView)
    }

    override fun onBindViewHolder(favRecyclerViewItemHolder: RecyclerViewItemHolder, position: Int) {
        val item = favList[position]

        // insert proper movie values into views
        favRecyclerViewItemHolder.tvItemFavMovieName.text = item.title
        Picasso.get().load(item.posterImgLink)
            .fit()
            .error(R.drawable.ic_launcher_background)//optional, Picasso supports both download and error placeholders as optional features
            .into(favRecyclerViewItemHolder.imageViewFavMovie) //taken image will be displayed on imgItemRecipe view.

        favRecyclerViewItemHolder.favMovieItemLayout.setOnClickListener {
            Snackbar.make(it, "${item.toString()}", Snackbar.LENGTH_LONG).show()
        }

    }

    override fun getItemCount(): Int {
        return favList.size
    }

    inner class RecyclerViewItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var favMovieItemLayout: LinearLayout
        var tvItemFavMovieName: TextView
        var imageViewFavMovie: ImageView
        init {
            favMovieItemLayout = itemView.findViewById(R.id.favMovieItemLayout)
            tvItemFavMovieName = itemView.findViewById(R.id.favMovieTextView)
            imageViewFavMovie = itemView.findViewById(R.id.favMovieImageView)
        }
    }

}