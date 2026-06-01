package com.example.calistalk

data class CeritaItem(
    val id: Int,
    val judul: String,
    val deskripsi: String,
    val folderName: String  // folder berbeda untuk setiap cerita
)

object StoryData {
    val daftarCerita = listOf(
        CeritaItem(1, "Si Monyet dan Tupai", "Cerita persahabatan monyet dan tupai di hutan rimba", "cerita1"),
        CeritaItem(2, "Si Kancil dan Pak Tani", "Kisah si Kancil yang cerdik dan Pak Tani yang baik hati", "cerita2"),
        CeritaItem(3, "Jack dan Pohon Kacang Ajaib", "Cerita tentang anak baik hati yang mendapat keajaiban", "cerita3"),
        CeritaItem(4, "4 Kata Ajaib", "Belajar 4 kata ajaib yang membuat hidup lebih bahagia", "cerita4"),
        CeritaItem(5, "Indahnya Saling Tolong Menolong", "Cerita tentang pentingnya saling membantu", "cerita5"),
        CeritaItem(6, "Hadiah Persahabatan dari Kurcaci Tua", "Belajar 4 kata ajaib yang membuat hidup lebih bahagia", "cerita6")
    )
}