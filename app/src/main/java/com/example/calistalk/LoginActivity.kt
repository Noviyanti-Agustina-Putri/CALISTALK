package com.example.calistalk

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        val email = findViewById<EditText>(R.id.etLoginUsername)
        val password = findViewById<EditText>(R.id.etLoginPassword)

        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnSignUp = findViewById<Button>(R.id.btnSignUp)

        btnLogin.setOnClickListener {

            val userEmail = email.text.toString()
            val userPassword = password.text.toString()

            auth.signInWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(this) { task ->

                    if (task.isSuccessful) {

                        Toast.makeText(
                            this,
                            "Login Berhasil",
                            Toast.LENGTH_SHORT
                        ).show()

                        startActivity(
                            Intent(this, MainActivity::class.java)
                        )

                    } else {

                        Toast.makeText(
                            this,
                            "Login Gagal",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }

        btnSignUp.setOnClickListener {

            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}