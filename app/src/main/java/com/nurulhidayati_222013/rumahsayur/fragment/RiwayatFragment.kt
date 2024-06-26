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
import com.google.firebase.Timestamp
import com.google.firebase.firestore.firestore
import com.nurulhidayati_222013.rumahsayur.InputPesanan
import com.nurulhidayati_222013.rumahsayur.R
import com.nurulhidayati_222013.rumahsayur.adapter.PesananRecyclerAdapter
import com.nurulhidayati_222013.rumahsayur.adapter.RiwayatReciclerdafter
import com.nurulhidayati_222013.rumahsayur.databinding.FragmentKeranjangBinding
import com.nurulhidayati_222013.rumahsayur.databinding.FragmentRiwayatBinding
import com.nurulhidayati_222013.rumahsayur.model.Pesanan
import com.nurulhidayati_222013.rumahsayur.model.Riwayat
import java.text.SimpleDateFormat
import java.util.Locale

class RiwayatFragment : Fragment() {
    private lateinit var myAdapter: RiwayatReciclerdafter
    private lateinit var binding: FragmentRiwayatBinding
    private val db = Firebase.firestore
    private val riwayatCol = db.collection("riwayat")
    private val foodCol = db.collection("food")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentRiwayatBinding.inflate(layoutInflater)
        myAdapter = RiwayatReciclerdafter()
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


        var strTanggal = ""
        var strImage = ""
        var strName = ""
        var intPrice = 0

        val data1 = hashMapOf(
            "name" to "basb",
            "total_price" to 1,
            "quantity" to 1,
            "idCustomer" to "1",
            "idFood" to "9eD7FNYVNoaYRRiPZUGv",
        )

        val riwayat = hashMapOf(
            "total_price" to 30000,
            "quantity" to 2,
            "ongkir" to 8000, // Ganti dengan ongkos kirim jika ada
            "alamat" to "btn insignia", // Ganti dengan alamat jika ada
            "jalan" to "jl. maros", // Ganti dengan jalan jika ada
            "tgl" to Timestamp.now(), // Ganti dengan tanggal jika ada
            "idCustomer" to "1",
            "idFood" to "9eD7FNYVNoaYRRiPZUGv"
        )
//        riwayatCol.add(riwayat)

        val docFood = foodCol.document("9eD7FNYVNoaYRRiPZUGv")
        docFood.get()
            .addOnSuccessListener { document ->
                intPrice = (document.getLong("price") ?: 0L).toInt()
                strName = document.getString("name") ?: ""
                strImage = document.getString("image") ?: ""
            }
            .addOnFailureListener { exception ->
                // Handle any errors
                Log.e("Food", "Error getting food documents", exception)
            }

//        foofCol.
        riwayatCol.get()
            .addOnSuccessListener { documents ->
                val pesananList = documents.map { document ->
                    val totPrice = document.getLong("total_price") ?: 0L
                    val quantity = document.getLong("quantity") ?: 0L
                    val ongkir = document.getLong("ongkir") ?: 0L
                    val timestamp = document.getTimestamp("tgl")
                    if (timestamp != null) {
                        // Convert Timestamp to Date if needed
                        val date = timestamp.toDate()
                        //Format the date as a string
                        val formattedDate: String = SimpleDateFormat(
                            "dd-MM-yyyy",
                            Locale.getDefault()
                        ).format(date)
                        // Now you have the timestamp as a formatted string
                        strTanggal = formattedDate

                    } else {
                        strTanggal = ""
                    }
                    Riwayat(
                        idRiwayat = document.id,
                        intTotPrice = totPrice.toInt(),
                        intQuantity = quantity.toInt(),
                        intOngkir = ongkir.toInt(),

                        strName = strName,
                        strImage = strImage,
                        intPrice = intPrice,

                        strAlamat = document.getString("alamat") ?: "",
                        strJalan = document.getString("jalan") ?: "",
                        strTanggal = strTanggal,
                        idCustomer = document.getString("idCustomer") ?: "",
                        idFood = document.getString("idFood") ?: "",
                    )
                }
                setRiwayatAdapter(pesananList)
                Log.d("RiwayatFragment", "berhasil mengambil data")

            }
            .addOnFailureListener { exception ->
                // Tangani kesalahan pengambilan data
                Log.e("RiwayatFragment", "Error getting food documents", exception)
            }

        preparePesananRecyclerView()
        myAdapter.onItemClicked(object : RiwayatReciclerdafter.OnItemRiwayatClicked {
            override fun onClickListener(riwayat: Riwayat) {
                val intent = Intent(activity, InputPesanan::class.java)
                intent.putExtra("data", riwayat.idRiwayat)
                startActivity(intent)
            }
        })
    }

    private fun showLoadingCase() {
        binding.apply {
            recViewRiwayat.visibility = View.VISIBLE
            rootRiwayat.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.frame
                )
            )
        }
    }

    private fun cancelLoadingCase() {
        binding.apply {
            recViewRiwayat.visibility = View.VISIBLE
            rootRiwayat.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.frame
                )
            )

        }
    }

    private fun setRiwayatAdapter(food: List<Riwayat>) {
        myAdapter.setRiwayatList(food)
    }

    private fun preparePesananRecyclerView() {
        binding.recViewRiwayat.apply {
            adapter = myAdapter
//            layoutManager = LinearLayoutManager(context)
            layoutManager = GridLayoutManager(context, 1, GridLayoutManager.VERTICAL, false)

        }
    }
}