package com.example.calistalk

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MenyalinKataActivity : AppCompatActivity() {

    data class Buah(
        val gambar: Int,
        val nama: String
    )

    private lateinit var imgObjek: ImageView
    private lateinit var txtKata: TextView
    private lateinit var tracingView: TracingView

    private val daftarBuah = arrayListOf(
        Buah(R.drawable.apel, "APEL"),
        Buah(R.drawable.jeruk, "JERUK"),
        Buah(R.drawable.pisang, "PISANG")
    )

    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menyalin_kata)

        imgObjek = findViewById(R.id.imgObjek)
        txtKata = findViewById(R.id.txtKata)
        tracingView = findViewById(R.id.tracingView)

        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        val btnPrev = findViewById<ImageButton>(R.id.leftArrow)
        val btnNext = findViewById<ImageButton>(R.id.rightArrow)
        val btnClear = findViewById<Button>(R.id.btnClear)

        daftarBuah.shuffle()

        tampilkanBuah()

        btnBack.setOnClickListener {
            finish()
        }

        btnClear.setOnClickListener {
            tracingView.clearCanvas()
        }

        btnNext.setOnClickListener {

            if (currentIndex < daftarBuah.size - 1) {

                currentIndex++

                tampilkanBuah()
            }
        }

        btnPrev.setOnClickListener {

            if (currentIndex > 0) {

                currentIndex--

                tampilkanBuah()
            }
        }
    }

    private fun tampilkanBuah() {

        val buah = daftarBuah[currentIndex]

        imgObjek.setImageResource(buah.gambar)

        txtKata.text = buah.nama

        tracingView.clearCanvas()
    }
}