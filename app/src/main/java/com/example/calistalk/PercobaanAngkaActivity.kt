package com.example.calistalk

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class PercobaanAngkaActivity : AppCompatActivity() {

    private lateinit var imgAngka: ImageView
    private lateinit var tracingView: TracingView

    private val angkaList = arrayOf(
        R.drawable.angka_1,
        R.drawable.angka_2,
        R.drawable.angka_3
    )

    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tracking_angka_percobaan)

        imgAngka = findViewById(R.id.imgAngkaTrace)
        tracingView = findViewById(R.id.tracingView)

        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        val btnNext = findViewById<ImageButton>(R.id.rightArrow)
        val btnPrev = findViewById<ImageButton>(R.id.leftArrow)
        val btnClear = findViewById<Button>(R.id.btnClear)

        updateAngka()

        btnBack.setOnClickListener {
            finish()
        }

        btnClear.setOnClickListener {
            tracingView.clearCanvas()
        }

        btnNext.setOnClickListener {
            if (currentIndex < angkaList.lastIndex) {
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
}