package com.example.belanjaku.data

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class BarangManager(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("BarangPrefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    companion object {
        private const val KEY_BARANG_LIST = "barang_list"
    }

    fun simpanBarang(barang: Barang) {
        val barangList = getDaftarBarang().toMutableList()
        barangList.add(barang)
        simpanDaftarBarang(barangList)
    }

    fun getDaftarBarang(): List<Barang> {
        val json = sharedPreferences.getString(KEY_BARANG_LIST, "[]")
        val type = object : TypeToken<List<Barang>>() {}.type
        return gson.fromJson(json, type) ?: emptyList()
    }

    fun getBarangById(id: Long): Barang? {
        return getDaftarBarang().find { it.id == id }
    }

    fun hapusBarang(id: Long) {
        val barangList = getDaftarBarang().toMutableList()
        barangList.removeAll { it.id == id }
        simpanDaftarBarang(barangList)
    }

    fun getTotalHarga(): Int {
        return getDaftarBarang().sumOf { it.harga }
    }

    private fun simpanDaftarBarang(barangList: List<Barang>) {
        val json = gson.toJson(barangList)
        sharedPreferences.edit().putString(KEY_BARANG_LIST, json).apply()
    }
}
