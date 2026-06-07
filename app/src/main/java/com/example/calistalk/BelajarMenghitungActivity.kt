package com.example.calistalk

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class BelajarMenghitungActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.belajar_menghitung)

        val cardAngka = findViewById<CardView>(R.id.card1)
        val cardBenda = findViewById<CardView>(R.id.card2)
        val cardPenjumlahan = findViewById<CardView>(R.id.card3)
        val cardPengurangan = findViewById<CardView>(R.id.card4)

        // Mengenal Angka
        cardAngka.setOnClickListener {
            startActivity(
                Intent(this, PilihanLevelActivity::class.java)
            )
        }

        // Menghitung Benda
        cardBenda.setOnClickListener {
            startActivity(
                Intent(this, PilihanBendaActivity::class.java)
            )
        }

        // Penjumlahan
        cardPenjumlahan.setOnClickListener {
            startActivity(
                Intent(this, PenjumlahanActivity::class.java)
            )
        }

        // Pengurangan
        cardPengurangan.setOnClickListener {
            startActivity(
                Intent(this, PenguranganActivity::class.java)
            )
        }
    }
}