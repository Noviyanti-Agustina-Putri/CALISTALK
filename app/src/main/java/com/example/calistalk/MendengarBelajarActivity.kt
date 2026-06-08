package com.example.calistalk

import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MendengarBelajarActivity : AppCompatActivity() {
    private var mediaPlayer: MediaPlayer? = null

    private val daftarMateri = listOf(
        Materi("Kucing", "hewan/gambar/kucing.png", R.raw.cat),
        Materi("Singa", "hewan/gambar/singa.png", R.raw.lion)
    )

    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mendengar_belajar)

        updateMateri()

        findViewById<ImageView>(R.id.btnNext).setOnClickListener {
            if (currentIndex < daftarMateri.size - 1) {
                currentIndex++
                updateMateri()
            }
        }

        findViewById<ImageView>(R.id.btnPrev).setOnClickListener {
            if (currentIndex > 0) {
                currentIndex--
                updateMateri()
            }
        }
    }

    private fun updateMateri() {
        val materi = daftarMateri[currentIndex]

        // Update Teks
        findViewById<TextView>(R.id.tvNamaMateri).text = materi.nama

        // Update Gambar
        try {
            val inputStream = assets.open(materi.gambarPath)
            findViewById<ImageView>(R.id.imgMateri).setImageBitmap(BitmapFactory.decodeStream(inputStream))
        } catch (e: Exception) { e.printStackTrace() }

        // Reset Media Player jika ganti materi
        mediaPlayer?.release()
        findViewById<ImageButton>(R.id.btnPlaySuara).setOnClickListener {
            mediaPlayer = MediaPlayer.create(this, materi.suaraId)
            mediaPlayer?.start()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
    }
}