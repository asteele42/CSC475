package com.example.ctmod3
//Alicia Steele CSC474 - CT3
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.ctmod3.DatabaseContract.TaskEntry.TABLE_NAME

class DbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
        db.close()
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
        db.close()
    }

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "ToDoList.db"
        private const val SQL_CREATE_ENTRIES =
            "CREATE TABLE ${DatabaseContract.TaskEntry.TABLE_NAME} (" +
                    "${DatabaseContract.TaskEntry.COLUMN_NAME_ID} INTEGER PRIMARY KEY," +
                    "${DatabaseContract.TaskEntry.COLUMN_NAME_TASK} TEXT," +
                    "${DatabaseContract.TaskEntry.COLUMN_NAME_COMPLETED} INTEGER)"
        private const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${DatabaseContract.TaskEntry.TABLE_NAME}"
    }

    fun addItem(item: String, completed: Boolean) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(DatabaseContract.TaskEntry.COLUMN_NAME_TASK, item)
            put(DatabaseContract.TaskEntry.COLUMN_NAME_COMPLETED, 0)
        }
        db.insert(DatabaseContract.TaskEntry.TABLE_NAME, null, values)
        db.close()
    }

    fun getAllItems(): MutableList<String> {
        val itemList = mutableListOf<String>()
        val db = readableDatabase
        val projection = arrayOf(
            DatabaseContract.TaskEntry.COLUMN_NAME_TASK,
            DatabaseContract.TaskEntry.COLUMN_NAME_COMPLETED
        )
        val cursor: Cursor? = db.query(
            DatabaseContract.TaskEntry.TABLE_NAME, projection, null, null,
            null, null, null
        )
        cursor?.use {
            while (it.moveToNext()) {
                val task = it.getString(it.getColumnIndexOrThrow(DatabaseContract.TaskEntry.COLUMN_NAME_TASK))
                val completed = it.getInt(it.getColumnIndexOrThrow(DatabaseContract.TaskEntry.COLUMN_NAME_COMPLETED)) == 1
                if (completed) {
                    itemList.add("Completed: $task")
                } else {
                    itemList.add(task)
                }
            }
        }
        db.close()
        return itemList
    }

    fun updateItem(task: String, completed: Boolean) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(DatabaseContract.TaskEntry.COLUMN_NAME_COMPLETED, if (completed) 1 else 0)
        }
        db.update(
            DatabaseContract.TaskEntry.TABLE_NAME,
            values,
            "${DatabaseContract.TaskEntry.COLUMN_NAME_TASK} = ?",
            arrayOf(task)
        )
        db.close()
    }

    fun deleteAllItems() {
        val db = writableDatabase
        db.delete(DatabaseContract.TaskEntry.TABLE_NAME,
            "${DatabaseContract.TaskEntry.COLUMN_NAME_COMPLETED} = ?", arrayOf("1"))
        db.close()
    }


}