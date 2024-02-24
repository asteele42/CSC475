package com.example.portfolioproject.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.portfolioproject.DatabaseHelper
import com.example.portfolioproject.R

class ProfileFragment : Fragment() {

    private lateinit var databaseHelper: DatabaseHelper
    private var firstNameEditText: EditText? = null
    private var lastNameEditText: EditText? = null
    private var emailEditText: EditText? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_profile, container, false)
        databaseHelper = DatabaseHelper(requireContext())

        firstNameEditText = view.findViewById(R.id.firstName)
        lastNameEditText = view.findViewById(R.id.lastName)
        emailEditText = view.findViewById(R.id.email)

        val saveButton = view.findViewById<Button>(R.id.saveProfileButton)
        saveButton.setOnClickListener {
            saveProfile()
        }

        return view
    }

    private fun saveProfile() {
        val firstName = firstNameEditText?.text.toString()
        val lastName = lastNameEditText?.text.toString()
        val email = emailEditText?.text.toString()

        // Save the profile data to the database
        databaseHelper.addProfile(firstName, lastName, email)

        // Update the UI with the saved data
        updateUI(firstName, lastName, email)
        Toast.makeText(context, "Profile saved successfully", Toast.LENGTH_SHORT).show()
    }

    private fun updateUI(firstName: String, lastName: String, email: String) {
        firstNameEditText?.setText(firstName)
        lastNameEditText?.setText(lastName)
        emailEditText?.setText(email)
    }

}