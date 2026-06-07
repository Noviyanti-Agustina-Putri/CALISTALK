package com.example.calistalk

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class PilihanBendaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.halaman_awal_mengenal_benda)
        findViewById<ImageView>(R.id.btnBack)
            .setOnClickListener {
                finish()
            }

        val belajar = findViewById<CardView>(R.id.bendaBelajar)
        val percobaan = findViewById<CardView>(R.id.bendaPercobaan)
        val kuis = findViewById<CardView>(R.id.bendaKuis)

        belajar.setOnClickListener {

            val intent =
                Intent(this, MenghitungBendaActivity::class.java)

            intent.putExtra("MODE", "BELAJAR")

            startActivity(intent)
        }

        percobaan.setOnClickListener {

            val intent =
                Intent(this, MenghitungBendaActivity::class.java)

            intent.putExtra("MODE", "PERCOBAAN")

            startActivity(intent)
        }

        kuis.setOnClickListener {

            val intent =
                Intent(this, MenghitungBendaActivity::class.java)

            intent.putExtra("MODE", "KUIS")

            startActivity(intent)
        }
    }
}