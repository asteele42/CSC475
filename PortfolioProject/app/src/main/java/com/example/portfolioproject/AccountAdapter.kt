package com.example.portfolioproject

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class AccountAdapter(context: Context, accounts: MutableList<Account>) :
    ArrayAdapter<Account>(context, 0, accounts) {
    private var accountList: MutableList<Account> = accounts

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val itemView = convertView ?: LayoutInflater.from(context).inflate(R.layout.account_item, parent, false)
        val account = getItem(position)
        itemView.findViewById<TextView>(R.id.accountName).text = account?.name
        itemView.findViewById<TextView>(R.id.accountType).text = account?.type
        itemView.findViewById<TextView>(R.id.accountBalance).text = account?.balance.toString()

        return itemView
    }

    fun addAll() {
        accountList.clear()
        val accounts = mutableListOf<Account>()
        accountList.addAll(accounts)
        notifyDataSetChanged()
    }
}