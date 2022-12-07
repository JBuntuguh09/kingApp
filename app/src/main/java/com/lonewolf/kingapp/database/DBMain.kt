package com.lonewolf.kingapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Note::class], version = 1)
 abstract class DBMain : RoomDatabase() {

    abstract fun noteDao() : NoteDao
    companion object {
        @Volatile
        var instance : DBMain? = null

        fun getInstance(context: Context): DBMain {
            val tempInstance = instance
            if(tempInstance!=null){
                return tempInstance
            }
            synchronized(this) {
                val nInstance = Room.databaseBuilder(
                    context.applicationContext,
                    DBMain::class.java,
                    "question_db"
                ).fallbackToDestructiveMigration()
                    .build()
                instance = nInstance
                return nInstance
            }


        }
    }

}