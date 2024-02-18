package com.example.ctmod5
//Alicia Steele CTMOD5
import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.provider.MediaStore
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var imageAdapter: ImageAdapter
    private val images = mutableListOf<GalleryItem>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        recyclerView = findViewById(R.id.galleryRecyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 3) // 3 columns
        imageAdapter = ImageAdapter(images)
        recyclerView.adapter = imageAdapter

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 101)
        } else {
            loadImages()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 101 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            loadImages()
        } else {
            Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadImages() {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null, null, null)
        images.clear()

        cursor?.use {
            val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            while (it.moveToNext()) {
                images.add(GalleryItem(it.getString(columnIndex)))
            }
        }
        imageAdapter.notifyDataSetChanged()
    }
}
