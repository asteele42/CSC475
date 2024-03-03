package com.example.mod7ct
import org.junit.Assert.assertEquals
import org.junit.Test

class ConversionUtilTest {

    @Test
    fun celsiusToFahrenheit_conversion_isCorrect() {
        val inputCelsius = 0.0
        val expectedFahrenheit = 32.0
        val resultFahrenheit = ConversionUtility.celsiusToFahrenheit(inputCelsius)
        assertEquals("Conversion from Celsius to Fahrenheit failed", expectedFahrenheit, resultFahrenheit, 0.001)
    }

    @Test
    fun fahrenheitToCelsius_conversion_isCorrect() {
        val inputFahrenheit = 32.0
        val expectedCelsius = 0.0
        val resultCelsius = ConversionUtility.fahrenheitToCelsius(inputFahrenheit)
        assertEquals("Conversion from Fahrenheit to Celsius failed", expectedCelsius, resultCelsius, 0.001)
    }
}
