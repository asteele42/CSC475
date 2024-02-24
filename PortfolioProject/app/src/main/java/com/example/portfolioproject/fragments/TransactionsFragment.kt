package com.example.portfolioproject.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.portfolioproject.TransactionAdapter
import com.example.portfolioproject.DatabaseHelper
import com.example.portfolioproject.R

class TransactionsFragment : Fragment() {
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var addTransactionButton: Button
    private lateinit var transactionsListView: ListView
    private var adapter: TransactionAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_transactions, container, false)
        databaseHelper = DatabaseHelper(requireContext())
        transactionsListView = view.findViewById(R.id.transactionsList)
        addTransactionButton = view.findViewById(R.id.addTransactionButton)
        addTransactionButton.setOnClickListener {
            showAddTransactionDialog()
        }

        val addTransactionButton = view.findViewById<Button>(R.id.addTransactionButton)
        addTransactionButton.setOnClickListener {
            showAddTransactionDialog()
        }

        loadTransactions()
        return view
    }

    private fun showAddTransactionDialog() {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_add_transaction, null)
        val accountNameInput = dialogView.findViewById<EditText>(R.id.accountNameInput)
        val transactionTypeInput = dialogView.findViewById<EditText>(R.id.transactionTypeInput)
        val transactionAmount = dialogView.findViewById<EditText>(R.id.transactionAmount)

        context?.let {
            AlertDialog.Builder(it).apply {
                setView(dialogView)
                setTitle("Add New Transaction")
                setPositiveButton("Save") { dialog, _ ->
                    val name = accountNameInput.text.toString()
                    val type = transactionTypeInput.text.toString()
                    val balance = transactionAmount.text.toString().toDoubleOrNull() ?: 0.0
                    databaseHelper.addTransaction(name, type, balance)
                    updateTransactionsList()
                    loadTransactions()
                    dialog.dismiss()
                }
                setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
                show()
            }
        }
    }

    private fun updateTransactionsList() {
        val updatedTransactions = databaseHelper.getAllTransactions()
        databaseHelper.getAllTransactions()
        adapter?.clear()
        adapter?.addAll()
        adapter?.notifyDataSetChanged()
    }

    private fun loadTransactions() {
        val transactions = databaseHelper.getAllTransactions()
        adapter = TransactionAdapter(requireContext(), transactions)
        transactionsListView.adapter = adapter
    }
}

private fun TransactionAdapter?.clear() {
    TODO("Not yet implemented")
}
