package com.nurulhidayati_222013.rumahsayur

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class TransaksiActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState) // Memanggil konstruktor kelas induk (AppCompatActivity)
        enableEdgeToEdge() // Mengaktifkan tampilan edge-to-edge (jika didukung perangkat)
        setContentView(R.layout.activity_transaksi) // Menetapkan layout untuk aktivitas ini

        // Mengatur padding untuk tampilan utama agar tidak tumpang tindih dengan System Bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Mengambil data yang dikirimkan dari aktivitas sebelumnya melalui Intent
        val totalPrice = intent.getIntExtra("totalPrice", 0) // Mengambil nilai "totalPrice" atau 0 jika tidak ada
        val quantity = intent.getIntExtra("quantity", 0) // Mengambil nilai "quantity" atau 0 jika tidak ada
        val ongkir = intent.getIntExtra("ongkir", 0) // Mengambil nilai "ongkir" atau 0 jika tidak ada
        val harga = intent.getIntExtra("harga", 0) ?: "" // Mengambil nilai "harga" atau string kosong jika tidak ada
        val pembayaran = intent.getStringExtra("pembayaran") ?: "" // Mengambil nilai "pembayaran" atau string kosong jika tidak ada

        // Menampilkan data yang diterima di TextView yang sesuai
        findViewById<TextView>(R.id.tv_total_price).text = "Rp. $totalPrice" // Menampilkan total harga
        findViewById<TextView>(R.id.tv_quantity).text = "$quantity" // Menampilkan kuantitas
        findViewById<TextView>(R.id.tv_ongkir).text = "Rp. $ongkir" // Menampilkan ongkos kirim
        findViewById<TextView>(R.id.tv_harga_trans).text = "Rp. $harga" // Menampilkan harga
        findViewById<TextView>(R.id.tv_pembayaran).text = "$pembayaran" // Menampilkan metode pembayaran
    }
}