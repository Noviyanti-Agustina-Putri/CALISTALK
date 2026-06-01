package com.example.calistalk

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.EmailAuthProvider

class VerifikasiOrangTuaActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var etPassword: EditText
    private lateinit var btnMasuk: Button
    private lateinit var btnKembali: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verifikasi_orangtua)

        auth = FirebaseAuth.getInstance()
        etPassword = findViewById(R.id.etPassword)
        btnMasuk = findViewById(R.id.btnMasuk)
        btnKembali = findViewById(R.id.btnKembali)

        btnMasuk.setOnClickListener {
            val password = etPassword.text.toString().trim()

            if (password.isEmpty()) {
                Toast.makeText(this, "Masukkan password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Verifikasi dengan password Firebase user yang sedang login
            verifikasiDenganFirebase(password)
        }

        btnKembali.setOnClickListener {
            finish()
        }
    }

    private fun verifikasiDenganFirebase(password: String) {
        val currentUser = auth.currentUser

        if (currentUser == null) {
            Toast.makeText(this, "Anda belum login. Silakan login terlebih dahulu.", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        val userEmail = currentUser.email
        if (userEmail.isNullOrEmpty()) {
            Toast.makeText(this, "Email tidak ditemukan", Toast.LENGTH_SHORT).show()
            return
        }

        // Tampilkan loading
        Toast.makeText(this, "Memverifikasi...", Toast.LENGTH_SHORT).show()

        val credential = EmailAuthProvider.getCredential(userEmail, password)

        currentUser.reauthenticate(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Verifikasi Berhasil!", Toast.LENGTH_SHORT).show()

                    // SETELAH VERIFIKASI SUKSES, LANGSUNG KE MODE ORANG TUA (Jurnal)
                    startActivity(Intent(this, JurnalOrtuActivity::class.java))
                    finish()
                } else {
                    val error = task.exception?.message ?: "Password salah"
                    Toast.makeText(this, "Verifikasi Gagal: $error", Toast.LENGTH_LONG).show()
                    etPassword.text.clear()
                }
            }
    }
}