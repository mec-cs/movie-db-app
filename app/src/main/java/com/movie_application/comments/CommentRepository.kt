package com.movie_application.comments

import androidx.lifecycle.LiveData

/*
Used to access multiple data sources. It is used to seperate code and the architecture
 */
class CommentRepository(private val commentDAO: CommentDAO) {
    val readAlldata:LiveData<List<Comment>> = commentDAO.getCommentsBySearchKey(CommentSys.movieName)

    fun insertComment(comment: Comment){
        commentDAO.insertComment(comment)
    }
    fun insertComments(comments:ArrayList<Comment>){
        commentDAO.insertAllComment(comments)
    }
    fun updateComment(comment: Comment){
        commentDAO.updateComment(comment)
    }
    fun deleteComment(comment: Comment){
        commentDAO.deleteComment(comment)
    }
    fun deleteAllComments(){
        commentDAO.deleteAllComments()
    }
    fun getAllComments():LiveData<List<Comment>>{
        return commentDAO.getAllComments()
    }
    fun getCommentById(id:Int): Comment {
        return commentDAO.getCommentById(id)
    }
    fun getCommentsBySearchKey(searchKey:String): LiveData<List<Comment>> {
        return commentDAO.getCommentsBySearchKey(searchKey)
    }
}