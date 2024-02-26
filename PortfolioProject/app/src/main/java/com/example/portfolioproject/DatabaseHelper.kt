package com.example.portfolioproject

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {

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
                    "${DatabaseContract.TaskEntry.COLUMN_TRANSACTION_DESC} TEXT," +
                    "${DatabaseContract.TaskEntry.COLUMN_TRANSACTION_AMOUNT} REAL," +
                    "${DatabaseContract.TaskEntry.COLUMN_TRANSACTION_BALANCE} REAL," +
                    "${DatabaseContract.TaskEntry.COLUMN_TRANSACTION_TIMESTAMP} DEFAULT CURRENT_TIMESTAMP);"
        private const val SQL_CREATE_TRANSACTION_DESC =
            "CREATE TABLE ${DatabaseContract.TaskEntry.TABLE_TRANSACTION_DESC} (" +
                    "${DatabaseContract.TaskEntry.COLUMN_TRANSACTION_DESCRIPTION_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "${DatabaseContract.TaskEntry.COLUMN_TRANSACTION_DESCRIPTION} TEXT);"
        private const val SQL_DELETE_PROFILE = "DROP TABLE IF EXISTS ${DatabaseContract.TaskEntry.TABLE_PROFILE};"
        private const val SQL_DELETE_ACCOUNTS = "DROP TABLE IF EXISTS ${DatabaseContract.TaskEntry.TABLE_ACCOUNTS};"
        private const val SQL_DELETE_TRANSACTIONS = "DROP TABLE IF EXISTS ${DatabaseContract.TaskEntry.TABLE_TRANSACTIONS};"
        private const val SQL_DELETE_TRANSACTIONS_DESC = "DROP TABLE IF EXISTS ${DatabaseContract.TaskEntry.TABLE_TRANSACTION_DESC};"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_PROFILE)
        db.execSQL(SQL_CREATE_ACCOUNTS)
        db.execSQL(SQL_CREATE_TRANSACTIONS)
        db.execSQL(SQL_CREATE_TRANSACTION_DESC)

        // Directly insert initial transaction descriptions
        val initialTransactionDescriptions = arrayOf("Expense", "Income", "Savings", "Entertainment")
        initialTransactionDescriptions.forEach { description ->
            val insertSql = "INSERT INTO ${DatabaseContract.TaskEntry.TABLE_TRANSACTION_DESC} (${DatabaseContract.TaskEntry.COLUMN_TRANSACTION_DESCRIPTION}) VALUES ('$description')"
            db.execSQL(insertSql)
        }
    }


    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        try {
            db.execSQL(SQL_DELETE_PROFILE)
            db.execSQL(SQL_DELETE_ACCOUNTS)
            db.execSQL(SQL_DELETE_TRANSACTIONS)
            db.execSQL(SQL_DELETE_TRANSACTIONS_DESC)
            onCreate(db)
        } catch (e: Exception) {
            Log.e("DatabaseHelper", "Error upgrading database: ${e.message}")
        }
    }

    fun addProfile(firstName: String, lastName: String, email: String) {
        val values = ContentValues().apply {
            put(DatabaseContract.TaskEntry.COLUMN_PROFILE_FIRST_NAME, firstName)
            put(DatabaseContract.TaskEntry.COLUMN_PROFILE_LAST_NAME, lastName)
            put(DatabaseContract.TaskEntry.COLUMN_PROFILE_EMAIL, email)
        }
        val db = this.writableDatabase
        try {
            db.insert(DatabaseContract.TaskEntry.TABLE_PROFILE, null, values)
        } catch (e: Exception) {
            Log.e("DatabaseHelper", "Error adding profile: ${e.message}")
        } finally {
            db.close()
        }
    }

    fun addTDesc(transactionDesc: String) {
        val values = ContentValues().apply {
            put(DatabaseContract.TaskEntry.COLUMN_TRANSACTION_DESCRIPTION, transactionDesc)
        }
        val db = this.writableDatabase
        try {
        db.insert(DatabaseContract.TaskEntry.TABLE_TRANSACTION_DESC, null, values)
        } catch (e: Exception) {
            Log.e("DatabaseHelper", "Error adding description: ${e.message}")
        } finally {
            db.close()
        }
    }

    fun addAccount(accountName: String, accountType: String, accountBalance: Double) {

        val values = ContentValues().apply {
            put(DatabaseContract.TaskEntry.COLUMN_ACCOUNT_NAME, accountName)
            put(DatabaseContract.TaskEntry.COLUMN_ACCOUNT_TYPE, accountType)
            put(DatabaseContract.TaskEntry.COLUMN_ACCOUNT_BALANCE, accountBalance)
        }
        val db = this.writableDatabase
        try{
        db.insert(DatabaseContract.TaskEntry.TABLE_ACCOUNTS, null, values)
        } catch (e: Exception) {
            Log.e("DatabaseHelper", "Error adding account: ${e.message}")
        } finally {
            db.close()
        }
    }

    fun addTransaction(transactionAcctID: Int, transactionType: String, transactionDesc: String,
                       transactionAmount: Double, transactionBalance: Double) {
        val db = this.writableDatabase

        val values = ContentValues().apply {
            put(DatabaseContract.TaskEntry.COLUMN_TRANSACTION_ACCOUNT_ID, transactionAcctID)
            put(DatabaseContract.TaskEntry.COLUMN_TRANSACTION_TYPE, transactionType)
            put(DatabaseContract.TaskEntry.COLUMN_TRANSACTION_DESC, transactionDesc)
            put(DatabaseContract.TaskEntry.COLUMN_TRANSACTION_AMOUNT, transactionAmount)
            put(DatabaseContract.TaskEntry.COLUMN_TRANSACTION_BALANCE, transactionBalance)
        }
        try{
        db.insert(DatabaseContract.TaskEntry.TABLE_TRANSACTIONS, null, values)
        updateAccountBalance(transactionAcctID, transactionBalance)
        } catch (e: Exception) {
            Log.e("DatabaseHelper", "Error adding transaction: ${e.message}")
        } finally {
            db.close()
        }
    }

    private fun updateAccountBalance(accountId: Int, newBalance: Double) {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put("balance", newBalance)
        }
        try{
            db.update(DatabaseContract.TaskEntry.TABLE_ACCOUNTS, contentValues, "id = ?", arrayOf(accountId.toString()))
        } catch (e: Exception) {
            Log.e("DatabaseHelper", "Error updating account balance: ${e.message}")
        } finally {
            db.close()
        }
    }

    fun calcBalance(acctID: Int, transactionAmount: Double, type: String): Double {
        var newBalance = 0.0
        val db = this.readableDatabase
        val tableaccounts = DatabaseContract.TaskEntry.TABLE_ACCOUNTS
        val accountBalance = DatabaseContract.TaskEntry.COLUMN_ACCOUNT_BALANCE
        val accountID = DatabaseContract.TaskEntry.COLUMN_ACCOUNT_ID
        val account = acctID.toString()
        val selectionArgs = arrayOf(account)
        val cursor = db.rawQuery("SELECT $accountBalance FROM $tableaccounts WHERE $accountID = ?", selectionArgs)
        val singleValue: String? = if (cursor.moveToFirst()) {
            cursor.getString(cursor.getColumnIndexOrThrow(accountBalance))
        } else {
            null
        }
        val balance = singleValue?.toDouble()
        if (balance != null) {
            newBalance = if(type == "Withdrawal"){
                balance - transactionAmount
            } else {
                balance + transactionAmount
            }
        }
        cursor.close()
        return newBalance
    }

    fun getAccountID(accountName: String): Int {

        val db = this.readableDatabase
        val tableaccounts = DatabaseContract.TaskEntry.TABLE_ACCOUNTS
        val accountID = DatabaseContract.TaskEntry.COLUMN_ACCOUNT_ID
        val accountNameCol = DatabaseContract.TaskEntry.COLUMN_ACCOUNT_NAME
        var account = 0
        val selectionArgs = arrayOf(accountName)
        val cursor = db.rawQuery("SELECT $accountID FROM $tableaccounts WHERE $accountNameCol = ?", selectionArgs)
        val singleValue: String? = if (cursor.moveToFirst()) {
            cursor.getString(cursor.getColumnIndexOrThrow(accountID))
        } else {
            null
        }
        if (singleValue != null) {
            account = singleValue.toInt()
        }
        cursor.close()
        return account

    }

    fun getAccountName(accountID: Int): String {

        val db = this.readableDatabase
        val tableaccounts = DatabaseContract.TaskEntry.TABLE_ACCOUNTS
        var accountName = ""
        val accountNameCol = DatabaseContract.TaskEntry.COLUMN_ACCOUNT_NAME
        val accountIDCol = DatabaseContract.TaskEntry.COLUMN_ACCOUNT_ID
        val selectionArgs = arrayOf(accountID.toString())
        val cursor = db.rawQuery(
            "SELECT $accountNameCol FROM $tableaccounts WHERE $accountIDCol = ?",
            selectionArgs
        )
        accountName = (if (cursor.moveToFirst()) {
            cursor.getString(cursor.getColumnIndexOrThrow(accountNameCol))
        } else {
            null
        }) ?: "Account Unknown"
        cursor.close()
        db.close()
        return accountName

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
        db.close()
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
                        description = cursor.getString(with(cursor) { getColumnIndex(DatabaseContract.TaskEntry.COLUMN_TRANSACTION_DESC) }),
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

    fun getAllTransactionDesc(): MutableList<Settings> {
        val settingsList = mutableListOf<Settings>()
        val db = this.readableDatabase
        val tableSettings = DatabaseContract.TaskEntry.TABLE_TRANSACTION_DESC
        val cursor = db.rawQuery("SELECT * FROM $tableSettings", null)
        if (cursor.moveToFirst()) {
            do {
                val settings = Settings(
                    settingsId = cursor.getInt(with(cursor) { getColumnIndex(DatabaseContract.TaskEntry.COLUMN_TRANSACTION_DESCRIPTION_ID) }),
                    desc = cursor.getString(with(cursor) { getColumnIndex(DatabaseContract.TaskEntry.COLUMN_TRANSACTION_DESCRIPTION) })
                )
                settingsList.add(settings)
            } while (cursor.moveToNext())
        }
        cursor.close()
        //db.close()
        return settingsList
    }

    fun deleteDescItem(itemName: String){
        // Corrected whereClause without "WHERE"
        val whereClause = "${DatabaseContract.TaskEntry.COLUMN_TRANSACTION_DESCRIPTION} = ?"
        val selectionArgs = arrayOf(itemName)
        val db = this.writableDatabase
        db.use { db ->
            db.delete(DatabaseContract.TaskEntry.TABLE_TRANSACTION_DESC, whereClause, selectionArgs)
        }
    }


}
