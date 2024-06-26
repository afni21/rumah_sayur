package com.nurulhidayati_222013.rumahsayur.authentication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.nurulhidayati_222013.rumahsayur.MainActivity
import com.nurulhidayati_222013.rumahsayur.R

class Login : AppCompatActivity() {
    private lateinit var btnLogin: Button
    private lateinit var btnCreate: TextView
    private lateinit var btnLupaPass: TextView
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnLogin = findViewById(R.id.button_login)
        btnCreate = findViewById(R.id.text_create_account)
        btnLupaPass = findViewById(R.id.lupa)
        emailEditText = findViewById(R.id.editTextEmailAddress)
        passwordEditText = findViewById(R.id.editTextPassword3)
        auth = FirebaseAuth.getInstance()

        btnLogin.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "Login failed: Username atau Password anda salah", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
            }
        }

        btnCreate.setOnClickListener {
            val intent = Intent(this, CreateAccount::class.java)
            startActivity(intent)
        }

        btnLupaPass.setOnClickListener {
            val intent = Intent(this, LupaPass::class.java)
            startActivity(intent)
        }
    }
}
