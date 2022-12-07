package com.lonewolf.kingapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface NoteDao{

    @Insert
    fun insertNote(note: Note)

    @Update
    fun updateNote(note: Note)

    @Query("select * from Note")
    fun getAllNotes() : LiveData<List<Note>>

    @Query("select * from Note where Note_Id == :nVal")
    fun getNote(nVal : String) : LiveData<List<Note>>

    @Query("delete from Note where Note_Id == :nId")
    fun deleteNote(nId : Int)

    @Query("delete from Note ")
    fun deleteAllNotes()

}