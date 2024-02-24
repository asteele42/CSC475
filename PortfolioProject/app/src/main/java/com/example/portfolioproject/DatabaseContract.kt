package com.example.portfolioproject
import android.provider.BaseColumns

object DatabaseContract {
    object TaskEntry : BaseColumns {
        const val TABLE_PROFILE = "profile"
        const val COLUMN_PROFILE_ID = "id"
        const val COLUMN_PROFILE_FIRST_NAME = "first_name"
        const val COLUMN_PROFILE_LAST_NAME = "last_name"
        const val COLUMN_PROFILE_EMAIL = "email"

        const val TABLE_ACCOUNTS = "accounts"
        const val COLUMN_ACCOUNT_ID = "id"
        const val COLUMN_ACCOUNT_NAME = "account_name"
        const val COLUMN_ACCOUNT_TYPE = "account_type"
        const val COLUMN_ACCOUNT_BALANCE = "balance"

        const val TABLE_TRANSACTIONS = "transactions"
        const val COLUMN_TRANSACTION_ID = "id"
        const val COLUMN_TRANSACTION_ACCOUNT_ID = "accountId"
        const val COLUMN_TRANSACTION_TYPE = "type"
        const val COLUMN_TRANSACTION_AMOUNT = "amount"
        const val COLUMN_TRANSACTION_BALANCE = "balance"
        const val COLUMN_TRANSACTION_TIMESTAMP = "timestamp"
    }
}