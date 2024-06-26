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
import com.nurulhidayati_222013.rumahsayur.OrderActivity
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
    private val foodCol = db.collection("food")


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
//        const val FRUIT_NAME = "com.nurulhidayati_222013.rumahsayur.fragment.fruitname"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val mainFragMVVM = ViewModelProviders.of(this)[MainFragMVVM::class.java]
        showLoadingCase()
        cancelLoadingCase()

//        val data1 = hashMapOf(
//            "quantity" to 1,
//            "idCustomer" to "1",
//            "idFood" to "9eD7FNYVNoaYRRiPZUGv",
//        )
//        pesananCol.add(data1)

        val pesananList = mutableListOf<Pesanan>()

        pesananCol.get()
            .addOnSuccessListener { documents ->
                documents.forEach { document ->
                    val quantity = document.getLong("quantity") ?: 0L
                    val idCustomer = document.getString("idCustomer") ?: ""
                    val idFood = document.getString("idFood") ?: ""

                    val docFood = foodCol.document(idFood)
                    docFood.get()
                        .addOnSuccessListener { foodDocument ->
                            val intPrice = (foodDocument.getLong("price") ?: 0L).toInt()
                            val strName = foodDocument.getString("name") ?: ""
                            val strImage = foodDocument.getString("image") ?: ""

                            val pesanan = Pesanan(
                                idPesanan = document.id,
                                intPrice = intPrice,
                                intQuantity = quantity.toInt(),
                                strName = strName,
                                strImage = strImage,
                                idCustomer = idCustomer,
                                idFood = idFood
                            )
                            pesananList.add(pesanan)

                            // Periksa apakah ini dokumen terakhir, lalu atur adapter
                            if (pesananList.size == documents.size()) {
                                setPesananAdapter(pesananList)
                                Log.d("KeranjangFragment", "berhasil mengambil data")
                            }
                        }
                        .addOnFailureListener { exception ->
                            // Handle any errors
                            Log.e("Food", "Error getting food documents", exception)
                        }
                }
            }
            .addOnFailureListener { exception ->
                // Tangani kesalahan pengambilan data
                Log.e("KeranjangFragment", "Error getting food documents", exception)
            }
        preparePesananRecyclerView()
        myAdapter.onItemClicked(object : PesananRecyclerAdapter.OnItemPesananClicked {
            override fun onClickListener(pesanan: Pesanan) {
                val intent = Intent(activity, OrderActivity::class.java)
                intent.putExtra("idPesanan", pesanan.idPesanan)
                intent.putExtra("intQuantity", pesanan.intQuantity)
                intent.putExtra("intPrice", pesanan.intPrice)
                intent.putExtra("idCustomer", pesanan.idCustomer)
                intent.putExtra("idFood", pesanan.idPesanan)
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

    private fun setPesananAdapter(pesanan: List<Pesanan>) {
        myAdapter.setPesananList(pesanan)
    }

    private fun preparePesananRecyclerView() {
        binding.recViewPesanan.apply {
            adapter = myAdapter
//            layoutManager = LinearLayoutManager(context)
            layoutManager = GridLayoutManager(context, 1, GridLayoutManager.VERTICAL, false)

        }
    }
}