package com.example.belanjaku

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.belanjaku.adapter.BarangAdapter
import com.example.belanjaku.data.BarangManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

class ItemListActivity : BaseAuthenticatedActivity() {
    private lateinit var rvBarang: RecyclerView
    private lateinit var barangAdapter: BarangAdapter
    private lateinit var barangManager: BarangManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_list)

        barangManager = BarangManager(this)
        setupViews()
        setupRecyclerView()
    }

    private fun setupViews() {
        // Setup toolbar
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Daftar Barang"
        }
    }

    private fun setupRecyclerView() {
        rvBarang = findViewById(R.id.rvBarang)
        rvBarang.layoutManager = LinearLayoutManager(this)

        barangAdapter = BarangAdapter(
            barangList = barangManager.getDaftarBarang(),
            onItemClick = { barang ->
                // Buka detail barang
                Intent(this, ItemDetailActivity::class.java).apply {
                    putExtra("barang_id", barang.id)
                    startActivity(this)
                }
            },
            onDeleteClick = { barang ->
                showDeleteConfirmation(barang.id)
            }
        )

        rvBarang.adapter = barangAdapter
    }

    private fun showDeleteConfirmation(barangId: Long) {
        MaterialAlertDialogBuilder(this)
            .setTitle("Hapus Barang")
            .setMessage("Apakah Anda yakin ingin menghapus barang ini?")
            .setNegativeButton("Batal") { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton("Hapus") { _, _ ->
                barangManager.hapusBarang(barangId)
                updateDaftarBarang()
                showSnackbar("Barang berhasil dihapus")
            }
            .show()
    }

    private fun updateDaftarBarang() {
        barangAdapter.updateData(barangManager.getDaftarBarang())
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        updateDaftarBarang()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
