package com.example.calistalk

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class BelajarMendengarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_belajar_mendengar)

        // 1. Tombol Kembali
        findViewById<ImageView>(R.id.btnBack).setOnClickListener { finish() }

        // 2. Navigasi ke Halaman Belajar (Materi)
        val cardBelajar = findViewById<CardView>(R.id.cardBelajar)
        cardBelajar.setOnClickListener {
            val intent = Intent(this, MendengarBelajarActivity::class.java)
            startActivity(intent)
        }

        // 3. Navigasi ke Halaman Percobaan
        val cardPercobaan = findViewById<CardView>(R.id.cardPercobaan)
        cardPercobaan.setOnClickListener {
            val intent = Intent(this, BelajarMendengarkanActivity::class.java)
            startActivity(intent)
        }

        // 4. Navigasi ke Halaman Kuis (Opsional)

        }
    }
