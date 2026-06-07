package com.example.calistalk

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import androidx.cardview.widget.CardView

class HalamanAwalAnakActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.halaman_awal_anak)

        val cardMenghitung = findViewById<CardView>(R.id.cardMenghitung)
        val cardMenulis = findViewById<CardView>(R.id.cardMenulis)
        val cardMiniGameBicara = findViewById<CardView>(R.id.cardGame)

        cardMenghitung.setOnClickListener {
            startActivity(
                Intent(this, BelajarMenghitungActivity::class.java)
            )
        }

        cardMenulis.setOnClickListener {
            startActivity(
                Intent(this, BelajarMenulisActivity::class.java)
            )
        }

        // 2. Tambahkan fungsi klik untuk pindah ke MiniGameBicaraActivity
        cardMiniGameBicara.setOnClickListener {
            startActivity(
                Intent(this, MiniGameBicaraActivity::class.java)
            )
        }
    }
}