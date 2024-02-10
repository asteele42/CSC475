package com.example.ctmod3
import android.provider.BaseColumns
//Alicia Steele CSC474 - CT3
object DatabaseContract {
    object TaskEntry : BaseColumns {
        const val TABLE_NAME = "tasks"
        const val COLUMN_NAME_ID = "ID"
        const val COLUMN_NAME_TASK = "task"
        const val COLUMN_NAME_COMPLETED = "completed"
    }
}
