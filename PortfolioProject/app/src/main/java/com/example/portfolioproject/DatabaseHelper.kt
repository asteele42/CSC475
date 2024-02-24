package com.example.portfolioproject

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private var instance: DatabaseHelper? = null
        fun getInstance(context: Context): DatabaseHelper {
            if (instance == null) {
                instance = DatabaseHelper(context.applicationContext)
            }
            return instance!!
        }

        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "portfolio-project.db"
        private const val SQL_CREATE_PROFILE =
            "CREATE TABLE ${DatabaseContract.TaskEntry.TABLE_PROFILE} (" +
                    "${DatabaseContract.TaskEntry.COLUMN_PROFILE_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "${DatabaseContract.TaskEntry.COLUMN_PROFILE_FIRST_NAME} TEXT," +
                    "${DatabaseContract.TaskEntry.COLUMN_PROFILE_LAST_NAME} TEXT," +
                    "${DatabaseContract.TaskEntry.COLUMN_PROFILE_EMAIL} TEXT);"
        private const val SQL_CREATE_ACCOUNTS =
            "CREATE TABLE ${DatabaseContract.TaskEntry.TABLE_ACCOUNTS} (" +
                    "${DatabaseContract.TaskEntry.COLUMN_ACCOUNT_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "${DatabaseContract.TaskEntry.COLUMN_ACCOUNT_NAME} TEXT," +
                    "${DatabaseContract.TaskEntry.COLUMN_ACCOUNT_TYPE} TEXT," +
                    "${DatabaseContract.TaskEntry.COLUMN_ACCOUNT_BALANCE} REAL);"
        private const val SQL_CREATE_TRANSACTIONS =
            "CREATE TABLE ${DatabaseContract.TaskEntry.TABLE_TRANSACTIONS} (" +
                    "${DatabaseContract.TaskEntry.COLUMN_TRANSACTION_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "${DatabaseContract.TaskEntry.COLUMN_TRANSACTION_ACCOUNT_ID} INTEGER," +
                    "${DatabaseContract.TaskEntry.COLUMN_TRANSACTION_TYPE} TEXT," +
                    "${DatabaseContract.TaskEntry.COLUMN_TRANSACTION_AMOUNT} TEXT," +
                    "${DatabaseContract.TaskEntry.COLUMN_TRANSACTION_BALANCE} TEXT," +
                    "${DatabaseContract.TaskEntry.COLUMN_TRANSACTION_TIMESTAMP} DEFAULT CURRENT_TIMESTAMP," +
                    "FOREIGN KEY (${DatabaseContract.TaskEntry.COLUMN_TRANSACTION_ACCOUNT_ID}) REFERENCES " +
                    "${DatabaseContract.TaskEntry.TABLE_ACCOUNTS}(${DatabaseContract.TaskEntry.COLUMN_ACCOUNT_ID});"
        private const val SQL_DELETE_PROFILE = "DROP TABLE IF EXISTS ${DatabaseContract.TaskEntry.TABLE_PROFILE};"
        private const val SQL_DELETE_ACCOUNTS = "DROP TABLE IF EXISTS ${DatabaseContract.TaskEntry.TABLE_ACCOUNTS};"
        private const val SQL_DELETE_TRANSACTIONS = "DROP TABLE IF EXISTS ${DatabaseContract.TaskEntry.TABLE_TRANSACTIONS};"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_PROFILE)
        db.execSQL(SQL_CREATE_ACCOUNTS)
        db.execSQL(SQL_CREATE_TRANSACTIONS)
        //db.close()
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_PROFILE)
        db.execSQL(SQL_DELETE_ACCOUNTS)
        db.execSQL(SQL_DELETE_TRANSACTIONS)
        onCreate(db)
        //db.close()
    }

    fun addProfile(firstName: String, lastName: String, email: String) {
        val values = ContentValues().apply {
            put(DatabaseContract.TaskEntry.COLUMN_PROFILE_FIRST_NAME, firstName)
            put(DatabaseContract.TaskEntry.COLUMN_PROFILE_LAST_NAME, lastName)
            put(DatabaseContract.TaskEntry.COLUMN_PROFILE_EMAIL, email)
        }

        val db = this.writableDatabase
        db.insert(DatabaseContract.TaskEntry.TABLE_PROFILE, null, values)
        //db.close()
    }

    fun addAccount(accountName: String, accountType: String, accountBalance: Double) {

        val values = ContentValues().apply {
            put(DatabaseContract.TaskEntry.COLUMN_ACCOUNT_NAME, accountName)
            put(DatabaseContract.TaskEntry.COLUMN_ACCOUNT_TYPE, accountType)
            put(DatabaseContract.TaskEntry.COLUMN_ACCOUNT_BALANCE, accountBalance)
        }
        val db = this.writableDatabase
        db.insert(DatabaseContract.TaskEntry.TABLE_ACCOUNTS, null, values)
        //db.close()
    }

    fun addTransaction(accountName: String, transactionType: String, transactionAmount: Double) {

        val values = ContentValues().apply {
            put(DatabaseContract.TaskEntry.COLUMN_ACCOUNT_NAME, accountName)
            put(DatabaseContract.TaskEntry.COLUMN_ACCOUNT_TYPE, transactionType)
            put(DatabaseContract.TaskEntry.COLUMN_TRANSACTION_AMOUNT, transactionAmount)
        }
        val db = this.writableDatabase
        db.insert(DatabaseContract.TaskEntry.TABLE_ACCOUNTS, null, values)
        //db.close()
    }

    fun getAllAccounts(): MutableList<Account> {
        val accountsList = mutableListOf<Account>()
        val db = this.readableDatabase
        val tableaccounts = DatabaseContract.TaskEntry.TABLE_ACCOUNTS
        val cursor = db.rawQuery("SELECT * FROM $tableaccounts", null)

        if (cursor.moveToFirst()) {
            do {
                val account = Account(
                    id = cursor.getInt(with(cursor) { getColumnIndex(DatabaseContract.TaskEntry.COLUMN_ACCOUNT_ID) }),
                    name = cursor.getString(with(cursor) { getColumnIndex(DatabaseContract.TaskEntry.COLUMN_ACCOUNT_NAME) }),
                    type = cursor.getString(with(cursor) { getColumnIndex(DatabaseContract.TaskEntry.COLUMN_ACCOUNT_TYPE) }),
                    balance = cursor.getDouble(with(cursor) { getColumnIndex(DatabaseContract.TaskEntry.COLUMN_ACCOUNT_BALANCE) })
                )
                accountsList.add(account)
            } while (cursor.moveToNext())
        }
        cursor.close()
        //db.close()
        return accountsList
    }

    fun getAllTransactions(): MutableList<Transaction> {
            val transactionsList = mutableListOf<Transaction>()
            val db = this.readableDatabase
            val tableTransactions = DatabaseContract.TaskEntry.TABLE_TRANSACTIONS
            val cursor = db.rawQuery("SELECT * FROM $tableTransactions", null)

            if (cursor.moveToFirst()) {
                do {
                    val transaction = Transaction(
                        id = cursor.getInt(with(cursor) { getColumnIndex(DatabaseContract.TaskEntry.COLUMN_TRANSACTION_ID) }),
                        accountId = cursor.getInt(with(cursor) { getColumnIndex(DatabaseContract.TaskEntry.COLUMN_TRANSACTION_ACCOUNT_ID) }),
                        type = cursor.getString(with(cursor) { getColumnIndex(DatabaseContract.TaskEntry.COLUMN_TRANSACTION_TYPE) }),
                        timestamp = cursor.getString(with(cursor) { getColumnIndex(DatabaseContract.TaskEntry.COLUMN_TRANSACTION_TIMESTAMP) }),
                        balance = cursor.getDouble(with(cursor) { getColumnIndex(DatabaseContract.TaskEntry.COLUMN_TRANSACTION_BALANCE) }),
                        amount = cursor.getDouble(with(cursor) { getColumnIndex(DatabaseContract.TaskEntry.COLUMN_TRANSACTION_AMOUNT) })
                    )
                    transactionsList.add(transaction)
                } while (cursor.moveToNext())
            }
            cursor.close()
            //db.close()
            return transactionsList
    }

}
