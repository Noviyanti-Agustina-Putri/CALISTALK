package com.example.calistalk

import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MenghitungBendaActivity : AppCompatActivity() {

    private lateinit var txtJudul: TextView
    private lateinit var containerBuah: LinearLayout
    private lateinit var leftArrow: ImageView
    private lateinit var rightArrow: ImageView

    private var currentIndex = 0

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.menghitung_benda_belajar)

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

    private fun tampilkanData() {

        val item = dataBuah[currentIndex]

        txtJudul.text = item.judul

        containerBuah.removeAllViews()

        repeat(item.jumlah) {

            val imageView = ImageView(this)

            imageView.layoutParams =
                LinearLayout.LayoutParams(
                    150,
                    150
                )

            imageView.setImageResource(item.gambar)

            containerBuah.addView(imageView)
        }
    }
}