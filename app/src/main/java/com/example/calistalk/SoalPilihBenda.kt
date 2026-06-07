package com.example.calistalk

data class SoalPilihBenda(
    val instruksi: String,
    val folderGambar: String,           // Folder di assets (contoh: "benda")
    val daftarOpsi: List<OpsiGambar>,   // List opsi gambar
    val kunciJawaban: String
)