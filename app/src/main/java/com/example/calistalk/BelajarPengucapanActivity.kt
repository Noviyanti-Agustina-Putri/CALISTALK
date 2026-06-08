package com.example.calistalk

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class BelajarPengucapanActivity : AppCompatActivity() {

    private lateinit var txtKata: TextView
    private lateinit var imgKarakter: ImageView
    private lateinit var btnMic: ImageButton
    private lateinit var leftArrow: ImageButton
    private lateinit var rightArrow: ImageButton

    private var mediaPlayer: MediaPlayer? = null

    data class KataBelajar(
        val kata: String,
        val gambar: Int,
        val suara: Int
    )

    private val daftarKata = listOf(

        KataBelajar(
            "AYAH",
            R.drawable.ayah,
            R.raw.ayah
        ),

        KataBelajar(
            "IBU",
            R.drawable.ibu,
            R.raw.ibu
        ),

        KataBelajar(
            "KAKAK",
            R.drawable.kakak,
            R.raw.kakak
        ),
        KataBelajar(
            "KAKEK",
            R.drawable.kakek,
            R.raw.kakek
        ),
        KataBelajar(
            "NENEK",
            R.drawable.nenek,
            R.raw.nenek
        ),

    )

    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.belajar_pengucapan)

        txtKata = findViewById(R.id.txtKata)
        imgKarakter = findViewById(R.id.imgKarakter)
        btnMic = findViewById(R.id.btnMic)
        leftArrow = findViewById(R.id.leftArrow)
        rightArrow = findViewById(R.id.rightArrow)

        tampilkanData()

        // Tombol Mic
        btnMic.setOnClickListener {
            putarSuara()
        }

        // Panah Kanan
        rightArrow.setOnClickListener {

            currentIndex++

            if (currentIndex >= daftarKata.size) {
                currentIndex = 0
            }

            tampilkanData()
        }

        // Panah Kiri
        leftArrow.setOnClickListener {

            currentIndex--

            if (currentIndex < 0) {
                currentIndex = daftarKata.size - 1
            }

            tampilkanData()
        }
    }

    private fun tampilkanData() {

        val data = daftarKata[currentIndex]

        txtKata.text = data.kata
        imgKarakter.setImageResource(data.gambar)
    }

    private fun putarSuara() {

        val data = daftarKata[currentIndex]

        mediaPlayer?.release()

        mediaPlayer = MediaPlayer.create(
            this,
            data.suara
        )

        mediaPlayer?.start()
    }

    override fun onDestroy() {
        super.onDestroy()

        mediaPlayer?.release()
        mediaPlayer = null
    }
}