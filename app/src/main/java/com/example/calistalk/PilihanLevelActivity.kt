package com.example.calistalk

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class PilihanLevelActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.halaman_awal_mengenal_angka)

        findViewById<ImageView>(R.id.btnBack).setOnClickListener {
            finish()
        }

        val dasar = findViewById<CardView>(R.id.hitungDasar)
        val lanjutan = findViewById<CardView>(R.id.hitungLanjutan)
        val sulit = findViewById<CardView>(R.id.hitungSulit)

        dasar.setOnClickListener {
            val intent = Intent(this, MengenalAngkaActivity::class.java)
            intent.putExtra("LEVEL", "DASAR")
            startActivity(intent)
        }

        lanjutan.setOnClickListener {
            val intent = Intent(this, MengenalAngkaActivity::class.java)
            intent.putExtra("LEVEL", "LANJUTAN")
            startActivity(intent)
        }

        sulit.setOnClickListener {
            val intent = Intent(this, MengenalAngkaActivity::class.java)
            intent.putExtra("LEVEL", "SULIT")
            startActivity(intent)
        }
    }
}