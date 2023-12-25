package com.movie_application.comments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CommentViewModel(application:Application):AndroidViewModel(application) {
    val readAllData: LiveData<List<Comment>>

    private val repository: CommentRepository
    init {
        val commentDAO= CommentRoomDatabase.getDatabase(application).commentDao()
        repository = CommentRepository(commentDAO)
        readAllData = repository.readAlldata
    }
    fun addComments(comment: Comment){
        viewModelScope.launch(Dispatchers.IO){ // that code will be run in background thread, coroutine scope
            repository.insertComment(comment)

        }
    }
    fun addComments(comments: List<Comment>){
        viewModelScope.launch(Dispatchers.IO) { // that code will be run in background thread, coroutine scope
            comments.forEach{
                repository.insertComment(it)
            }
        }
    }
    fun deleteComment(comment: Comment){
        viewModelScope.launch(Dispatchers.IO){ // that code will be run in background thread, coroutine scope
            repository.deleteComment(comment)
        }
    }
    fun deleteAllComments(){
        viewModelScope.launch(Dispatchers.IO){ // that code will be run in background thread, coroutine scope
            repository.deleteAllComments()
        }
    }
    fun updateComment(comment: Comment){
        viewModelScope.launch(Dispatchers.IO){ // that code will be run in background thread, coroutine scope
            repository.updateComment(comment)
        }
    }
    fun getAllComments(){
        viewModelScope.launch(Dispatchers.IO){ // that code will be run in background thread, coroutine scope
            repository.getAllComments()
        }
    }
    fun searchComment(searchkey:String):LiveData<List<Comment>>{
        return repository.getCommentsBySearchKey(searchkey)
    }

}