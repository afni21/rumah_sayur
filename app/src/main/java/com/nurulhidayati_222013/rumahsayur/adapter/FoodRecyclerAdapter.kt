package com.nurulhidayati_222013.rumahsayur.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nurulhidayati_222013.rumahsayur.databinding.FruitCardBinding
import com.nurulhidayati_222013.rumahsayur.model.Food

class FoodRecyclerAdapter : RecyclerView.Adapter<FoodRecyclerAdapter.FruitViewHolder>() {
    private var categoryList:List<Food> = ArrayList()
    private lateinit var onItemClick: OnItemFruitClicked
    private lateinit var onLongCategoryClick:OnLongFruitClick

    fun setFruitList(categoryList: List<Food>){
        this.categoryList = categoryList
        notifyDataSetChanged()
    }

    fun setOnLongFruitClick(onLongCategoryClick:OnLongFruitClick){
        this.onLongCategoryClick = onLongCategoryClick
    }



    fun onItemClicked(onItemClick: OnItemFruitClicked){
        this.onItemClick = onItemClick
    }

    class FruitViewHolder(val binding:FruitCardBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FruitViewHolder {
        return FruitViewHolder(FruitCardBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: FruitViewHolder, position: Int) {
        holder.binding.apply {
            textSayur.text = categoryList[position].strName
            textharga.text = categoryList[position].strPrice

            Glide.with(holder.itemView)
                .load(categoryList[position].strImage)
                .into(imgSayur)
//            Glide.with(holder.itemView)
//                .load(food.strImage)
//                .placeholder(R.drawable.placeholder_image) // Tambahkan placeholder jika diperlukan
//                .error(R.drawable.error_image) // Tambahkan gambar kesalahan jika diperlukan
//                .into(imgSayur)
        }

        holder.itemView.setOnClickListener {
            onItemClick.onClickListener(categoryList[position])
        }

    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    interface OnItemFruitClicked{
        fun onClickListener(food: Food)
    }

    interface OnLongFruitClick{
        fun onCategoryLongCLick(food: Food)
    }
}