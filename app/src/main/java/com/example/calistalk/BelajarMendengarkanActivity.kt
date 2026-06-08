package com.example.calistalk

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class BelajarMendengarkanActivity : AppCompatActivity() {

    private var mediaPlayer: MediaPlayer? = null
    private var currentIndex = 0
    data class Materi(val nama: String, val suaraResId: Int)
    private val daftarMateri = listOf(
        Materi("Kucing", R.raw.cat),
        Materi("Singa", R.raw.lion),
        Materi("Sapi", R.raw.cow)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_belajar_mendengar)

        val btnBack = findViewById<ImageView>(R.id.btnBack)
        val btnPlay = findViewById<ImageButton>(R.id.btnPlay)
        val leftArrow = findViewById<ImageView>(R.id.leftArrow)
        val rightArrow = findViewById<ImageView>(R.id.rightArrow)
        val txtJawaban = findViewById<TextView>(R.id.txtJawaban)

        btnBack.setOnClickListener { finish() }

        // Fungsi Update Tampilan
        fun updateUI() {
            txtJawaban.text = "" // Sembunyikan jawaban saat pindah soal
            mediaPlayer?.release()
            mediaPlayer = null
        }

        // Tombol Play
        btnPlay.setOnClickListener {
            // Tampilkan jawaban
            txtJawaban.text = daftarMateri[currentIndex].nama

            // Putar suara
            mediaPlayer?.release()
            mediaPlayer = MediaPlayer.create(this, daftarMateri[currentIndex].suaraResId)
            mediaPlayer?.start()
        }

        // Navigasi Panah
        rightArrow.setOnClickListener {
            if (currentIndex < daftarMateri.size - 1) {
                currentIndex++
                updateUI()
            }
        }

        leftArrow.setOnClickListener {
            if (currentIndex > 0) {
                currentIndex--
                updateUI()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
    }
}

