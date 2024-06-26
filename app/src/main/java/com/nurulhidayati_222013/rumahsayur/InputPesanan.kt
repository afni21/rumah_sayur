package com.nurulhidayati_222013.rumahsayur

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class InputPesanan : AppCompatActivity() {
    private lateinit var image1: ImageView
    private lateinit var image2: ImageView
    private lateinit var name: TextView
    private lateinit var editTextNumber: EditText
    private lateinit var buttonKurang: ImageButton
    private lateinit var buttonTambah: ImageButton
    private lateinit var buttonBack: ImageButton
    private lateinit var buttonTamKer: Button
    private val db = Firebase.firestore
    private val pesananCol = db.collection("pesanan")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_input_pesanan)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val idFood = intent.getStringExtra("idFood")
        val strName = intent.getStringExtra("strName")
        val strImage = intent.getStringExtra("strImage")
        val intPrice = intent.getIntExtra("int", 0)

        image1 = findViewById(R.id.imageView7)
        image2 = findViewById(R.id.imageView8)
        name = findViewById(R.id.tv_sayur)

        name.text = strName

        Glide.with(this)
            .load(strImage)
            .into(image1)

        Glide.with(this)
            .load(strImage)
            .into(image1)

        // Inisialisasi views
        editTextNumber = findViewById(R.id.editTextNumber)
        buttonKurang = findViewById(R.id.imageButton3)
        buttonTambah = findViewById(R.id.imageButton4)
        buttonBack = findViewById(R.id.imageButton2)
        buttonTamKer = findViewById(R.id.btnTamKer)

        // Set initial value to 0
        editTextNumber.setText("0")

        // Event listener untuk tombol kurang
        buttonKurang.setOnClickListener {
            val currentValue = editTextNumber.text.toString().toIntOrNull() ?: 0
            if (currentValue > 0) {
                editTextNumber.setText((currentValue - 1).toString())
            }
        }

        // Event listener untuk tombol tambah
        buttonTambah.setOnClickListener {
            val currentValue = editTextNumber.text.toString().toIntOrNull() ?: 0
            editTextNumber.setText((currentValue + 1).toString())
        }

        // Event listener untuk tombol back
        buttonBack.setOnClickListener {
            finish() // Kembali ke halaman sebelumnya
        }

        buttonTamKer.setOnClickListener {
            val quantity = editTextNumber.text.toString().toIntOrNull() ?: 0

            val data1 = hashMapOf(
                "quantity" to quantity,
                "idCustomer" to "1",
                "idFood" to idFood,
            )
            pesananCol.add(data1).addOnSuccessListener {
                // Data berhasil ditambahkan
                finish() // Kembali ke halaman utama
                Toast.makeText(this, "Keranjang +1 pesanan", Toast.LENGTH_SHORT).show() // Tampilkan Toast
            }
                .addOnFailureListener { exception ->
                    // Tangani kesalahan penambahan data
                    Log.e("Input Pesanan", "Error add pesanan", exception)

                }
        }

        // Ensure the EditText stays in numeric format even when edited manually
        editTextNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val value = s.toString().toIntOrNull()
                if (value == null || value < 0) {
                    editTextNumber.setText("0")
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })


    }
}
