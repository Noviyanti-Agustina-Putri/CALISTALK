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
            // Ambil nilai dengan .toString() dan pastikan tidak null
            val userEmail = email.text.toString().trim()
            val userPassword = password.text.toString().trim()

            // Debug: cetak ke log untuk memastikan nilai
            android.util.Log.d("REGISTER", "Email: $userEmail")
            android.util.Log.d("REGISTER", "Password: $userPassword")

            // Validasi email
            if (userEmail.isEmpty()) {
                Toast.makeText(this, "Email tidak boleh kosong", Toast.LENGTH_SHORT).show()
                email.requestFocus()
                return@setOnClickListener
            }

            // Validasi password
            if (userPassword.isEmpty()) {
                Toast.makeText(this, "Password tidak boleh kosong", Toast.LENGTH_SHORT).show()
                password.requestFocus()
                return@setOnClickListener
            }

            // Validasi panjang password
            if (userPassword.length < 6) {
                Toast.makeText(this, "Password minimal 6 karakter", Toast.LENGTH_SHORT).show()
                password.requestFocus()
                return@setOnClickListener
            }

            // Tampilkan loading
            Toast.makeText(this, "Mendaftar...", Toast.LENGTH_SHORT).show()

            // Register ke Firebase
            auth.createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Register Berhasil!", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, LoginActivity::class.java))
                        finish()
                    } else {
                        val error = task.exception?.message ?: "Register Gagal"
                        Toast.makeText(this, "Error: $error", Toast.LENGTH_LONG).show()
                        android.util.Log.e("REGISTER", "Error: $error", task.exception)
                    }
                }
        }

        btnSignIn.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}