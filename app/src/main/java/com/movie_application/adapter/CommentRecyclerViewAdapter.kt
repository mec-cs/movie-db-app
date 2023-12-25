package com.movie_application.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.movie_application.comments.Comment
import com.movie_application.R

class CommentRecyclerViewAdapter(private val context: Context): RecyclerView.Adapter<CommentRecyclerViewAdapter.RecyclerViewItemHolder>() {
    private var recyclerItemValues = emptyList<Comment>()
    private lateinit var onItemClickListener: (Comment) -> Unit

    fun setOnItemClickListener(listener: (Comment) -> Unit) {
        onItemClickListener = listener
    }

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
        myRecyclerViewItemHolder.layout.setOnClickListener(){
            onItemClickListener.invoke(item)
        }

    }

    override fun getItemCount(): Int {
        return recyclerItemValues.size
    }

    inner class RecyclerViewItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvComment: TextView
        var tvCommentOwner: TextView
        var layout: LinearLayout
        init {
            layout=itemView.findViewById(R.id.commentLayout)
            tvComment = itemView.findViewById(R.id.commentTextView)
            tvCommentOwner = itemView.findViewById(R.id.commentOwnerTextView)
        }
    }
}
