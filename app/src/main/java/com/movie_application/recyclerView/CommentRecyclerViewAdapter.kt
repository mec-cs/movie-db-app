package com.movie_application.recyclerView

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.movie_application.comments.Comment
import com.movie_application.R

class CommentRecyclerViewAdapter(private val context: Context): RecyclerView.Adapter<CommentRecyclerViewAdapter.RecyclerViewItemHolder>() {
    private var recyclerItemValues = emptyList<Comment>()
    fun setData(items:List<Comment>){
        recyclerItemValues = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerViewItemHolder {
        val inflator = LayoutInflater.from(viewGroup.context)
        val itemView: View = inflator.inflate(R.layout.comment_recycler_layout, viewGroup, false)
        return RecyclerViewItemHolder(itemView)
    }

    override fun onBindViewHolder(myRecyclerViewItemHolder: RecyclerViewItemHolder, position: Int) {
        val item = recyclerItemValues[position]
        myRecyclerViewItemHolder.tvCommentOwner.text = item.owner
        myRecyclerViewItemHolder.tvComment.text = item.comment
    }

    override fun getItemCount(): Int {
        return recyclerItemValues.size
    }

    inner class RecyclerViewItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var tvComment: TextView
        lateinit var tvCommentOwner: TextView
        init {
            tvComment = itemView.findViewById(R.id.commentTextView)
            tvCommentOwner = itemView.findViewById(R.id.commentOwnerTextView)
        }
    }
}