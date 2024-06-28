package com.nurulhidayati_222013.rumahsayur.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nurulhidayati_222013.rumahsayur.databinding.PesananCardBinding
import com.nurulhidayati_222013.rumahsayur.model.Pesanan

class PesananRecyclerAdapter : RecyclerView.Adapter<PesananRecyclerAdapter.PesananViewHolder>() {
    private var pesananList: List<Pesanan> = emptyList()
    private var onItemClick: OnItemPesananClicked? = null
    private var onLongPesananClick: OnLongPesananClick? = null

    // Method to update the list of pesanan
    fun setPesananList(pesananList: List<Pesanan>) {
        this.pesananList = pesananList
        notifyDataSetChanged()
    }

    // Method to set the long click listener
    fun setOnLongPesananClick(onLongPesananClick: OnLongPesananClick) {
        this.onLongPesananClick = onLongPesananClick
    }

    // Method to set the item click listener
    fun onItemClicked(onItemClick: OnItemPesananClicked) {
        this.onItemClick = onItemClick
    }

    // ViewHolder class to hold the views for each pesanan item
    class PesananViewHolder(val binding: PesananCardBinding) : RecyclerView.ViewHolder(binding.root)

    // Method to create ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PesananViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PesananCardBinding.inflate(inflater, parent, false)
        return PesananViewHolder(binding)
    }

    // Method to bind data to ViewHolder
    override fun onBindViewHolder(holder: PesananViewHolder, position: Int) {
        val pesanan = pesananList[position]
        holder.binding.apply {
            textSayurPesanan.text = pesanan.strName
            textHargaPesanan.text = "Rp. ${pesanan.intPrice}"

            Glide.with(holder.itemView.context)
                .load(pesanan.strImage)
                .into(imgSayurPesanan)
        }

        holder.itemView.setOnClickListener {
            onItemClick?.onClickListener(pesanan)
        }

        holder.itemView.setOnLongClickListener {
            onLongPesananClick?.onPesananLongClick(pesanan)
            true
        }
    }

    // Method to return the total number of items
    override fun getItemCount(): Int {
        return pesananList.size
    }

    // Interface for item click listener
    interface OnItemPesananClicked {
        fun onClickListener(pesanan: Pesanan)
    }

    // Interface for item long click listener
    interface OnLongPesananClick {
        fun onPesananLongClick(pesanan: Pesanan)
    }
}
