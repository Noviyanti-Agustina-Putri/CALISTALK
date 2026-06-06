package com.example.calistalk

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class BelajarMenghitungActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.halaman_awal_mengenal_angka)

        findViewById<CardView>(R.id.hitungDasar).setOnClickListener {
            val intent = Intent(this, MengenalAngkaActivity::class.java)
            intent.putExtra("LEVEL", "DASAR")
            startActivity(intent)
        }

        findViewById<CardView>(R.id.hitungLanjutan).setOnClickListener {
            val intent = Intent(this, MengenalAngkaActivity::class.java)
            intent.putExtra("LEVEL", "LANJUTAN")
            startActivity(intent)
        }

        findViewById<CardView>(R.id.hitungSulit).setOnClickListener {
            val intent = Intent(this, MengenalAngkaActivity::class.java)
            intent.putExtra("LEVEL", "SULIT")
            startActivity(intent)

        }
    }
}