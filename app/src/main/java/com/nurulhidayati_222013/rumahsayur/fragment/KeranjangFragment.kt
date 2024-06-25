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
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.nurulhidayati_222013.rumahsayur.InputPesanan
import com.nurulhidayati_222013.rumahsayur.R
import com.nurulhidayati_222013.rumahsayur.adapter.PesananRecyclerAdapter
//import com.nurulhidayati_222013.rumahsayur.adapter.PesananRecyclerAdapter
import com.nurulhidayati_222013.rumahsayur.databinding.FragmentKeranjangBinding
import com.nurulhidayati_222013.rumahsayur.model.Pesanan

class KeranjangFragment : Fragment() {
    private lateinit var myAdapter: PesananRecyclerAdapter
    private lateinit var binding: FragmentKeranjangBinding
    private val db = Firebase.firestore
    private val pesananCol = db.collection("pesanan")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentKeranjangBinding.inflate(layoutInflater)
        myAdapter = PesananRecyclerAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_keranjang, container, false)
    }

    companion object {
        const val FRUIT_NAME = "com.nurulhidayati_222013.rumahsayur.fragment.fruitname"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val mainFragMVVM = ViewModelProviders.of(this)[MainFragMVVM::class.java]
        showLoadingCase()
        cancelLoadingCase()

        val data1 = hashMapOf(
            "name" to "basb",
            "image" to "https://cdn1-production-images-kly.akamaized.net/d_XeXi__hXPpdP-kOUjugTYc9HU=/500x667/smart/filters:quality(75):strip_icc():format(webp)/kly-media-production/medias/3527238/original/091552700_1627785660-132468207_734819403906396_393591296740971871_n.jpg",
            "price" to 1,
            "quantity" to 1,
            "idCustamer" to "1",
        )

//        pesananCol.add(data1)

        pesananCol.get()
            .addOnSuccessListener { documents ->
                val pesananList = documents.map { document ->
                    val price = document.getLong("price") ?: 0L
                    val quantity = document.getLong("quantity") ?: 0L
                    Pesanan(
                        idFood = document.id,
                        strName = document.getString("name") ?: "",
                        strImage = document.getString("image") ?: "",
                        intPrice = price.toInt(),
                        intQuantity = quantity.toInt(),
                        idCustamer = document.getString("idCustamer") ?: "",
                    )
                }
                setPesananAdapter(pesananList)
                Log.d("KeranjangFragment", "berhasil mengambil data")

            }
            .addOnFailureListener { exception ->
                // Tangani kesalahan pengambilan data
                Log.e("KeranjangFragment", "Error getting food documents", exception)
            }

        preparePesananRecyclerView()
        myAdapter.onItemClicked(object : PesananRecyclerAdapter.OnItemPesananClicked {
            override fun onClickListener(food: Pesanan) {
                val intent = Intent(activity, InputPesanan::class.java)
                intent.putExtra(FRUIT_NAME, food.strName)
                startActivity(intent)
            }
        })
    }

    private fun showLoadingCase() {
        binding.apply {
            recViewPesanan.visibility = View.VISIBLE
            rootKerangang.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.white
                )
            )
        }
    }

    private fun cancelLoadingCase() {
        binding.apply {
            recViewPesanan.visibility = View.VISIBLE
            rootKerangang.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.white
                )
            )

        }
    }

    private fun setPesananAdapter(food: List<Pesanan>) {
        myAdapter.setPesananList(food)
    }

    private fun preparePesananRecyclerView() {
        binding.recViewPesanan.apply {
            adapter = myAdapter
//            layoutManager = LinearLayoutManager(context)
            layoutManager = GridLayoutManager(context, 1, GridLayoutManager.VERTICAL, false)

        }
    }
}