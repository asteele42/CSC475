package com.example.portfolioproject

import android.content.Context
import android.database.DataSetObserver
import com.example.portfolioproject.Transaction
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class TransactionAdapter(private val transactions: Context, transactions1: MutableList<Transaction>) : RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>(),
    ListAdapter {

    class TransactionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Bind views here
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        // Inflate layout and return ViewHolder
        return TODO("Provide the return value")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        // Bind Transaction data to views
    }

    override fun registerDataSetObserver(observer: DataSetObserver?) {
        TODO("Not yet implemented")
    }

    override fun unregisterDataSetObserver(observer: DataSetObserver?) {
        TODO("Not yet implemented")
    }

    override fun getCount(): Int {
        TODO("Not yet implemented")
    }

    override fun getItem(position: Int): Any {
        TODO("Not yet implemented")
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        TODO("Not yet implemented")
    }

    override fun getViewTypeCount(): Int {
        TODO("Not yet implemented")
    }

    override fun isEmpty(): Boolean {
        TODO("Not yet implemented")
    }

    override fun areAllItemsEnabled(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isEnabled(position: Int): Boolean {
        TODO("Not yet implemented")
    }

    fun addAll() {
        TODO("Not yet implemented")
    }
}
