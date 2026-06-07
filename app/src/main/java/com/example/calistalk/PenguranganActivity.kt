package com.example.calistalk

import android.os.Bundle
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class PenguranganActivity : AppCompatActivity() {

    private lateinit var containerKiri: GridLayout
    private lateinit var containerKanan: GridLayout
    private lateinit var containerHasil: GridLayout

    private lateinit var txtJumlah1: TextView
    private lateinit var txtJumlah2: TextView
    private lateinit var txtHasil: TextView

    private lateinit var leftArrow: ImageView
    private lateinit var rightArrow: ImageView

    private var currentIndex = 0

    private val daftarSoal = listOf(

        SoalPengurangan(
            2,
            1,
            1,
            R.drawable.apel
        ),

        SoalPengurangan(
            3,
            1,
            2,
            R.drawable.strawberry
        ),

        SoalPengurangan(
            4,
            2,
            2,
            R.drawable.jeruk
        ),

        SoalPengurangan(
            5,
            2,
            3,
            R.drawable.pisang
        ),

        SoalPengurangan(
            5,
            1,
            4,
            R.drawable.apel
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.pengurangan_sederhana)

        findViewById<ImageView>(R.id.btnBack)
            .setOnClickListener {
                finish()
            }

        containerKiri =
            findViewById(R.id.containerKiri)

        containerKanan =
            findViewById(R.id.containerKanan)

        containerHasil =
            findViewById(R.id.containerHasil)

        txtJumlah1 =
            findViewById(R.id.txtJumlah1)

        txtJumlah2 =
            findViewById(R.id.txtJumlah2)

        txtHasil =
            findViewById(R.id.txtHasil)

        leftArrow =
            findViewById(R.id.leftArrow)

        rightArrow =
            findViewById(R.id.rightArrow)

        tampilkanSoal()

        rightArrow.setOnClickListener {

            if (currentIndex < daftarSoal.size - 1) {

                currentIndex++

                tampilkanSoal()
            }
        }

        leftArrow.setOnClickListener {

            if (currentIndex > 0) {

                currentIndex--

                tampilkanSoal()
            }
        }
    }

    private fun tampilkanSoal() {

        val soal = daftarSoal[currentIndex]

        txtJumlah1.text = soal.angkaKiri.toString()
        txtJumlah2.text = soal.angkaKanan.toString()
        txtHasil.text = soal.hasil.toString()

        containerKiri.removeAllViews()
        containerKanan.removeAllViews()
        containerHasil.removeAllViews()

        tampilkanGambar(
            containerKiri,
            soal.angkaKiri,
            soal.gambar
        )

        tampilkanGambar(
            containerKanan,
            soal.angkaKanan,
            soal.gambar
        )

        tampilkanGambar(
            containerHasil,
            soal.hasil,
            soal.gambar
        )
    }
    private fun tampilkanGambar(
        container: GridLayout,
        jumlah: Int,
        gambar: Int
    ) {
        repeat(jumlah) {

            val imageView = ImageView(this)

            val size =
                (35 * resources.displayMetrics.density).toInt()

            val params = GridLayout.LayoutParams()

            params.width = size
            params.height = size

            imageView.layoutParams = params
            imageView.setImageResource(gambar)

            container.addView(imageView)
        }
    }
}