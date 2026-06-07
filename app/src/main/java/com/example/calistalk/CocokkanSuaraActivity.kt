package com.example.calistalk

import android.app.AlertDialog
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class CocokkanSuaraActivity : AppCompatActivity() {

    private lateinit var btnPlay: ImageView
    private lateinit var btnOpsiA: Button
    private lateinit var btnOpsiB: Button
    private lateinit var btnOpsiC: Button
    private lateinit var btnOpsiD: Button
    private lateinit var btnBack: ImageView

    private var mediaPlayer: MediaPlayer? = null
    private var indexSoalAktif = 0

    private val daftarSoal = listOf(
        SoalCocokkanSuara("raw", "kucing", "Kucing", "Monyet", "Ular", "Bebek", "Kucing"),
        SoalCocokkanSuara("raw", "monyet", "Kucing", "Monyet", "Ular", "Bebek", "Monyet")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cocokkan_suara)

        btnPlay = findViewById(R.id.btnPlayAudio)
        btnOpsiA = findViewById(R.id.btn_opsi_a)
        btnOpsiB = findViewById(R.id.btn_opsi_b)
        btnOpsiC = findViewById(R.id.btn_opsi_c)
        btnOpsiD = findViewById(R.id.btn_opsi_d)
        btnBack = findViewById(R.id.btnBackGame)

        btnBack.setOnClickListener { finish() }
        btnPlay.setOnClickListener { putarSuara() }

        tampilkanSoal()

        btnOpsiA.setOnClickListener { periksaJawaban(btnOpsiA.text.toString()) }
        btnOpsiB.setOnClickListener { periksaJawaban(btnOpsiB.text.toString()) }
        btnOpsiC.setOnClickListener { periksaJawaban(btnOpsiC.text.toString()) }
        btnOpsiD.setOnClickListener { periksaJawaban(btnOpsiD.text.toString()) }
    }

    private fun putarSuara() {
        mediaPlayer?.release()
        val suara = daftarSoal[indexSoalAktif].FileSuara
        val resId = resources.getIdentifier(suara, "raw", packageName)
        if (resId != 0) {
            mediaPlayer = MediaPlayer.create(this, resId)
            mediaPlayer?.start()
        }
    }

    private fun tampilkanSoal() {
        val soal = daftarSoal[indexSoalAktif]
        btnOpsiA.text = soal.opsiA
        btnOpsiB.text = soal.opsiB
        btnOpsiC.text = soal.opsiC
        btnOpsiD.text = soal.opsiD
    }

    private fun periksaJawaban(jawabanDipilih: String) {
        if (jawabanDipilih == daftarSoal[indexSoalAktif].jawabanBenar) {
            tampilkanPopUpBenar()
        } else {
            tampilkanPopUpSalah()
        }
    }

    private fun tampilkanPopUpBenar() {
        val imageView = ImageView(this)
        imageView.setImageResource(R.drawable.benar)
        val dialog = AlertDialog.Builder(this).setView(imageView).setCancelable(false).create()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()
        imageView.postDelayed({
            dialog.dismiss()
            indexSoalAktif++
            if (indexSoalAktif < daftarSoal.size) tampilkanSoal() else finish()
        }, 2000)
    }

    private fun tampilkanPopUpSalah() {
        val imageView = ImageView(this)
        imageView.setImageResource(R.drawable.salah)
        val dialog = AlertDialog.Builder(this).setView(imageView).create()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()
        imageView.postDelayed({ dialog.dismiss() }, 1500)
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
    }
}