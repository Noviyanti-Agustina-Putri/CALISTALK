package com.example.calistalk

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class BelajarMenulisActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.belajar_menulis)

        val card1 = findViewById<CardView>(R.id.card1)
        val card2 = findViewById<CardView>(R.id.card2)
        val card3 = findViewById<CardView>(R.id.card3)
        val card4 = findViewById<CardView>(R.id.card4)


        card1.setOnClickListener {
            startActivity(
                Intent(this, TracingHurufActivity::class.java)
            )
        }

        card2.setOnClickListener {
            startActivity(
                Intent(this, TracingAngkaActivity::class.java)
            )
        }

        card3.setOnClickListener {
            startActivity(
                Intent(this, MenyalinKataActivity::class.java)
            )
        }

        card4.setOnClickListener {
            startActivity(
                Intent(this, MenyusunHurufActivity::class.java)
            )
        }

    }
}