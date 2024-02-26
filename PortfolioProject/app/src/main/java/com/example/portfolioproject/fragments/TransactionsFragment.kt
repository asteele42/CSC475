package com.example.portfolioproject.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.portfolioproject.DatabaseHelper
import com.example.portfolioproject.Settings
import com.example.portfolioproject.R
import com.example.portfolioproject.SpinnerAdapter
import com.example.portfolioproject.TransactionAdapter

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
        val context: Context = requireContext()
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_add_transaction, null)
        val accountNameInput = dialogView.findViewById<EditText>(R.id.accountNameInput)
        val transactionAmount = dialogView.findViewById<EditText>(R.id.transactionAmount)
        val typeSpinner: Spinner = dialogView.findViewById(R.id.transactionType)
        val descSpinner: Spinner = dialogView.findViewById(R.id.transactionDescSP)
        val settings = databaseHelper.getAllTransactionDesc()
        val adapterSP = SpinnerAdapter(context, settings)
        descSpinner.adapter = adapterSP
        val adapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(
            context, R.array.dropdown_items, android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        typeSpinner.adapter = adapter
        var accountID = 0
        var balance = 0.0


        context.let {
            AlertDialog.Builder(it).apply {
                setView(dialogView)
                setTitle("Add New Transaction")
                setPositiveButton("Save") { dialog, _ ->
                    val name = accountNameInput.text.toString()
                    val type = typeSpinner.selectedItem.toString()
                    val settings = descSpinner.selectedItem as Settings
                    val desc = settings.desc
                    val amount = transactionAmount.text.toString().toDoubleOrNull() ?: 0.0
                    accountID = databaseHelper.getAccountID(name)
                    balance = databaseHelper.calcBalance(accountID, amount, type)
                    databaseHelper.addTransaction(accountID, type, desc, amount, balance)
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
        adapter?.addAll()
        adapter?.notifyDataSetChanged()
    }

    private fun loadTransactions() {
        val transactions = databaseHelper.getAllTransactions()
        adapter =  TransactionAdapter(requireContext(), transactions)
        transactionsListView.adapter = adapter
    }
}

