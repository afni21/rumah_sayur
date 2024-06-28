package com.nurulhidayati_222013.rumahsayur

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
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
        val btnUbah = findViewById<Button>(R.id.btn_ubah)
        val tvJalan = findViewById<TextView>(R.id.tv_jalan)
        val tvAalamat = findViewById<TextView>(R.id.tv_alamat)


        val idPesanan = intent.getStringExtra("idPesanan")
        val idCustomer = intent.getStringExtra("idCustomer")
        val idFood = intent.getStringExtra("idFood")
        val intQuantity = intent.getIntExtra("intQuantity", 0)
        val intPrice = intent.getIntExtra("intPrice", 0)
        var pembayaran = ""
        val intOngkir = 8000
        val intTotal = (intQuantity * intPrice) + intOngkir

        findViewById<TextView>(R.id.tv_harga_order).text = "Rp. $intPrice" // Menampilkan total harga
        findViewById<TextView>(R.id.tv_jumlah_order).text = "$intQuantity" // Menampilkan total harga
        findViewById<TextView>(R.id.tv_ongkir_order).text = "Rp. $intOngkir" // Menampilkan total harga
        findViewById<TextView>(R.id.tv_total_order).text = "Rp. $intTotal" // Menampilkan total harga


        radioGroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { radioGroup, checkedButtonId ->
            when (checkedButtonId) {
                R.id.rb_option_a -> {
                    pembayaran = "cod"
                    Toast.makeText(this, "Cach On Dilevery", Toast.LENGTH_SHORT).show()
                }

                R.id.rb_option_b -> {
                    pembayaran = "mandiri"
                    Toast.makeText(this, "Mandiri", Toast.LENGTH_SHORT).show()
                }
            }
        })

        btnUbah.setOnClickListener {
            showEditAddressDialog()
        }

        buttonPesan.setOnClickListener {

            if (pembayaran == "") {
                Toast.makeText(this, "pilih metode pembayaran", Toast.LENGTH_SHORT).show()
            } else {
                val riwayat = hashMapOf(
                    "total_price" to intOngkir + (intQuantity * intPrice),
                    "quantity" to intQuantity,
                    "ongkir" to intOngkir,
                    "alamat" to tvAalamat.text.toString(),
                    "jalan" to tvJalan.text.toString(),
                    "pembayaran" to pembayaran,
                    "tgl" to Timestamp.now(),
                    "idCustomer" to "1",
                    "idFood" to idFood,
                )

                riwayatCol.add(riwayat).addOnSuccessListener {
                    val intent = Intent(this, TransaksiActivity::class.java)
                    intent.putExtra("totalPrice", intOngkir + (intQuantity * intPrice))
                    intent.putExtra("quantity", intQuantity)
                    intent.putExtra("ongkir", intOngkir)
                    intent.putExtra("harga", intPrice)
                    intent.putExtra("jalan", tvJalan.text.toString())
                    intent.putExtra("pembayaran", pembayaran)
                    // ... add other order details as extras if needed

                    startActivity(intent)
                    finish()
                    Toast.makeText(this, "Riwayat +1 pesanan", Toast.LENGTH_SHORT).show()
                }
                    .addOnFailureListener { exception ->
                        // Tangani kesalahan penambahan data
                        Log.e("Input Pesanan", "Error add pesanan", exception)

                    }
            }
        }

    }

    private fun showEditAddressDialog() {
        val builder = AlertDialog.Builder(this)
        val view = layoutInflater.inflate(
            R.layout.edit_address_alamat,
            null
        ) // Inflate your custom layout for the dialog

        val etAlamat = view.findViewById<EditText>(R.id.et_alamat)
        val etJalan = view.findViewById<EditText>(R.id.et_jalan)

        builder.setView(view)
            .setPositiveButton("Simpan") { dialog, _ ->
                val newAlamat = etAlamat.text.toString()
                val newJalan = etJalan.text.toString()

                findViewById<TextView>(R.id.tv_alamat).text = newAlamat
                findViewById<TextView>(R.id.tv_jalan).text = newJalan

                dialog.dismiss()
            }
            .setNegativeButton("Batal") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

}