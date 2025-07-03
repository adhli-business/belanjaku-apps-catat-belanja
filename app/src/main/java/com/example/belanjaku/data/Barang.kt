package com.example.belanjaku.data

data class Barang(
    val id: Long = System.currentTimeMillis(),
    val nama: String,
    val harga: Int
)
