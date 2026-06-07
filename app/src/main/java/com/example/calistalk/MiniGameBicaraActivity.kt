package com.example.calistalk

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class MiniGameBicaraActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mini_game)

        val btnBack = findViewById<ImageView>(R.id.btnBack)
        val card1 = findViewById<CardView>(R.id.card1)
        val card2 = findViewById<CardView>(R.id.card2)
        val card3 = findViewById<CardView>(R.id.card3)

        // Fungsi Klik untuk Tombol Kembali
        btnBack.setOnClickListener {
            finish()
        }

        // Fungsi Klik untuk Menu Game 1: Tebak Gambar
        // 3. Fungsi Klik untuk Menu Game 1: Tebak Gambar
        card1.setOnClickListener {
            val intent = Intent(this, TebakGambarActivity::class.java)
            startActivity(intent)
        }

        // Fungsi Klik untuk Menu Game 2: Cocokkan Suara
        card2.setOnClickListener {
            val intent = Intent(this, CocokkanSuaraActivity::class.java)
            startActivity(intent)
        }

        // Fungsi Klik untuk Menu Game 3: Pilih Benda
        card3.setOnClickListener {
            // Catatan: Hapus tanda komentar (//) di bawah jika Activity tujuan sudah dibuat
            // val intent = Intent(this, PilihBendaActivity::class.java)
            // startActivity(intent)
        }
    }
}