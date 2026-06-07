package com.example.calistalk

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class PercobaanHurufActivity : AppCompatActivity() {

    private lateinit var imgHuruf: ImageView
    private lateinit var tracingView: TracingView

    private val hurufList = arrayOf(
        R.drawable.huruf_a,
        R.drawable.huruf_b,
        R.drawable.huruf_c
    )

    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tracking_huruf_percobaan)

        imgHuruf = findViewById(R.id.imgHurufTrace)
        tracingView = findViewById(R.id.tracingView)

        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        val btnNext = findViewById<ImageButton>(R.id.rightArrow)
        val btnPrev = findViewById<ImageButton>(R.id.leftArrow)
        val btnClear = findViewById<Button>(R.id.btnClear)

        updateHuruf()

        btnBack.setOnClickListener {
            finish()
        }

        btnClear.setOnClickListener {
            tracingView.clearCanvas()
        }

        btnNext.setOnClickListener {
            if (currentIndex < hurufList.lastIndex) {
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
}