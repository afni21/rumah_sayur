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
import com.google.firebase.firestore.FirebaseFirestore
import com.nurulhidayati_222013.rumahsayur.OrderActivity
import com.nurulhidayati_222013.rumahsayur.R
import com.nurulhidayati_222013.rumahsayur.adapter.PesananRecyclerAdapter
import com.nurulhidayati_222013.rumahsayur.databinding.FragmentKeranjangBinding
import com.nurulhidayati_222013.rumahsayur.model.Pesanan

class KeranjangFragment : Fragment() {
    private lateinit var myAdapter: PesananRecyclerAdapter
    private lateinit var binding: FragmentKeranjangBinding
    private val db = FirebaseFirestore.getInstance()
    private val pesananCol = db.collection("pesanan")
    private val foodCol = db.collection("food")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myAdapter = PesananRecyclerAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentKeranjangBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showLoadingCase()
        val pesananList = mutableListOf<Pesanan>()

        pesananCol.get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    showError("Tidak ada data pesanan")
                    return@addOnSuccessListener
                }
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
                                cancelLoadingCase()
                            }
                        }
                        .addOnFailureListener { exception ->
                            // Handle any errors
                            Log.e("Food", "Error getting food documents", exception)
                            showError("Gagal memuat data makanan")
                        }
                }
            }
            .addOnFailureListener { exception ->
                // Tangani kesalahan pengambilan data
                Log.e("KeranjangFragment", "Error getting pesanan documents", exception)
                showError("Gagal memuat data pesanan")
            }

        preparePesananRecyclerView()
        myAdapter.onItemClicked(object : PesananRecyclerAdapter.OnItemPesananClicked {
            override fun onClickListener(pesanan: Pesanan) {
                val intent = Intent(activity, OrderActivity::class.java)
                intent.putExtra("idPesanan", pesanan.idPesanan)
                intent.putExtra("intQuantity", pesanan.intQuantity)
                intent.putExtra("intPrice", pesanan.intPrice)
                intent.putExtra("idCustomer", pesanan.idCustomer)
                intent.putExtra("idFood", pesanan.idFood)
                startActivity(intent)
            }
        })
    }

    private fun showLoadingCase() {
        binding.apply {
            recViewPesanan.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
            tvError.visibility = View.GONE
            rootKeranjang.setBackgroundColor(
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
            progressBar.visibility = View.GONE
            tvError.visibility = View.GONE
            rootKeranjang.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.white
                )
            )
        }
    }

    private fun showError(message: String) {
        binding.apply {
            recViewPesanan.visibility = View.GONE
            progressBar.visibility = View.GONE
            tvError.text = message
            tvError.visibility = View.VISIBLE
            rootKeranjang.setBackgroundColor(
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
            layoutManager = GridLayoutManager(context, 1, GridLayoutManager.VERTICAL, false)
        }
    }
}
