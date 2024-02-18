package com.example.ctmod5

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


open class GalleryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity) // Use the actual layout name
    }
}