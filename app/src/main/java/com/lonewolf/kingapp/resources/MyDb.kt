package com.lonewolf.kingapp.resources

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.CursorFactory
import android.database.sqlite.SQLiteOpenHelper

class MyDb(context: Context?, name: String?, factory: CursorFactory?, version: Int) :
    SQLiteOpenHelper(context, name, factory, version) {

    val MainTb = "Main"

    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.execSQL("Create table if not exists ${MainTb}(" +
                "Main_Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Title TEXT," +
                "Content TEXT," +
                "Path TEXT,"+
                "Type TEXT,"+
                "Uploaded TEXT default 'No',"+
                "Created_Datetime TEXT);")


    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    fun  insertNote(title:String, content:String, path:String, type:String):Boolean{
        val db = this.writableDatabase
        onCreate(db)
        val contentValues = ContentValues()

        contentValues.put("Title", title)
        contentValues.put("Content", content)
        contentValues.put("Path", path)
        contentValues.put("Type", type)
        contentValues.put("Created_Datetime", ShortCut_To.getCurrentDatewithTime())



        val userStatus = db.insert(MainTb, null, contentValues)

        return userStatus.equals(-1)
    }

    fun  updateNote(title:String, content:String, path: String, id:Int, uploaded : String):Boolean{
        val db = this.writableDatabase
        onCreate(db)
        val contentValues = ContentValues()

        contentValues.put("Title", title)
        contentValues.put("Content", content)
        contentValues.put("Path", path)
        contentValues.put("Uploaded", uploaded)


        val userStatus = db.update(MainTb, contentValues, "Main_Id = ${id}",null)

        return userStatus.equals(-1)
    }

    fun  insertAudio(title:String, path:String):Boolean{
        val db = this.writableDatabase
        onCreate(db)
        val contentValues = ContentValues()

        contentValues.put("Title", title)
        contentValues.put("Path", path)
        contentValues.put("Content", path)
        contentValues.put("Type", "Audio")


        val userStatus = db.insert(MainTb, null, contentValues)

        return userStatus.equals(-1)
    }

    fun getData(query: String?): Cursor? {
        val db = this.readableDatabase
        onCreate(db)
        return db.rawQuery(query, null)
    }



}