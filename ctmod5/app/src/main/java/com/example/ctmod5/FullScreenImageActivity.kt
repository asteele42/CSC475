package com.example.ctmod5

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class FullScreenImageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_screen_image)

        // Hide the action bar
        supportActionBar?.hide()

        val imageView = findViewById<ImageView>(R.id.fullScreenImageView)
        val closeButton = findViewById<ImageView>(R.id.closeButton)

        // Load the image using Glide (or your preferred method)
        val imagePath = intent.getStringExtra("imagePath")
        Glide.with(this).load(imagePath).into(imageView)

        // Set the click listener for the close button
        closeButton.setOnClickListener {
            // Finish the activity and return to the previous screen
            finish()
        }
    }
}
