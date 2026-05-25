package com.example.calistalk

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()

        val email = findViewById<EditText>(R.id.etEmail)
        val password = findViewById<EditText>(R.id.etPassword)

        val btnDaftar = findViewById<Button>(R.id.btnDaftar)
        val btnSignIn = findViewById<Button>(R.id.btnSignIn)

        btnDaftar.setOnClickListener {

            val userEmail = email.text.toString()
            val userPassword = password.text.toString()

            auth.createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(this) { task ->

                    if (task.isSuccessful) {

                        Toast.makeText(
                            this,
                            "Register Berhasil",
                            Toast.LENGTH_SHORT
                        ).show()

                        startActivity(
                            Intent(this, LoginActivity::class.java)
                        )

                    } else {

                        Toast.makeText(
                            this,
                            "Register Gagal",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }

        btnSignIn.setOnClickListener {

            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}