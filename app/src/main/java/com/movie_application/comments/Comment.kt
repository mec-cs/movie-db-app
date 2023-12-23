package com.movie_application.comments

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.movie_application.constants.Constants

@Entity(tableName = Constants.COM_TABLE_NAME)
class Comment (
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0,
    var comment: String,
    @ColumnInfo(name = "comment_owner")
    var owner: String,
    @ColumnInfo(name = "movie_name")
    var movieName: String){

}