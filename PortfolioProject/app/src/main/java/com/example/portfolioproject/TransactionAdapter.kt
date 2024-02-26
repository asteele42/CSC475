package com.example.portfolioproject

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class TransactionAdapter(context: Context, transaction: MutableList<Transaction>) :
ArrayAdapter<Transaction>(context, 0, transaction) {
    private var transactionList: MutableList<Transaction> = transaction
    private val databaseHelper: DatabaseHelper = DatabaseHelper(context) // Initialize DatabaseHelper

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var accountName = ""
        val itemView = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.transaction_item, parent, false)
        val transaction = getItem(position)
        // Use databaseHelper to get account name by ID
        if(transaction?.accountId != null){
            accountName = if(transaction.accountId is Int){
                databaseHelper.getAccountName(transaction.accountId)
            } else {
                "Account Unknown"
            }
        }

        itemView.findViewById<TextView>(R.id.accountName).text = accountName
        itemView.findViewById<TextView>(R.id.transactionType).text = transaction?.type.toString()
        itemView.findViewById<TextView>(R.id.transactionDesc).text = transaction?.description.toString()
        itemView.findViewById<TextView>(R.id.transactionAmount).text = transaction?.amount.toString()
        itemView.findViewById<TextView>(R.id.transactionBalance).text = transaction?.balance.toString()

        return itemView
    }

    fun addAll() {
        transactionList.clear()
        val transaction = mutableListOf<Transaction>()
        transactionList.addAll(transaction)
        notifyDataSetChanged()
    }
}
