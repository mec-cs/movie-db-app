package com.movie_application.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.movie_application.comments.Comment
import com.movie_application.comments.CommentSys
import com.movie_application.comments.CommentViewModel
import com.movie_application.databinding.FragmentCommentBinding

class CommentFragment : Fragment() {
    private lateinit var binding: FragmentCommentBinding
    private lateinit var commentViewModel: CommentViewModel
    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCommentBinding.inflate(inflater, container, false)
        commentViewModel = ViewModelProvider(this).get(CommentViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.commentSharebutton.setOnClickListener {
            if(!binding.commentText.text.toString().isEmpty()){
                val comment = Comment(0, binding.commentText.text.toString(), "Anonymous", CommentSys.movieName)
                commentViewModel.addComments(comment)
                Toast.makeText(context, "Your comment is added!!!", Toast.LENGTH_SHORT).show()

            }
            else{
                Toast.makeText(context, "Enter a comment!!!", Toast.LENGTH_SHORT).show()
            }
        }
    }


}