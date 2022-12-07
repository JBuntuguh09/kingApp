package com.lonewolf.kingapp.database

import androidx.lifecycle.LiveData

class NoteRepo(private val noteDao: NoteDao, nVal : String){
    val liveData : LiveData<List<Note>> = noteDao.getAllNotes()
    val liveDataSingle : LiveData<List<Note>> = noteDao.getNote(nVal)


    suspend fun insertNote(note:Note){
        noteDao.insertNote(note)
    }

    suspend fun updateNote(note: Note){
        noteDao.updateNote(note)
    }

    suspend fun deleteAllNotes(){
        noteDao.deleteAllNotes()
    }

    suspend fun deleteNote(nVal : Int){
        noteDao.deleteNote(nVal)
    }
}