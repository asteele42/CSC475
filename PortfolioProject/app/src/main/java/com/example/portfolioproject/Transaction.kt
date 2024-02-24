package com.example.portfolioproject


data class Transaction(
    val id: Int,
    val accountId: Int,
    val type: String,
    val amount: Double,
    val balance: Double,
    val timestamp: String
)