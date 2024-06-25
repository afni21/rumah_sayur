package com.nurulhidayati_222013.rumahsayur.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nurulhidayati_222013.rumahsayur.databinding.PesananCardBinding
import com.nurulhidayati_222013.rumahsayur.model.Pesanan

class PesananRecyclerAdapter : RecyclerView.Adapter<PesananRecyclerAdapter.PesananViewHolder>() {
    private var pesananList:List<Pesanan> = ArrayList()
    private lateinit var onItemClick: OnItemPesananClicked
    private lateinit var onLongPesananClick:OnLongPesananClick

    fun setPesananList(pesananList: List<Pesanan>){
        this.pesananList = pesananList
        notifyDataSetChanged()
    }

    fun setOnLongPesananClick(onLongCategoryClick:OnLongPesananClick){
        this.onLongPesananClick = onLongCategoryClick
    }

    fun onItemClicked(onItemClick: OnItemPesananClicked){
        this.onItemClick = onItemClick
    }

    class PesananViewHolder(val binding:PesananCardBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PesananViewHolder {
        return PesananViewHolder(PesananCardBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: PesananViewHolder, position: Int) {
        holder.binding.apply {
            textSayurPesanan.text = pesananList[position].strName
            textHargaPesanan.text = "Rp. " + pesananList[position].intPrice.toString()

            Glide.with(holder.itemView)
                .load(pesananList[position].strImage)
                .into(imgSayurPesanan)

        }

        holder.itemView.setOnClickListener {
            onItemClick.onClickListener(pesananList[position])
        }

    }

    override fun getItemCount(): Int {
        return pesananList.size
    }

    interface OnItemPesananClicked{
        fun onClickListener(pesanan: Pesanan)
    }

    interface OnLongPesananClick{
        fun onPesananLongCLick(pesanan: Pesanan)
    }
}