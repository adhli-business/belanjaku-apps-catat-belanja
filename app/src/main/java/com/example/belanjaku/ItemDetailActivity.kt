package com.example.belanjaku

import android.os.Bundle
import android.widget.TextView
import com.example.belanjaku.data.BarangManager
import java.text.NumberFormat
import java.util.Locale

class ItemDetailActivity : BaseAuthenticatedActivity() {
    private lateinit var tvNamaBarang: TextView
    private lateinit var tvHargaBarang: TextView
    private lateinit var barangManager: BarangManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_detail)

        barangManager = BarangManager(this)
        setupViews()
        loadItemDetails()
    }

    private fun setupViews() {
        tvNamaBarang = findViewById(R.id.tvNamaBarang)
        tvHargaBarang = findViewById(R.id.tvHargaBarang)

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Detail Barang"
        }
    }

    private fun loadItemDetails() {
        val barangId = intent.getLongExtra("barang_id", -1)
        if (barangId == -1L) {
            finish()
            return
        }

        val barang = barangManager.getBarangById(barangId)
        if (barang == null) {
            finish()
            return
        }

        val formatRupiah = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
        tvNamaBarang.text = barang.nama
        tvHargaBarang.text = formatRupiah.format(barang.harga.toDouble())
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
