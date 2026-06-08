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
        if (imageView == null) return

        val cleanFileName = if (fileName.endsWith(".png")) fileName else "$fileName.png"

        val paths = listOf(
            "$folderName/gambar/$cleanFileName",
            "$folderName/$cleanFileName"
        )

        var loaded = false
        for (path in paths) {
            try {
                context.assets.open(path).use { stream ->
                    val bitmap = BitmapFactory.decodeStream(stream)
                    imageView.setImageBitmap(bitmap)
                    loaded = true
                    return // Berhasil, keluar dari fungsi
                }
            } catch (e: Exception) {
                android.util.Log.d("DEBUG_GAMBAR", "Gagal di path: $path")
            }
        }

        if (!loaded) {
            android.util.Log.e("DEBUG_GAMBAR", "Gambar tidak ditemukan di semua path: $cleanFileName")
            imageView.setImageResource(R.drawable.ic_game)
        }
    }
}