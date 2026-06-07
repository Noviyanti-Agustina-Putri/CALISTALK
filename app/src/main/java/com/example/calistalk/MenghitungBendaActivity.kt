package com.example.calistalk

import android.os.Bundle
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class MenghitungBendaActivity : AppCompatActivity() {

    private lateinit var txtJudul: TextView
    private lateinit var containerBuah: GridLayout
    private lateinit var leftArrow: ImageView
    private lateinit var rightArrow: ImageView
    private lateinit var txtPertanyaan: TextView
    private var currentIndex = 0
    private var skor = 0

    private val dataBuah = listOf(

        ItemBelajar(
            "1 Buah Apel",
            R.drawable.apel,
            1
        ),

        ItemBelajar(
            "2 Buah Apel",
            R.drawable.apel,
            2
        ),

        ItemBelajar(
            "3 Buah Strawberry",
            R.drawable.strawberry,
            3
        ),

        ItemBelajar(
            "4 Buah Jeruk",
            R.drawable.jeruk,
            4
        ),

        ItemBelajar(
            "5 Buah Pisang",
            R.drawable.pisang,
            5
        )
    )

    private val dataPercobaan = listOf(

        SoalPercobaan(
            "Hmm, Buah Apel ada berapa ya?",
            R.drawable.apel,
            1
        ),

        SoalPercobaan(
            "Hmm, Buah Strawberry ada berapa ya?",
            R.drawable.strawberry,
            2
        ),

        SoalPercobaan(
            "Hmm, Buah Jeruk ada berapa ya?",
            R.drawable.jeruk,
            3
        ),

        SoalPercobaan(
            "Hmm, Buah Pisang ada berapa ya?",
            R.drawable.pisang,
            4
        ),

        SoalPercobaan(
            "Hmm, Buah Apel ada berapa ya?",
            R.drawable.apel,
            5
        ),

        SoalPercobaan(
            "Hmm, Buah Strawberry ada berapa ya?",
            R.drawable.strawberry,
            6
        ),

        SoalPercobaan(
            "Hmm, Buah Jeruk ada berapa ya?",
            R.drawable.jeruk,
            7
        ),

        SoalPercobaan(
            "Hmm, Buah Pisang ada berapa ya?",
            R.drawable.pisang,
            8
        ),

        SoalPercobaan(
            "Hmm, Buah Apel ada berapa ya?",
            R.drawable.apel,
            9
        ),

        SoalPercobaan(
            "Hmm, Buah Strawberry ada berapa ya?",
            R.drawable.strawberry,
            10
        )
    )

    private val dataKuis = listOf(

        SoalKuis(
            "Buah Apel ada berapa ya?",
            R.drawable.apel,
            1
        ),

        SoalKuis(
            "Buah Strawberry ada berapa ya?",
            R.drawable.strawberry,
            2
        ),

        SoalKuis(
            "Buah Jeruk ada berapa ya?",
            R.drawable.jeruk,
            3
        ),

        SoalKuis(
            "Buah Pisang ada berapa ya?",
            R.drawable.pisang,
            4
        ),

        SoalKuis(
            "Buah Apel ada berapa ya?",
            R.drawable.apel,
            5
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mode = intent.getStringExtra("MODE")

        when (mode) {

            "PERCOBAAN" -> {

                setContentView(R.layout.menghitung_benda_percobaan)
                findViewById<ImageView>(R.id.btnBack)
                    .setOnClickListener {
                        finish()
                    }

                txtJudul = findViewById(R.id.txtJudul)
                containerBuah = findViewById(R.id.containerBuah)

                leftArrow = findViewById(R.id.leftArrow)
                rightArrow = findViewById(R.id.rightArrow)

                tampilkanPercobaan()

                rightArrow.setOnClickListener {

                    if (currentIndex < dataPercobaan.size - 1) {
                        currentIndex++
                        tampilkanPercobaan()
                    }
                }

                leftArrow.setOnClickListener {

                    if (currentIndex > 0) {
                        currentIndex--
                        tampilkanPercobaan()
                    }
                }
            }

            "KUIS" -> {
                setContentView(R.layout.menghitung_benda_kuis)
                findViewById<ImageView>(R.id.btnBack)
                    .setOnClickListener {
                        finish()
                    }

                txtPertanyaan =
                    findViewById(R.id.txtPertanyaan)

                containerBuah =
                    findViewById(R.id.containerBuah)

                findViewById<CardView>(R.id.card1)
                    .setOnClickListener {
                        cekJawaban(1)
                    }

                findViewById<CardView>(R.id.card2)
                    .setOnClickListener {
                        cekJawaban(2)
                    }

                findViewById<CardView>(R.id.card3)
                    .setOnClickListener {
                        cekJawaban(3)
                    }

                findViewById<CardView>(R.id.card4)
                    .setOnClickListener {
                        cekJawaban(4)
                    }

                findViewById<CardView>(R.id.card5)
                    .setOnClickListener {
                        cekJawaban(5)
                    }

                tampilkanKuis()
            }

            else -> {

                setContentView(R.layout.menghitung_benda_belajar)
                findViewById<ImageView>(R.id.btnBack)
                    .setOnClickListener {
                        finish()
                    }

                txtJudul = findViewById(R.id.txtJudul)
                containerBuah = findViewById(R.id.containerBuah)

                leftArrow = findViewById(R.id.leftArrow)
                rightArrow = findViewById(R.id.rightArrow)

                tampilkanData()

                rightArrow.setOnClickListener {

                    if (currentIndex < dataBuah.size - 1) {
                        currentIndex++
                        tampilkanData()
                    }
                }

                leftArrow.setOnClickListener {

                    if (currentIndex > 0) {
                        currentIndex--
                        tampilkanData()
                    }
                }
            }
        }
    }

    private fun tampilkanData() {

        val item = dataBuah[currentIndex]

        txtJudul.text = item.judul

        containerBuah.removeAllViews()

        containerBuah.columnCount =
            if (item.jumlah < 5) item.jumlah else 5

        repeat(item.jumlah) {

            val imageView = ImageView(this)

            val params = GridLayout.LayoutParams()

            params.width = 100
            params.height = 100

            params.setMargins(
                8,
                8,
                8,
                8
            )

            imageView.layoutParams = params

            imageView.setImageResource(item.gambar)

            containerBuah.addView(imageView)
        }
    }

    private fun tampilkanPercobaan() {

        val item = dataPercobaan[currentIndex]

        txtJudul.text = item.pertanyaan

        containerBuah.removeAllViews()

        containerBuah.columnCount =
            if (item.jumlah < 5) item.jumlah else 5

        repeat(item.jumlah) {

            val imageView = ImageView(this)

            val params = GridLayout.LayoutParams()

            params.width = 100
            params.height = 100

            params.setMargins(
                8,
                8,
                8,
                8
            )

            imageView.layoutParams = params

            imageView.setImageResource(item.gambar)

            containerBuah.addView(imageView)
        }
    }

    private fun tampilkanKuis() {

        val soal = dataKuis[currentIndex]

        txtPertanyaan.text = soal.pertanyaan

        containerBuah.removeAllViews()

        containerBuah.columnCount =
            if (soal.jawaban < 5) soal.jawaban else 5

        repeat(soal.jawaban) {

            val imageView = ImageView(this)

            val params = GridLayout.LayoutParams()

            params.width = 100
            params.height = 100

            params.setMargins(
                8,
                8,
                8,
                8
            )

            imageView.layoutParams = params

            imageView.scaleType =
                ImageView.ScaleType.FIT_CENTER

            imageView.setImageResource(
                soal.gambar
            )

            containerBuah.addView(imageView)
        }
    }

    private fun cekJawaban(jawabanUser: Int) {

        val soal = dataKuis[currentIndex]

        if (jawabanUser == soal.jawaban) {

            skor++

            Toast.makeText(
                this,
                "Hebat!",
                Toast.LENGTH_SHORT
            ).show()

            lanjutSoal()

        } else {

            Toast.makeText(
                this,
                "Coba Lagi",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun lanjutSoal() {

        currentIndex++

        if (currentIndex < dataKuis.size) {

            tampilkanKuis()

        } else {

            Toast.makeText(
                this,
                "Kuis Selesai!\nSkor: $skor",
                Toast.LENGTH_LONG
            ).show()

            finish()
        }
    }
}