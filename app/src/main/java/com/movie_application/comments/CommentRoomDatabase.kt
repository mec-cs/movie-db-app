package com.movie_application.comments

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.movie_application.constants.Constants

/*
If you change anything on the database like adding a field to table, chnaging type of a filed, deleting a filed, changing the name of the field
exportSchema: to have a version of history of your schema in your caode base, it is not required so assigned as false
 */
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
            /*
            everthing in this block protected from concurrent execution by multiple threads.In this block database instance is created
            same database instance will be used. If many instance are used, it will be so expensive
             */
            synchronized(this){
                val  instance = Room.databaseBuilder(context.applicationContext, CommentRoomDatabase::class.java, Constants.COM_DATABASE_NAME).build()
                INSTANCE = instance
                return instance
            }
        }

    }
}
