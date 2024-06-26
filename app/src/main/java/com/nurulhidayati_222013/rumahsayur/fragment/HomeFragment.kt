package com.nurulhidayati_222013.rumahsayur.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.nurulhidayati_222013.rumahsayur.InputPesanan
import com.nurulhidayati_222013.rumahsayur.R
import com.nurulhidayati_222013.rumahsayur.adapter.FoodRecyclerAdapter
import com.nurulhidayati_222013.rumahsayur.databinding.FragmentHomeBinding
import com.nurulhidayati_222013.rumahsayur.model.Food

class HomeFragment : Fragment() {
    private lateinit var myAdapter: FoodRecyclerAdapter
    private lateinit var binding: FragmentHomeBinding
    private  val db = Firebase.firestore
    private val foodCollection = db.collection("food")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentHomeBinding.inflate(layoutInflater)
        myAdapter = FoodRecyclerAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    companion object {
        const val FRUIT_NAME="com.nurulhidayati_222013.rumahsayur.fragment.fruitname"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val mainFragMVVM = ViewModelProviders.of(this)[MainFragMVVM::class.java]
        showLoadingCase()
        cancelLoadingCase()

        val data1 = hashMapOf(
            "name" to "basb",
            "image" to "https://cdn1-production-images-kly.akamaized.net/d_XeXi__hXPpdP-kOUjugTYc9HU=/500x667/smart/filters:quality(75):strip_icc():format(webp)/kly-media-production/medias/3527238/original/091552700_1627785660-132468207_734819403906396_393591296740971871_n.jpg",
            "price" to 2000,
        )

//        foodCollection.add(data1)

        foodCollection.get()
            .addOnSuccessListener { documents ->
                val foodList = documents.map { document ->
                    val price = document.getLong("price") ?: 0L
                    Food(
                        idFood = document.id,
                        strName = document.getString("name") ?: "",
                        strImage = document.getString("image") ?: "",
                        intPrice = price.toInt(),
                        )
                }
                setCategoryAdapter(foodList)
                Log.d("HomeFragment", "berhasil mengambil data",)

            }
            .addOnFailureListener { exception ->
                // Tangani kesalahan pengambilan data
                Log.e("HomeFragment", "Error getting food documents", exception)
            }

        prepareCategoryRecyclerView()
        myAdapter.onItemClicked(object : FoodRecyclerAdapter.OnItemFruitClicked {
            override fun onClickListener(food: Food) {
                val intent = Intent(activity, InputPesanan::class.java)
                intent.putExtra("idFood", food.idFood)
                intent.putExtra("strName", food.strName)
                intent.putExtra("strImage", food.strImage)
                intent.putExtra("int", food.intPrice)
                startActivity(intent)
            }

        })
    }

    private fun showLoadingCase() {
        binding.apply {
            recViewFruit.visibility = View.VISIBLE
            rootHome.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
        }
    }

    private fun cancelLoadingCase() {
        binding.apply {
            recViewFruit.visibility = View.VISIBLE
            rootHome.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))

        }
    }

    private fun setCategoryAdapter(food: List<Food>) {
        myAdapter.setFruitList(food)
    }

    private fun prepareCategoryRecyclerView() {
        binding.recViewFruit.apply {
            adapter = myAdapter
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
        }
    }

}