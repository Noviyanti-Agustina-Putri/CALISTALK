package com.example.calistalk

import android.app.AlertDialog
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat

class CocokkanSuaraActivity : AppCompatActivity() {

    // --- 1. Deklarasi View ---
    private lateinit var btnPlayAudio: ImageView

    private lateinit var optionContainers: List<CardView>
    private lateinit var optionImages: List<ImageView>
    private lateinit var optionTexts: List<TextView>

    private lateinit var btnBack: ImageView
    private lateinit var headerTitle: TextView
    private lateinit var headerSubtitle: TextView
    private lateinit var headerIcon: ImageView

    private var mediaPlayer: MediaPlayer? = null
    private lateinit var assetsHelper: AssetsHelper // Tambahkan ini

    // --- 2. Data Soal ---
    // Pastikan file suara ada di res/raw/kucing.mp3
    // Pastikan file gambar ada di assets/hewan/kucing.png
    private val daftarSoal = listOf(
        SoalCocokkanSuara(
            "cat", "hewan",
            listOf(
                OpsiGambar("kucing", "Kucing"),
                OpsiGambar("monyet", "Monyet"),
                OpsiGambar("ular", "Ular"),
                OpsiGambar("bebek", "Bebek")
            ),
            "Kucing"
        ),
        SoalCocokkanSuara(
            "lion", "hewan",
            listOf(
                OpsiGambar("kucing", "Kucing"),
                OpsiGambar("monyet", "Monyet"),
                OpsiGambar("anjing", "Anjing"),
                OpsiGambar("singa", "Singa")
            ),
            "Singa"
        ),
    )

    private var indexSoalAktif = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cocokkan_suara)

        assetsHelper = AssetsHelper(this) // Init Helper

        initUI()
        setupHeader()
        setupListeners()
        tampilkanSoal()
    }

    private fun initUI() {
        btnPlayAudio = findViewById(R.id.btnPlayAudio)

        // Ubah menjadi CardView sesuai dengan perubahan di XML
        optionContainers = listOf(
            findViewById(R.id.btn_opsi_a),
            findViewById(R.id.btn_opsi_b),
            findViewById(R.id.btn_opsi_c),
            findViewById(R.id.btn_opsi_d)
        )

        optionImages = listOf(
            findViewById(R.id.img_a),
            findViewById(R.id.img_b),
            findViewById(R.id.img_c),
            findViewById(R.id.img_d)
        )
        optionTexts = listOf(
            findViewById(R.id.txt_a),
            findViewById(R.id.txt_b),
            findViewById(R.id.txt_c),
            findViewById(R.id.txt_d)
        )

        btnBack = findViewById(R.id.btnBackGame)
        headerTitle = findViewById(R.id.title)
        headerSubtitle = findViewById(R.id.subtitle)
        headerIcon = findViewById(R.id.headerIcon)
    }

    private fun setupHeader() {
        headerTitle.text = "Cocokkan Suara"
        headerSubtitle.text = "Pilih gambar sesuai suara"
        headerIcon.setImageResource(R.drawable.ic_volume)

        val colorBlue = ContextCompat.getColor(this, android.R.color.holo_blue_dark)
        headerTitle.setTextColor(colorBlue)
        headerSubtitle.setTextColor(colorBlue)
    }

    private fun setupListeners() {
        btnBack.setOnClickListener { finish() }

        btnPlayAudio.setOnClickListener {
            btnPlayAudio.animate().scaleX(0.9f).scaleY(0.9f).setDuration(100).withEndAction {
                btnPlayAudio.animate().scaleX(1f).scaleY(1f).setDuration(100)
            }.start()
            putarSuara()
        }
    }

    // Fungsi Suara (Tetap membaca dari raw)
    private fun putarSuara() {
        mediaPlayer?.release()
        val namaFileSuara = daftarSoal[indexSoalAktif].namaSuara
        val resId = resources.getIdentifier(namaFileSuara, "raw", packageName)

        if (resId != 0) {
            mediaPlayer = MediaPlayer.create(this, resId)
            mediaPlayer?.start()
            mediaPlayer?.setOnCompletionListener {
                it.release()
                mediaPlayer = null
            }
        } else {
            Toast.makeText(this, "Suara tidak ditemukan", Toast.LENGTH_SHORT).show()
        }
    }

    private fun tampilkanSoal() {
        if (indexSoalAktif < daftarSoal.size) {
            val soal = daftarSoal[indexSoalAktif]

            // Looping untuk mengisi gambar
            optionContainers.forEachIndexed { index, container ->
                val dataOpsi = soal.daftarOpsi[index]

                // --- PERUBAHAN DISINI (Menggunakan Assets) ---
                // Parameter: (Folder di Data Class, NamaFile di OpsiGambar, ImageView)
                assetsHelper.loadKuisImage(soal.folderGambar, dataOpsi.namaGambar, optionImages[index])
                // ----------------------------------------------

                optionTexts[index].text = dataOpsi.teks

                container.setOnClickListener {
                    cekJawaban(dataOpsi.teks)
                }
            }
        } else {
            tampilkanSelesai()
        }
    }

    private fun cekJawaban(jawabanDipilih: String) {
        if (jawabanDipilih == daftarSoal[indexSoalAktif].kunciJawaban) {
            tampilkanPopUpBenar()
        } else {
            tampilkanPopUpSalah()
        }
    }

    // --- Pop Up Handlers ---
    private fun tampilkanPopUpBenar() {
        tampilkanDialogGambar(R.drawable.benar) {
            mediaPlayer?.release()
            mediaPlayer = null
            indexSoalAktif++
            tampilkanSoal()
        }
    }

    private fun tampilkanPopUpSalah() {
        tampilkanDialogGambar(R.drawable.salah) { }
    }

    private fun tampilkanDialogGambar(resIdGambar: Int, onDismissAction: () -> Unit) {
        val imageView = ImageView(this).apply {
            setImageResource(resIdGambar)
            adjustViewBounds = true
        }

        val dialog = AlertDialog.Builder(this)
            .setView(imageView)
            .setCancelable(false)
            .create()

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()

        imageView.postDelayed({
            if (dialog.isShowing) dialog.dismiss()
            onDismissAction()
        }, if (resIdGambar == R.drawable.benar) 2000 else 1500)
    }

    private fun tampilkanSelesai() {
        mediaPlayer?.release()
        Toast.makeText(this, "Hebat! Telingamu tajam!", Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}