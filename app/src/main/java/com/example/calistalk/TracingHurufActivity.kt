package com.example.calistalk

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class TracingHurufActivity : AppCompatActivity() {

    private lateinit var imgHuruf: ImageView
    private lateinit var tracingView: TracingView

    private val hurufList = arrayOf(
        R.drawable.huruf_a_trace,
        R.drawable.huruf_b_trace,
        R.drawable.huruf_c_trace
    )

    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        showMenuTracing()
    }

    private fun showMenuTracing() {

        setContentView(R.layout.halaman_awal_tracking_huruf)

        val cardBelajar = findViewById<CardView>(R.id.cardBelajar)
        val cardPercobaan = findViewById<CardView>(R.id.cardPercobaan)
        val btnBack = findViewById<ImageButton>(R.id.btnBack)

        btnBack.setOnClickListener {
            finish()
        }

        cardBelajar.setOnClickListener {
            showTracingPage()
        }

        cardPercobaan.setOnClickListener {
            startActivity(
                android.content.Intent(
                    this,
                    PercobaanHurufActivity::class.java
                )
            )
        }
    }

    private fun showTracingPage() {

        setContentView(R.layout.tracking_huruf_belajar)

        imgHuruf = findViewById(R.id.imgHurufTrace)
        tracingView = findViewById(R.id.tracingView)

        val btnNext = findViewById<ImageButton>(R.id.rightArrow)
        val btnPrev = findViewById<ImageButton>(R.id.leftArrow)
        val btnClear = findViewById<Button>(R.id.btnClear)

        btnClear.setOnClickListener {
            tracingView.clearCanvas()
        }

        btnNext.setOnClickListener {
            if (currentIndex < hurufList.size - 1) {
                currentIndex++
                updateHuruf()
            }
        }

        btnPrev.setOnClickListener {
            if (currentIndex > 0) {
                currentIndex--
                updateHuruf()
            }
        }
    }

    private fun updateHuruf() {
        imgHuruf.setImageResource(hurufList[currentIndex])
        tracingView.clearCanvas()
    }

    override fun onBackPressed() {
        if (::imgHuruf.isInitialized) {
            showMenuTracing()
        } else {
            super.onBackPressed()
        }
    }
}