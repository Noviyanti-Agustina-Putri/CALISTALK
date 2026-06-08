package com.example.calistalk

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class BelajarMendengarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_belajar_mendengar) // Pastikan nama layout benar

        // 1. Tombol Kembali
        findViewById<ImageView>(R.id.btnBack).setOnClickListener { finish() }

        // 2. Navigasi ke Halaman Belajar (Materi)
        val cardBelajar = findViewById<CardView>(R.id.cardBelajar)
        cardBelajar.setOnClickListener {
            val intent = Intent(this, MendengarBelajarActivity::class.java)
            startActivity(intent)
        }

        // 3. (Opsional) Tambahkan untuk cardPercobaan atau cardKuis jika sudah ada
        // Di dalam onCreate() Activity menu utama Anda:

        val cardPercobaan = findViewById<CardView>(R.id.cardPercobaan)
        cardBelajar.setOnClickListener {
            val intent = Intent(this, BelajarMendengarActivity::class.java)
            startActivity(intent)
        }
    }
}