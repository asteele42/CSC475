package com.example.portfolioproject.fragments
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.portfolioproject.R

class SettingsFragment : Fragment() {

    private lateinit var addTransactionTypeButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_settings, container, false)
        addTransactionTypeButton = view.findViewById(R.id.addTransactionTypeButton)
        addTransactionTypeButton.setOnClickListener {
            // Open dialog to add/edit transaction type
            // Implement dialog logic
        }
        return view
    }

    // Implement list logic and dialog interaction
}
