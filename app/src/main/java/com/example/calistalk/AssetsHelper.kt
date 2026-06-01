package com.example.calistalk

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.ImageView
import java.io.IOException

class AssetsHelper(private val context: Context) {

    // Membaca gambar cover (awal.png) dari folder story/ceritaX/
    fun loadCoverImage(ceritaFolder: String, imageView: ImageView) {
        try {
            // Path: story/cerita1/awal.png
            val inputStream = context.assets.open("story/$ceritaFolder/awal.png")
            val bitmap = BitmapFactory.decodeStream(inputStream)
            imageView.setImageBitmap(bitmap)
        } catch (e: IOException) {
            e.printStackTrace()
            // Jika awal.png tidak ada, coba 1.png
            try {
                val inputStream = context.assets.open("story/$ceritaFolder/1.png")
                val bitmap = BitmapFactory.decodeStream(inputStream)
                imageView.setImageBitmap(bitmap)
            } catch (e2: IOException) {
                imageView.setImageResource(R.drawable.ic_book)
            }
        }
    }

    // Mendapatkan jumlah halaman cerita (file .png selain awal.png)
    fun getJumlahHalaman(ceritaFolder: String): Int {
        return getDaftarHalaman(ceritaFolder).size
    }

    // Mendapatkan daftar nama file halaman (exclude awal.png)
    fun getDaftarHalaman(ceritaFolder: String): List<String> {
        val halamanList = mutableListOf<String>()
        try {
            val list = context.assets.list("story/$ceritaFolder")
            list?.forEach { fileName ->
                if (fileName.endsWith(".png") && fileName != "awal.png") {
                    halamanList.add(fileName)
                }
            }
            // Urutkan berdasarkan nomor (1.png, 2.png, dst)
            halamanList.sortBy {
                it.replace(".png", "").toIntOrNull() ?: 0
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return halamanList
    }

    // Membaca gambar halaman tertentu
    fun loadHalamanImage(ceritaFolder: String, fileName: String, imageView: ImageView) {
        try {
            val inputStream = context.assets.open("story/$ceritaFolder/$fileName")
            val bitmap = BitmapFactory.decodeStream(inputStream)
            imageView.setImageBitmap(bitmap)
        } catch (e: IOException) {
            e.printStackTrace()
            imageView.setImageResource(R.drawable.ic_book)
        }
    }
}