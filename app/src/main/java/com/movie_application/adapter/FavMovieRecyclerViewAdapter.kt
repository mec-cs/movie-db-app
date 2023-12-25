package com.movie_application.adapter

import android.annotation.SuppressLint
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
class FavMovieRecyclerViewAdapter(private val context: Context) : RecyclerView.Adapter<FavMovieRecyclerViewAdapter.RecyclerViewItemHolder>() {

    var favAdapterInterface: FavMovieRecyclerViewAdapter.FavRecyclerViewInterface

    init{
        favAdapterInterface = context as FavRecyclerViewInterface
    }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): RecyclerViewItemHolder {
        val inflator = LayoutInflater.from(viewGroup.context)
        val itemView: View = inflator.inflate(R.layout.movie_story_item, viewGroup, false)
        return RecyclerViewItemHolder(itemView)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(favRecyclerViewItemHolder: RecyclerViewItemHolder, position: Int) {
        val movie = FavoriteMovieSys.favMovieList[position]

        // insert proper movie values into views
        favRecyclerViewItemHolder.tvItemFavMovieName.text = movie.title
        Picasso.get().load(movie.posterImgLink)
            .fit()
            .error(R.drawable.ic_launcher_background)
            .into(favRecyclerViewItemHolder.imageViewFavMovie)


        favRecyclerViewItemHolder.favMovieItemLayout.setOnClickListener {
            Snackbar.make(it , "Click to see detailed info, Long click to Delete", Snackbar.LENGTH_LONG).show()
            favAdapterInterface.showFavMovie(movie)
        }

        favRecyclerViewItemHolder.favMovieItemLayout.setOnLongClickListener {
            var deletedMoviePos: Int = FavoriteMovieSys.favMovieList.indexOf(movie)
            FavoriteMovieSys.favMovieList.remove(movie)
            notifyItemRemoved(deletedMoviePos)
            return@setOnLongClickListener true
        }
    }

    override fun getItemCount(): Int {
        return FavoriteMovieSys.favMovieList.size
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

    interface FavRecyclerViewInterface {
        fun showFavMovie(movie: Movie)
    }
}

