package com.lonewolf.kingapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Note")
data class Note (
    @PrimaryKey(autoGenerate = true)
    val Note_Id : Int,
    val Title :String,
    val Content : String,
    val Type : String,
    val Path : String,
    val CreatedDateTime : String,
    val UpdatedDateTime : String
        )