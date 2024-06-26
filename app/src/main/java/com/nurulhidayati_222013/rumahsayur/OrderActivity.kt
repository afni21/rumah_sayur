package com.nurulhidayati_222013.rumahsayur

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.firestore.firestore

class OrderActivity : AppCompatActivity() {

    private lateinit var buttonPesan: Button
    private lateinit var radioGroup: RadioGroup

    private val db = Firebase.firestore
    private val riwayatCol = db.collection("riwayat")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_order)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        buttonPesan = findViewById(R.id.btn_pesan)
        radioGroup = findViewById(R.id.radio_group)

        val idPesanan = intent.getStringExtra("idPesanan")
        val idCustomer = intent.getStringExtra("idCustomer")
        val idFood = intent.getStringExtra("idFood")
        val intQuantity = intent.getIntExtra("intQuantity", 0)
        val intPrice = intent.getIntExtra("intPrice", 0)
        var pembayaran = ""
        val intOngkir = 8000

        radioGroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { radioGroup, checkedButtonId ->
            when (checkedButtonId) {
                R.id.rb_option_a -> {
                    pembayaran = "cod"
                    Toast.makeText(this, "Option A is selected", Toast.LENGTH_SHORT).show()
                }
                R.id.rb_option_b -> {
                    pembayaran = "mandiri"
                    Toast.makeText(this, "Option B is selected", Toast.LENGTH_SHORT).show()
                }
            }
        })


        buttonPesan.setOnClickListener {

            if(pembayaran == ""){
                Toast.makeText(this, "pilih metode pembayaran", Toast.LENGTH_SHORT).show()
            }

            val riwayat = hashMapOf(
                "total_price" to intOngkir + (intQuantity * intPrice),
                "quantity" to intQuantity,
                "ongkir" to intOngkir,
                "alamat" to "btn insignia",
                "jalan" to "jl. maros",
                "pembayaran" to pembayaran,
                "tgl" to Timestamp.now(),
                "idCustomer" to "1",
                "idFood" to idFood,
            )

            riwayatCol.add(riwayat).addOnSuccessListener {
                // Data berhasil ditambahkan
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish() // Kembali ke halaman utama
                Toast.makeText(this, "Keranjang +1 pesanan", Toast.LENGTH_SHORT).show() // Tampilkan Toast
            }
                .addOnFailureListener { exception ->
                    // Tangani kesalahan penambahan data
                    Log.e("Input Pesanan", "Error add pesanan", exception)

                }
        }

    }
}