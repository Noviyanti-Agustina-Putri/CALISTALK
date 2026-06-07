package com.example.calistalk

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class TracingAngkaActivity : AppCompatActivity() {

    private lateinit var imgAngka: ImageView
    private lateinit var tracingView: TracingView

    private val angkaList = arrayOf(
        R.drawable.angka_1_trace,
        R.drawable.angka_2_trace,
        R.drawable.angka_3_trace
    )

    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showMenuTracing()
    }

    private fun showMenuTracing() {

        setContentView(R.layout.halaman_awal_tracking_angka)

        val cardBelajar = findViewById<CardView>(R.id.cardBelajar)
        val cardPercobaan = findViewById<CardView>(R.id.cardPercobaan)
        val btnBack = findViewById<ImageButton>(R.id.btnBack)

        btnBack.setOnClickListener {
            finish()
        }

        cardBelajar.setOnClickListener {
            currentIndex = 0
            showTracingPage()
        }

        cardPercobaan.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    PercobaanAngkaActivity::class.java
                )
            )
        }
    }

    private fun showTracingPage() {

        setContentView(R.layout.tracking_angka_belajar)

        imgAngka = findViewById(R.id.imgAngkaTrace)
        tracingView = findViewById(R.id.tracingView)

        val btnNext = findViewById<ImageButton>(R.id.rightArrow)
        val btnPrev = findViewById<ImageButton>(R.id.leftArrow)
        val btnClear = findViewById<Button>(R.id.btnClear)

        updateAngka()

        btnClear.setOnClickListener {
            tracingView.clearCanvas()
        }

        btnNext.setOnClickListener {
            if (currentIndex < angkaList.size - 1) {
                currentIndex++
                updateAngka()
            }
        }

        btnPrev.setOnClickListener {
            if (currentIndex > 0) {
                currentIndex--
                updateAngka()
            }
        }
    }

    private fun updateAngka() {
        imgAngka.setImageResource(angkaList[currentIndex])
        tracingView.clearCanvas()
    }

    @Suppress("DEPRECATION")
    override fun onBackPressed() {
        if (::imgAngka.isInitialized) {
            showMenuTracing()
        } else {
            super.onBackPressed()
        }
    }
}