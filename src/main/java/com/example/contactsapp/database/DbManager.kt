package com.example.contactsapp.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder

class DbManager(context: Context) {
    open var Id =1
    val DbName : String = "ContactDB"
    val DbVersion : Int = 1
    val dbTable : String = "ContactList"
    val colId : String = "Id"
    val colName : String = "Name"
    val colNumber : String = "PhoneNumber"
    var sqlDb : SQLiteDatabase? = null
    val sqlCreateTableQuery = "CREATE TABLE IF NOT EXISTS ${dbTable} (" +
            "   ${colId} INTEGER PRIMARY KEY AUTOINCREMENT," +
            "   ${colName} TEXT NOT NULL," +
            "   ${colNumber} TEXT NOT NULL " +
            ");"

    init {
        val db = DbHelper(context)
        sqlDb = db.writableDatabase
    }

    //database creation and upgradation
    inner class DbHelper(var context: Context) : SQLiteOpenHelper(context,DbName,null,DbVersion) {

        override fun onCreate(db: SQLiteDatabase?) {
            db?.execSQL(sqlCreateTableQuery)
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            db?.execSQL("drop table if exists "+dbTable)
        }
    }

    //insert data to table
    fun insertData(values: ContentValues) : Boolean {
        val insertion = sqlDb?.insert(dbTable,"",values)
        return insertion!!>0
    }

    //fetch data from database
    fun fetchData(projection:Array<String>,selection: String,selectionArgs:Array<String>,sortOrder:String) : Cursor{
        val qb = SQLiteQueryBuilder()
        qb.tables = dbTable
        val data : Cursor = qb.query(sqlDb, projection ,selection,selectionArgs,null,null,sortOrder)
        return data
    }

    //update data to table
    fun Update(values:ContentValues, selection:String,selectionargs:Array<String>):Boolean{
        val count=sqlDb!!.update(dbTable,values,selection,selectionargs)
        return count >0
    }

    fun Delete(values: ContentValues, s: String, selectionArgs: Array<String>): Boolean {
        return sqlDb!!.delete(dbTable,s,selectionArgs) >0
    }
}