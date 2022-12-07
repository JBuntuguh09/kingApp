package com.lonewolf.kingapp.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.lonewolf.kingapp.resources.Storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class NoteViewModel(application: Application) : AndroidViewModel(application) {

    val liveData : LiveData<List<Note>>
    val liveDataSingle : LiveData<List<Note>>

    private val repo : NoteRepo

    init {
        val notedb = DBMain.getInstance(application).noteDao()
        val storage = Storage(application)
        repo = NoteRepo(notedb, storage.randVal!!)
        liveData = repo.liveData
        liveDataSingle = repo.liveDataSingle


    }

    fun insertNote(note: Note){
        viewModelScope.launch(Dispatchers.IO) {
            repo.insertNote(note)
        }
    }

    fun updateNote(note: Note){
        viewModelScope.launch(Dispatchers.IO) {
            repo.updateNote(note)
        }
    }


    fun deleteNote(nVal : Int){
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteNote(nVal)
        }
    }

    fun deleteallNotes(){
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteAllNotes()
        }
    }

}