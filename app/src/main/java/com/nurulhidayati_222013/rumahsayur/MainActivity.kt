package com.nurulhidayati_222013.rumahsayur

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var  buttonimg: ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        buttonimg = findViewById(R.id.brokoli)
        buttonimg.setOnClickListener {
            val intent = Intent(this, InputPesanan::class.java)
            startActivity(intent)
        }
        buttonimg = findViewById(R.id.kangkung)
        buttonimg.setOnClickListener {
            val intent = Intent(this, InputPesanan::class.java)
            startActivity(intent)
        }

        buttonimg = findViewById(R.id.apel)
        buttonimg.setOnClickListener {
            val intent = Intent(this, InputPesanan::class.java)
            startActivity(intent)
        }

        buttonimg = findViewById(R.id.anggur)
        buttonimg.setOnClickListener {
            val intent = Intent(this, InputPesanan::class.java)
            startActivity(intent)
        }

        buttonimg = findViewById(R.id.jeruk)
        buttonimg.setOnClickListener {
            val intent = Intent(this, InputPesanan::class.java)
            startActivity(intent)
        }

        buttonimg = findViewById(R.id.wortel)
        buttonimg.setOnClickListener {
            val intent = Intent(this, InputPesanan::class.java)
            startActivity(intent)
        }

        buttonimg = findViewById(R.id.beranda)
        buttonimg.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        buttonimg = findViewById(R.id.pesanan)
        buttonimg.setOnClickListener {
            val intent = Intent(this, FramePesanan::class.java)
            startActivity(intent)
        }

        buttonimg = findViewById(R.id.riwayat)
        buttonimg.setOnClickListener {
            val intent = Intent(this, Riwayat::class.java)
            startActivity(intent)
        }

    }
}