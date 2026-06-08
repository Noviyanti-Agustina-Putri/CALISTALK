package com.example.calistalk

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class HalamanAwalAnakActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.halaman_awal_anak)

        val cardMenghitung = findViewById<CardView>(R.id.cardMenghitung)
        val cardMenulis = findViewById<CardView>(R.id.cardMenulis)
        val cardMiniGameBicara = findViewById<CardView>(R.id.cardGame)
        val cardMendengar = findViewById<CardView>(R.id.cardMendengar)
        val cardPengucapan = findViewById<CardView>(R.id.cardPengucapan)


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

        cardMiniGameBicara.setOnClickListener {
            startActivity(
                Intent(this, MiniGameBicaraActivity::class.java)
            )
        }

        cardMendengar.setOnClickListener {
            startActivity(
                Intent(this, PercobaanMendengarActivity::class.java)
            )
        }
        cardPengucapan.setOnClickListener {
            startActivity(
                Intent(this, BelajarPengucapanActivity::class.java)
            )
        }
        cardPengucapan.setOnClickListener {
            startActivity(
                Intent(this, BelajarPengucapanActivity::class.java)
            )
        }
    }
}