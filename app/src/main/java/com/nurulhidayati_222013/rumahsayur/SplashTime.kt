package com.nurulhidayati_222013.rumahsayur

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.nurulhidayati_222013.rumahsayur.authentication.Login

class SplashTime : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash_time)

        // Use a Handler to delay navigation
        Handler(Looper.getMainLooper()).postDelayed({
            navigateToLogin() // Changed method name for clarity
        }, 2000)
    }

    private fun navigateToLogin() {
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
        finish() // Close the SplashTime activity after navigation
    }
}
