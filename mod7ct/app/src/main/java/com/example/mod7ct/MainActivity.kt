package com.example.mod7ct

// MainActivity.kt
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val inputCelsius = findViewById<EditText>(R.id.inputCelsius)
        val inputFahrenheit = findViewById<EditText>(R.id.inputFahrenheit)
        val convertToFahrenheitButton = findViewById<Button>(R.id.convertToFahrenheit)
        val convertToCelsiusButton = findViewById<Button>(R.id.convertToCelsius)

        convertToFahrenheitButton.setOnClickListener {
            val celsius = inputCelsius.text.toString().toDoubleOrNull()
            celsius?.let {
                val fahrenheit = ConversionUtility.celsiusToFahrenheit(it)
                inputFahrenheit.setText(fahrenheit.toString())
            }
        }

        convertToCelsiusButton.setOnClickListener {
            val fahrenheit = inputFahrenheit.text.toString().toDoubleOrNull()
            fahrenheit?.let {
                val celsius = ConversionUtility.fahrenheitToCelsius(it)
                inputCelsius.setText(celsius.toString())
            }
        }
    }
}

