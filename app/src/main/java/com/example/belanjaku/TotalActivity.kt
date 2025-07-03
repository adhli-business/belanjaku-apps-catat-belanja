package com.example.belanjaku

import android.os.Bundle
import android.widget.TextView
import com.example.belanjaku.data.BarangManager
import com.google.android.material.button.MaterialButton
import java.text.NumberFormat
import java.util.Locale

class TotalActivity : BaseAuthenticatedActivity() {
    private lateinit var tvTotalHarga: TextView
    private lateinit var btnKembali: MaterialButton
    private lateinit var barangManager: BarangManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_total)

        barangManager = BarangManager(this)
        setupViews()
        setupListeners()
        displayTotal()
    }

    private fun setupViews() {
        tvTotalHarga = findViewById(R.id.tvTotalHarga)
        btnKembali = findViewById(R.id.btnKembali)

        // Setup toolbar
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Total Belanja"
        }
    }

    private fun setupListeners() {
        btnKembali.setOnClickListener {
            finish()
        }
    }

    private fun displayTotal() {
        val total = barangManager.getTotalHarga()
        val formatRupiah = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
        tvTotalHarga.text = formatRupiah.format(total.toDouble())
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
