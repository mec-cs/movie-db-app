package com.movie_application.comments

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.movie_application.constants.Constants

@Database(
    entities = [Comment::class],
    version = 1,
    exportSchema = false
)
abstract class CommentRoomDatabase : RoomDatabase() {
    abstract fun commentDao(): CommentDAO

    companion object{
        @Volatile  //it makes that instance to visible to other threads
        private var INSTANCE: CommentRoomDatabase?=null

        fun getDatabase(context:Context): CommentRoomDatabase {
            val tempInstance = INSTANCE
            if(tempInstance !=null){
                return  tempInstance
            }

            synchronized(this){
                val  instance = Room.databaseBuilder(context.applicationContext, CommentRoomDatabase::class.java, Constants.COM_DATABASE_NAME).build()
                INSTANCE = instance
                return instance
            }
        }

    }
}
