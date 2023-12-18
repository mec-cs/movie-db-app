package com.movie_application.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.movie_application.constants.Constants
import com.movie_application.converter.Converters

@Database(
    entities = [Movie::class],
    version = 1,
    exportSchema = false,
)
@TypeConverters(Converters::class)
abstract class MovieRoomDatabase: RoomDatabase() {

    abstract fun movieDao(): MovieDAO

    companion object{
        @Volatile  //it makes that instance to visible to other threads
        private var INSTANCE:MovieRoomDatabase?=null

        fun getDatabase(context: Context): MovieRoomDatabase {
            val tempInstance = INSTANCE
            if(tempInstance !=null){
                return  tempInstance
            }

            /*
            everthing in this block protected from concurrent execution by multiple threads.In this block database instance is created
            same database instance will be used. If many instance are used, it will be so expensive
             */
            synchronized(this){
                val  instance = Room.databaseBuilder(context.applicationContext, MovieRoomDatabase::class.java, Constants.DATABASE_NAME).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}