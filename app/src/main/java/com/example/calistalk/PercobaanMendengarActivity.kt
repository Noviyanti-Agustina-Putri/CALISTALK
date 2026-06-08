package com.example.calistalk

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class PercobaanMendengarActivity : AppCompatActivity() {

    private lateinit var btnPlay: ImageButton
    private lateinit var leftArrow: ImageView
    private lateinit var rightArrow: ImageView
    private lateinit var txtJawaban: TextView

    private var mediaPlayer: MediaPlayer? = null

    data class SoalSuara(
        val suara: Int,
        val jawaban: String
    )

    private val daftarSoal = mutableListOf(
        SoalSuara(R.raw.anjing, "Anjing"),
        SoalSuara(R.raw.ayam, "Ayam"),
        SoalSuara(R.raw.bebek, "Bebek"),
        SoalSuara(R.raw.cat, "Kucing"),
        SoalSuara(R.raw.kambing, "Kambing"),
        SoalSuara(R.raw.kodok, "Kodok"),
        SoalSuara(R.raw.monyet, "Monyet")
    )

    private lateinit var soalAcak: List<SoalSuara>

    private var currentIndex = 0
    private var jawabanDitampilkan = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.belajar_mendengarkan)

        btnPlay = findViewById(R.id.btnPlay)
        leftArrow = findViewById(R.id.leftArrow)
        rightArrow = findViewById(R.id.rightArrow)
        txtJawaban = findViewById(R.id.txtJawaban)

        // Acak urutan soal
        soalAcak = daftarSoal.shuffled()

        tampilkanSoal()

        btnPlay.setOnClickListener {
            putarSuara()
        }

        rightArrow.setOnClickListener {

            // klik pertama = tampilkan jawaban
            if (!jawabanDitampilkan) {

                txtJawaban.text = soalAcak[currentIndex].jawaban
                jawabanDitampilkan = true

            } else {

                // klik kedua = lanjut soal berikutnya
                currentIndex++

                if (currentIndex >= soalAcak.size) {
                    currentIndex = 0
                }

                tampilkanSoal()
            }
        }

        leftArrow.setOnClickListener {

            currentIndex--

            if (currentIndex < 0) {
                currentIndex = soalAcak.size - 1
            }

            tampilkanSoal()
        }
    }

    private fun tampilkanSoal() {
        txtJawaban.text = ""
        jawabanDitampilkan = false
    }

    private fun putarSuara() {

        mediaPlayer?.release()

        mediaPlayer = MediaPlayer.create(
            this,
            soalAcak[currentIndex].suara
        )

        mediaPlayer?.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}