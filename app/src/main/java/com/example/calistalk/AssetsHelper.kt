package com.example.calistalk

import android.content.Context
import android.graphics.BitmapFactory
import android.widget.ImageView
import java.io.IOException

class AssetsHelper(private val context: Context) {

    private fun tryLoadImage(path: String, imageView: ImageView, defaultRes: Int = R.drawable.ic_book): Boolean {
        return try {
            context.assets.open(path).use { inputStream ->
                val bitmap = BitmapFactory.decodeStream(inputStream)
                if (bitmap != null) {
                    imageView.setImageBitmap(bitmap)
                    true
                } else {
                    imageView.setImageResource(defaultRes)
                    false
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
            imageView.setImageResource(defaultRes)
            false
        }
    }

    // --- LOGIKA CERITA ---
    fun loadCoverImage(ceritaFolder: String, imageView: ImageView) {
        val success = tryLoadImage("story/$ceritaFolder/awal.png", imageView)
        if (!success) {
            val successSecond = tryLoadImage("story/$ceritaFolder/1.png", imageView)
            if (!successSecond) imageView.setImageResource(R.drawable.ic_book)
        }
    }

    fun getJumlahHalaman(ceritaFolder: String): Int = getDaftarHalaman(ceritaFolder).size

    fun getDaftarHalaman(ceritaFolder: String): List<String> {
        val halamanList = mutableListOf<String>()
        try {
            val files = context.assets.list("story/$ceritaFolder")
            files?.forEach { fileName ->
                if (fileName.endsWith(".png") && fileName != "awal.png") {
                    halamanList.add(fileName)
                }
            }
            halamanList.sortWith(compareBy { it.removeSuffix(".png").toIntOrNull() ?: Int.MAX_VALUE })
        } catch (e: IOException) { e.printStackTrace() }
        return halamanList
    }

    fun loadHalamanImage(ceritaFolder: String, fileName: String, imageView: ImageView) {
        tryLoadImage("story/$ceritaFolder/$fileName", imageView)
    }

    fun loadKuisImage(folderName: String, fileName: String, imageView: ImageView?) {
        // 1. Cek apakah imageView null. Jika ya, hentikan fungsi agar tidak crash
        if (imageView == null) {
            android.util.Log.e("DEBUG_GAMBAR", "ImageView null, membatalkan loading.")
            return
        }

        val cleanFileName = if (fileName.endsWith(".png")) fileName else "$fileName.png"
        val path = if (folderName == "hewan") "hewan/gambar/$cleanFileName" else "$folderName/$cleanFileName"

        try {
            context.assets.open(path).use { stream ->
                val bitmap = android.graphics.BitmapFactory.decodeStream(stream)
                if (bitmap != null) {
                    imageView.setImageBitmap(bitmap)
                } else {
                    imageView.setImageResource(R.drawable.ic_game)
                }
            }
        } catch (e: Exception) {
            android.util.Log.e("DEBUG_GAMBAR", "Gagal cari: $path", e)
            imageView.setImageResource(R.drawable.ic_game)
        }
    }

    // Mendapatkan daftar semua gambar dari folder tertentu (misal: "hewan/gambar")
    fun getDaftarGambarKuis(folderName: String): List<String> {
        val path = if (folderName == "hewan") "hewan/gambar" else folderName
        return try {
            context.assets.list(path)?.filter { it.endsWith(".png") } ?: emptyList()
        } catch (e: IOException) {
            e.printStackTrace()
            emptyList()
        }
    }
}