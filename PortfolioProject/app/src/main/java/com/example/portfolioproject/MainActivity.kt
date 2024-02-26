package com.example.portfolioproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.portfolioproject.fragments.AccountsFragment
import com.example.portfolioproject.fragments.ProfileFragment
import com.example.portfolioproject.fragments.SettingsFragment
import com.example.portfolioproject.fragments.TransactionsFragment


class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_profile -> {

                    replaceFragment(ProfileFragment())
                    true
                }
                R.id.navigation_accounts -> {
                    replaceFragment(AccountsFragment())
                    true
                }
                R.id.navigation_transactions -> {
                    replaceFragment(TransactionsFragment())
                    true
                }
                R.id.navigation_settings -> {
                    replaceFragment(SettingsFragment())
                    true
                }
                else -> false
            }
        }

        // Default view
        bottomNavigationView.selectedItemId = R.id.navigation_accounts
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, fragment)
            commit()
        }
    }
}
