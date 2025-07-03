package com.example.belanjaku

import android.os.Bundle
import com.example.belanjaku.data.Barang
import com.example.belanjaku.data.BarangManager
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout

class AddItemActivity : BaseAuthenticatedActivity() {
    private lateinit var tilNamaBarang: TextInputLayout
    private lateinit var tilHargaBarang: TextInputLayout
    private lateinit var btnSimpan: MaterialButton
    private lateinit var barangManager: BarangManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)

        barangManager = BarangManager(this)
        setupViews()
        setupListeners()
    }

    private fun setupViews() {
        tilNamaBarang = findViewById(R.id.tilNamaBarang)
        tilHargaBarang = findViewById(R.id.tilHargaBarang)
        btnSimpan = findViewById(R.id.btnSimpan)

        // Setup toolbar
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Tambah Barang"
        }
    }

    private fun setupListeners() {
        btnSimpan.setOnClickListener {
            if (validateInputs()) {
                simpanBarang()
            }
        }
    }

    private fun validateInputs(): Boolean {
        var isValid = true

        val namaBarang = tilNamaBarang.editText?.text.toString()
        if (namaBarang.isEmpty()) {
            tilNamaBarang.error = "Nama barang harus diisi"
            isValid = false
        } else {
            tilNamaBarang.error = null
        }

        val hargaBarang = tilHargaBarang.editText?.text.toString()
        if (hargaBarang.isEmpty()) {
            tilHargaBarang.error = "Harga barang harus diisi"
            isValid = false
        } else {
            try {
                hargaBarang.toInt()
                tilHargaBarang.error = null
            } catch (e: NumberFormatException) {
                tilHargaBarang.error = "Harga harus berupa angka"
                isValid = false
            }
        }

        return isValid
    }

    private fun simpanBarang() {
        val namaBarang = tilNamaBarang.editText?.text.toString()
        val hargaBarang = tilHargaBarang.editText?.text.toString().toInt()

        val barang = Barang(
            nama = namaBarang,
            harga = hargaBarang
        )

        barangManager.simpanBarang(barang)
        showSuccess("Barang berhasil ditambahkan")
        finish()
    }

    private fun showSuccess(message: String) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT)
            .setBackgroundTint(getColor(android.R.color.holo_green_light))
            .show()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
