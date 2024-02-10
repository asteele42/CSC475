package com.example.ctmod3
//Alicia Steele CSC474 - CT3
import android.content.ContentValues
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    private lateinit var dbHelper: DbHelper
    private lateinit var editText: EditText
    private lateinit var addButton: Button
    private lateinit var deleteCompletedButton: Button
    private lateinit var listView: ListView
    private val itemList = mutableListOf<String>()
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        // Initialize views
        editText = findViewById(R.id.editText)
        addButton = findViewById(R.id.addButton)
        deleteCompletedButton = findViewById(R.id.deleteButton)
        listView = findViewById(R.id.listView)

        // Initialize database helper
        dbHelper = DbHelper(this)

        // Initialize adapter
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, itemList)
        listView.adapter = adapter

        // Load items from database
        loadItemsFromDatabase()

        // Set click listener for the list items
        listView.setOnItemClickListener { _, _, position, _ ->
            toggleItemCompletion(position)
        }

        // Set click listener for the button
        addButton.setOnClickListener {
            addItemToList()
        }

        // Set click listener for the "Delete Completed" button
        deleteCompletedButton.setOnClickListener {
            deleteCompletedItems()
        }
    }

    private fun addItemToList() {
        val newItem = editText.text.toString().trim()
        if (newItem.isNotEmpty()) {
            dbHelper.addItem(newItem, completed = false)
            itemList.add(newItem)
            adapter.notifyDataSetChanged()
            editText.text.clear()
        }
    }

    private fun loadItemsFromDatabase() {
        itemList.clear()
        itemList.addAll(dbHelper.getAllItems())
        adapter.notifyDataSetChanged()
    }

    private fun deleteCompletedItems() {
        dbHelper.deleteAllItems()
        loadItemsFromDatabase() // Refresh the list view
    }

    private fun toggleItemCompletion(position: Int) {
        val item = itemList[position]
        val completed = item.startsWith("Completed:")

        dbHelper.updateItem(item, !completed)
        loadItemsFromDatabase() // Refresh the list view
    }
}