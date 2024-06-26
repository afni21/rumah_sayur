package com.nurulhidayati_222013.rumahsayur.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nurulhidayati_222013.rumahsayur.databinding.PesananCardBinding
import com.nurulhidayati_222013.rumahsayur.databinding.RiwayatCardBinding
import com.nurulhidayati_222013.rumahsayur.model.Pesanan
import com.nurulhidayati_222013.rumahsayur.model.Riwayat

class RiwayatReciclerdafter : RecyclerView.Adapter<RiwayatReciclerdafter.RiwayatViewHolder>() {
    private var riwayatList:List<Riwayat> = ArrayList()
    private lateinit var onItemClick: OnItemRiwayatClicked
    private lateinit var onLongRiwayatClick:OnLongRiwayatClick

    fun setRiwayatList(riwayatList: List<Riwayat>){
        this.riwayatList = riwayatList
        notifyDataSetChanged()
    }

    fun setOnLongRiwayatClick(onLongCategoryClick:OnLongRiwayatClick){
        this.onLongRiwayatClick = onLongCategoryClick
    }

    fun onItemClicked(onItemClick: OnItemRiwayatClicked){
        this.onItemClick = onItemClick
    }

    class RiwayatViewHolder(val binding:RiwayatCardBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RiwayatViewHolder {
        return RiwayatViewHolder(RiwayatCardBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: RiwayatViewHolder, position: Int) {
        holder.binding.apply {
            textSayurRiwayat.text = riwayatList[position].strName
            txTglRiwayat.text = riwayatList[position].strTanggal
            tvHarga.text = "Rp. " + riwayatList[position].intPrice.toString()
            tvTotal.text = "Rp. " + riwayatList[position].intTotPrice.toString()
            tvOngkir.text = "Rp. " + riwayatList[position].intOngkir.toString()

            Glide.with(holder.itemView)
                .load(riwayatList[position].strImage)
                .into(imgSayurRiwayat)

        }

        holder.itemView.setOnClickListener {
            onItemClick.onClickListener(riwayatList[position])
        }

    }

    override fun getItemCount(): Int {
        return riwayatList.size
    }

    interface OnItemRiwayatClicked{
        fun onClickListener(riwayat: Riwayat)
    }

    interface OnLongRiwayatClick{
        fun onRiwayatLongCLick(riwayat: Riwayat)
    }
}