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
import com.example.portfolioproject.AccountAdapter
import com.example.portfolioproject.DatabaseHelper
import com.example.portfolioproject.R


class AccountsFragment : Fragment() {
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var addAccountButton: Button
    private lateinit var accountsListView: ListView
    private var adapter: AccountAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_accounts, container, false)
        databaseHelper = DatabaseHelper(requireContext())
        accountsListView = view.findViewById(R.id.accountsList)
        addAccountButton = view.findViewById(R.id.addAccountButton)
        addAccountButton.setOnClickListener {
            showAddAccountDialog()
        }

        val addAccountButton = view.findViewById<Button>(R.id.addAccountButton)
        addAccountButton.setOnClickListener {
            showAddAccountDialog()
        }

        loadAccounts()
        return view
    }

    private fun showAddAccountDialog() {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_add_account, null)
        val accountNameInput = dialogView.findViewById<EditText>(R.id.accountNameInput)
        val accountTypeInput = dialogView.findViewById<EditText>(R.id.accountTypeInput)
        val accountBalanceInput = dialogView.findViewById<EditText>(R.id.accountBalanceInput)

        context?.let {
            AlertDialog.Builder(it).apply {
                setView(dialogView)
                setTitle("Add New Account")
                setPositiveButton("Save") { dialog, _ ->
                    val name = accountNameInput.text.toString()
                    val type = accountTypeInput.text.toString()
                    val balance = accountBalanceInput.text.toString().toDoubleOrNull() ?: 0.0
                    databaseHelper.addAccount(name, type, balance)
                    updateAccountList()
                    loadAccounts()
                    dialog.dismiss()
                }
                setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
                show()
            }
        }
    }

    private fun updateAccountList() {
        val updatedAccounts = databaseHelper.getAllAccounts()
        adapter?.clear()
        adapter?.addAll()
        adapter?.notifyDataSetChanged()
    }

    private fun loadAccounts() {
        val accounts = databaseHelper.getAllAccounts()
        adapter = AccountAdapter(requireContext(), accounts)
        accountsListView.adapter = adapter
    }
}
