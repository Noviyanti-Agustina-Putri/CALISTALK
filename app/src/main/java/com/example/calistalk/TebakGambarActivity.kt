package com.example.calistalk

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class TebakGambarActivity : AppCompatActivity() {

    private lateinit var imgSoal: ImageView
    private lateinit var btnOpsiA: Button
    private lateinit var btnOpsiB: Button
    private lateinit var btnOpsiC: Button
    private lateinit var btnOpsiD: Button
    private lateinit var btnBack: ImageView
    private lateinit var headerTitle: TextView
    private lateinit var headerSubtitle: TextView
    private lateinit var headerIcon: ImageView
    private lateinit var assetsHelper: AssetsHelper

    private val daftarSoal = listOf(
        SoalTebakGambar("buah", "apel", "Sepatu", "Apel", "Gajah", "Sepeda", "Apel"),
        SoalTebakGambar("buah", "pisang", "Pisang", "Jeruk", "Strawberry", "Mangga", "Pisang"),
        SoalTebakGambar("hewan", "kucing", "Kambing", "Sapi", "Kucing", "Singa", "Kucing"),
        SoalTebakGambar("hewan", "kambing", "Kambing", "Kelinci", "Kucing", "Kuda", "Kambing")
    )

    private var indexSoalAktif = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tebak_gambar)

        assetsHelper = AssetsHelper(this)
        initUI()
        setupHeader()
        setupListeners()
        tampilkanSoal()
    }

    private fun initUI() {
        imgSoal = findViewById(R.id.img_soal)
        btnOpsiA = findViewById(R.id.btn_opsi_a)
        btnOpsiB = findViewById(R.id.btn_opsi_b)
        btnOpsiC = findViewById(R.id.btn_opsi_c)
        btnOpsiD = findViewById(R.id.btn_opsi_d)

        // Cari langsung di root layout
        btnBack = findViewById(R.id.btnBackGame)
        headerTitle = findViewById(R.id.title)
        headerSubtitle = findViewById(R.id.subtitle)
        headerIcon = findViewById(R.id.headerIcon)
    }

    private fun setupHeader() {
        headerTitle.text = "Tebak Gambar"
        headerSubtitle.text = "Lihat gambar dan tebak namanya"
        headerIcon.setImageResource(R.drawable.ic_image)
    }

    private fun setupListeners() {
        btnBack.setOnClickListener { finish() }
        val buttons = listOf(btnOpsiA, btnOpsiB, btnOpsiC, btnOpsiD)
        buttons.forEach { btn -> btn.setOnClickListener { cekJawaban(btn) } }
    }

    private fun tampilkanSoal() {
        if (indexSoalAktif < daftarSoal.size) {
            val soal = daftarSoal[indexSoalAktif]
            assetsHelper.loadKuisImage(soal.folderGambar, soal.namaGambar, imgSoal)
            btnOpsiA.text = soal.opsiA
            btnOpsiB.text = soal.opsiB
            btnOpsiC.text = soal.opsiC
            btnOpsiD.text = soal.opsiD
        } else {
            tampilkanSelesai()
        }
    }

    private fun cekJawaban(btn: Button) {
        val jawabanBenar = daftarSoal[indexSoalAktif].jawabanBenar
        if (btn.text.toString().equals(jawabanBenar, ignoreCase = true)) {
            tampilkanDialogGambar(R.drawable.benar) {
                indexSoalAktif++
                tampilkanSoal()
            }
        } else {
            tampilkanDialogGambar(R.drawable.salah) { }
        }
    }

    private fun tampilkanDialogGambar(resId: Int, onEnd: () -> Unit) {
        val img = ImageView(this).apply { setImageResource(resId) }
        val dialog = AlertDialog.Builder(this).setView(img).setCancelable(false).create()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()
        img.postDelayed({ dialog.dismiss(); onEnd() }, 1500)
    }

    private fun tampilkanSelesai() {
        Toast.makeText(this, "Kamu hebat!", Toast.LENGTH_SHORT).show()
        finish()
    }
}