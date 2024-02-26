package com.example.portfolioproject

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class SpinnerAdapter(context: Context, settings: MutableList<Settings>) :
    ArrayAdapter<Settings>(context, android.R.layout.simple_spinner_item, settings) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent)
        val item = getItem(position)
        val textView = view.findViewById<TextView>(android.R.id.text1)
        textView.text = item?.desc
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getDropDownView(position, convertView, parent)
        val item = getItem(position)
        val textView = view.findViewById<TextView>(android.R.id.text1)
        textView.text = item?.desc
        return view
    }
}
