package com.example.calistalk

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class TebakGambarActivity : AppCompatActivity() {

    private lateinit var imgSoal: ImageView
    private lateinit var btnOpsiA: Button
    private lateinit var btnOpsiB: Button
    private lateinit var btnOpsiC: Button
    private lateinit var btnOpsiD: Button
    private lateinit var btnBack: ImageView

    private lateinit var assetsHelper: AssetsHelper

    private val daftarSoal = listOf(
        // Perhatikan parameter pertama disesuaikan dengan properti "folderAssets" di model datamu
        SoalTebakGambar("drawable", "apel", "Sepatu", "Apel", "Gajah", "Sepeda", "Apel"),
        SoalTebakGambar("drawable", "pisang", "Pisang", "Jeruk", "Strawberry", "Mangga", "Pisang"),
        SoalTebakGambar("drawable", "kucing", "Kambing", "Sapi", "Kucing", "Singa", "Kucinf"),
        SoalTebakGambar("drawable", "kambing", "Kambing", "Kelinci", "Kucing", "Kuda", "Kambing")
    )

    private var indexSoalAktif = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tebak_gambar)

        assetsHelper = AssetsHelper(this)

        // Inisialisasi komponen UI
        imgSoal = findViewById(R.id.img_soal)
        btnOpsiA = findViewById(R.id.btn_opsi_a)
        btnOpsiB = findViewById(R.id.btn_opsi_b)
        btnOpsiC = findViewById(R.id.btn_opsi_c)
        btnOpsiD = findViewById(R.id.btn_opsi_d)
        btnBack = findViewById(R.id.btnBackGame)

        btnBack.setOnClickListener { finish() }

        tampilkanSoal()

        // Set listener klik untuk mendeteksi pilihan jawaban dari anak
        btnOpsiA.setOnClickListener { periksaJawaban(btnOpsiA.text.toString()) }
        btnOpsiB.setOnClickListener { periksaJawaban(btnOpsiB.text.toString()) }
        btnOpsiC.setOnClickListener { periksaJawaban(btnOpsiC.text.toString()) }
        btnOpsiD.setOnClickListener { periksaJawaban(btnOpsiD.text.toString()) }
    }

    private fun tampilkanSoal() {
        if (indexSoalAktif < daftarSoal.size) {
            val soal = daftarSoal[indexSoalAktif]

            // Memanggil properti "FileGambar" (F kapital) sesuai dengan isi file SoalTebakGambar.kt kamu
            val resId = resources.getIdentifier(soal.FileGambar, "drawable", packageName)

            if (resId != 0) {
                // Jika file gambar ditemukan di drawable, langsung pasang
                imgSoal.setImageResource(resId)
            } else {
                // Gambar cadangan kalau nama file di daftarSoal gak pas sama yang di drawable
                imgSoal.setImageResource(R.drawable.ic_game)
            }
            btnOpsiA.text = soal.opsiA
            btnOpsiB.text = soal.opsiB
            btnOpsiC.text = soal.opsiC
            btnOpsiD.text = soal.opsiD
        } else {
            Toast.makeText(this, "Hebat! Semua game berhasil diselesaikan!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun periksaJawaban(jawabanDipilih: String) {
        val soalSekarang = daftarSoal[indexSoalAktif]

        if (jawabanDipilih == soalSekarang.jawabanBenar) {
            tampilkanPopUpBenar()
        } else {
            tampilkanPopUpSalah()
        }
    }

    private fun tampilkanPopUpBenar() {
        val imageView = ImageView(this)
        imageView.setImageResource(R.drawable.benar)
        imageView.adjustViewBounds = true

        val dialog = AlertDialog.Builder(this)
            .setView(imageView)
            .setCancelable(false)
            .create()

        // Menghilangkan background bawaan dialog biar pojokan bulat dari gambar kelihatan rapi
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()

        // Otomatis hilang setelah 2 detik, lalu index nambah dan soal di-update
        imageView.postDelayed({
            dialog.dismiss()
            indexSoalAktif++
            tampilkanSoal()
        }, 2000)
    }

    private fun tampilkanPopUpSalah() {
        val imageView = ImageView(this)
        imageView.setImageResource(R.drawable.salah)
        imageView.adjustViewBounds = true

        val dialog = AlertDialog.Builder(this)
            .setView(imageView)
            .create()

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()

        // Otomatis hilang setelah 1.5 detik biar anak bisa nyoba lagi
        imageView.postDelayed({
            dialog.dismiss()
        }, 1500)
    }
}