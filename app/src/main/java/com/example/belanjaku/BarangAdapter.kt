package com.example.belanjaku

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BarangAdapter(private val listBarang: List<Barang>) :
    RecyclerView.Adapter<BarangAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nama: TextView = view.findViewById(R.id.tvNamaBarang)
        val harga: TextView = view.findViewById(R.id.tvHargaBarang)

        init {
            view.setOnClickListener {
                val barang = listBarang[adapterPosition]
                val context = view.context
                val intent = Intent(context, ItemDetailActivity::class.java)
                intent.putExtra("nama", barang.nama)
                intent.putExtra("harga", barang.harga)
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_barang, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val barang = listBarang[position]
        holder.nama.text = barang.nama
        holder.harga.text = "Rp${barang.harga}"
    }

    override fun getItemCount(): Int = listBarang.size
}
