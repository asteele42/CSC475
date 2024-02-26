package com.example.portfolioproject.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.portfolioproject.DatabaseHelper
import com.example.portfolioproject.R
import com.example.portfolioproject.Settings
import com.example.portfolioproject.SettingsAdapter

class SettingsFragment : Fragment() {
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var addTransactionTypeButton: Button
    private lateinit var settingsListView: ListView
    private var descEditView: EditText? = null
    private var adapter: SettingsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_settings, container, false)
        databaseHelper = DatabaseHelper(requireContext())
        descEditView = view.findViewById(R.id.transactionDESCEdit)
        addTransactionTypeButton = view.findViewById(R.id.addTransactionTypeButton)
        settingsListView = view.findViewById((R.id.settingsList))
        addTransactionTypeButton.setOnClickListener {
            saveTransactionDescription()
        }
        settingsListView.setOnItemClickListener { parent, view, position, id ->
            val settings = parent.getItemAtPosition(position) as Settings
            val selectedItem = settings.desc
            deleteItem(selectedItem)
        }
        loadTransactionDesc()
        return view
    }

    private fun saveTransactionDescription() {
        val desc = descEditView?.text.toString()
        databaseHelper.addTDesc(desc)

        // Update the UI with the saved data
        updateDescList()
        loadTransactionDesc()
        Toast.makeText(context, "Transaction Type saved successfully", Toast.LENGTH_SHORT).show()
    }

    private fun deleteItem(selectedItem: String){
            val context: Context = requireContext()

            // Create an AlertDialog Builder
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Delete Item")
            val message = "Are you sure you want to delete $selectedItem?"
            builder.setMessage(message)

            // Set the positive button for the dialog
            builder.setPositiveButton("Yes") { dialog, which ->
                databaseHelper.deleteDescItem(selectedItem)
                loadTransactionDesc()
            }
            builder.setNegativeButton("No") { dialog, which ->
                dialog.dismiss()
            }

            // Create and show the AlertDialog
            val dialog = builder.create()
            dialog.show()
        }

    private fun updateDescList() {
        val updatedSettings = databaseHelper.getAllTransactionDesc()
        databaseHelper.getAllTransactionDesc()
        adapter?.addAll()
        adapter?.notifyDataSetChanged()
    }

    private fun loadTransactionDesc() {
        val settings = databaseHelper.getAllTransactionDesc()
        if(settings != null){
            adapter =  SettingsAdapter(requireContext(), settings)
            settingsListView.adapter = adapter
        }

    }
}
