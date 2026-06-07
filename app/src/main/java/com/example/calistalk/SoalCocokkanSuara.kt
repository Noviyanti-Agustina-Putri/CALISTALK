package com.example.calistalk

data class SoalCocokkanSuara(
    val namaSuara: String,              // Nama file di res/raw (contoh: "kucing")
    val folderGambar: String,           // Folder di assets (contoh: "hewan")
    val daftarOpsi: List<OpsiGambar>,   // List opsi gambar
    val kunciJawaban: String            // Teks jawaban benar
)