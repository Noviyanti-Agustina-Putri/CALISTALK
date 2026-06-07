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

    private lateinit var assetsHelper: AssetsHelper // Tambahkan ini

    private var currentIndex = 0

    // DATA DIUBAH: R.drawable.apel -> "apel" (String)
    private val dataBuah = listOf(
        ItemBelajar("1 Buah Apel", "apel", 1),
        ItemBelajar("2 Buah Apel", "apel", 2),
        ItemBelajar("3 Buah Strawberry", "strawberry", 3),
        ItemBelajar("4 Buah Jeruk", "jeruk", 4),
        ItemBelajar("5 Buah Pisang", "pisang", 5)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menghitung_benda_belajar)

        assetsHelper = AssetsHelper(this) // Init Helper

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

        // Hapus tampilan lama sebelum menampilkan yang baru
        containerBuah.removeAllViews()

        // Ulangi sebanyak jumlah item (misal: 2 apel, maka loop 2 kali)
        repeat(item.jumlah) {
            val imageView = ImageView(this)

            // Atur ukuran gambar
            imageView.layoutParams = LinearLayout.LayoutParams(150, 150)
            // Tambah sedikit margin agar tidak berdempetan
            (imageView.layoutParams as LinearLayout.LayoutParams).setMargins(10, 10, 10, 10)

            // --- PERUBAHAN DISINI ---
            // Pakai AssetsHelper untuk load gambar
            // Pastikan folder "buah" ada di dalam assets, atau ganti "buah" dengan "benda" jika kamu taruh buah di folder benda
            assetsHelper.loadKuisImage("buah", item.gambar, imageView)
            // -----------------------

            containerBuah.addView(imageView)
        }
    }
}