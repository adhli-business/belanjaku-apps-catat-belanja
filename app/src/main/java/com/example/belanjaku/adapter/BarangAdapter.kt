package com.example.belanjaku.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.belanjaku.R
import com.example.belanjaku.data.Barang
import java.text.NumberFormat
import java.util.Locale

class BarangAdapter(
    private var barangList: List<Barang>,
    private val onItemClick: (Barang) -> Unit,
    private val onDeleteClick: (Barang) -> Unit
) : RecyclerView.Adapter<BarangAdapter.BarangViewHolder>() {

    class BarangViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNamaBarang: TextView = view.findViewById(R.id.tvNamaBarang)
        val tvHargaBarang: TextView = view.findViewById(R.id.tvHargaBarang)
        val btnDelete: ImageButton = view.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BarangViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_barang, parent, false)
        return BarangViewHolder(view)
    }

    override fun onBindViewHolder(holder: BarangViewHolder, position: Int) {
        val barang = barangList[position]
        val formatRupiah = NumberFormat.getCurrencyInstance(Locale("id", "ID"))

        holder.tvNamaBarang.text = barang.nama
        holder.tvHargaBarang.text = formatRupiah.format(barang.harga.toDouble())

        holder.itemView.setOnClickListener { onItemClick(barang) }
        holder.btnDelete.setOnClickListener { onDeleteClick(barang) }
    }

    override fun getItemCount() = barangList.size

    fun updateData(newList: List<Barang>) {
        barangList = newList
        notifyDataSetChanged()
    }
}
