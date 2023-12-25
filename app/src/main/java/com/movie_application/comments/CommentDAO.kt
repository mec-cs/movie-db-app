package com.movie_application.comments

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.movie_application.constants.Constants


//Data Access Object: It contains all the methods used for accessing to the database. Inside it all the required queries will be created
@Dao
interface CommentDAO {
    // The conflict strategy defines what happens,if there is an existing entry.
    // The default action is ABORT.
    //@Insert(onConflict = OnConflictStrategy.IGNORE) //if there is a conflict it will be ignored, if there is a new customer with the same data it will jut ignored
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertComment(comment: Comment) // suspend is written because it will be used with coroutine
    @Update
    fun updateComment(comment: Comment)
    @Delete
    fun deleteComment(comment: Comment)
    @Query("DELETE FROM ${Constants.COM_TABLE_NAME}")
    fun deleteAllComments()
    @Query("SELECT * FROM ${Constants.COM_TABLE_NAME} ORDER BY id ASC")
    fun getAllComments():LiveData<List<Comment>>
    @Query("SELECT * FROM ${Constants.COM_TABLE_NAME} WHERE id =:id")
    fun getCommentById(id:Int): Comment
    @Query("SELECT * FROM ${Constants.COM_TABLE_NAME} WHERE movie_name LIKE :searchKey ")
    fun getCommentsBySearchKey(searchKey:String): LiveData<List<Comment>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllComment(comments: ArrayList<Comment>){
        comments.forEach{
            insertComment(it)
        }
    }

}