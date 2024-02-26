package com.example.portfolioproject

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class SettingsAdapter(context: Context, settings: MutableList<Settings>) :
    ArrayAdapter<Settings>(context, 0, settings) {
    private var settingsList: MutableList<Settings> = settings

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val itemView = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.settings_item, parent, false)
        val settings = getItem(position)
        // Use databaseHelper to get account name by ID
        itemView.findViewById<TextView>(R.id.transactionTypeName).text = settings?.desc

        return itemView
        }



    fun addAll() {
        settingsList.clear()
        val settings = mutableListOf<Settings>()
        settingsList.addAll(settings)
        notifyDataSetChanged()
    }
}