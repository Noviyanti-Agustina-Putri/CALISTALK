package com.example.calistalk

import android.app.AlertDialog
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class PilihBendaActivity : AppCompatActivity() {

    // --- 1. Deklarasi View ---
    private lateinit var optionContainers: List<LinearLayout>
    private lateinit var optionImages: List<ImageView>
    private lateinit var optionTexts: List<TextView>

    private lateinit var tvInstruksi: TextView
    private lateinit var btnBack: ImageView

    // View Header
    private lateinit var headerTitle: TextView
    private lateinit var headerSubtitle: TextView
    private lateinit var headerIcon: ImageView

    private lateinit var assetsHelper: AssetsHelper // Tambahkan ini

    // --- 2. Data Soal ---
    private val daftarSoal = listOf(
        SoalPilihBenda(
            "Benda mana yang digunakan untuk membersihkan debu di lantai?",
            "benda",
            listOf(
                OpsiGambar("sapu", "Sapu"),
                OpsiGambar("sandal", "Sandal"),
                OpsiGambar("sendok", "Sendok"),
                OpsiGambar("topi", "Topi")
            ),
            "Sapu"
        ),
        SoalPilihBenda(
            "Benda mana yang biasanya dipakai untuk berjalan diluar rumah?",
            "benda",
            listOf(
                OpsiGambar("sapu", "Sapu"),
                OpsiGambar("sendal", "Sandal"),
                OpsiGambar("sendok", "Sendok"),
                OpsiGambar("topi", "Topi")
            ),
            "Sandal"
        ),
        // Contoh soal kategori Angka
        SoalPilihBenda(
            "Benda mana yang digunakaan diatas kepala",
            "benda", // Gambar diambil dari assets/angka/
            listOf(
                OpsiGambar("sapu", "Sapu"),
                OpsiGambar("sandal", "Sandal"),
                OpsiGambar("sendok", "Sendok"),
                OpsiGambar("topi", "Topi")
            ),
            "Topi"
        )
    )

    private var indexSoalAktif = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pilih_benda)

        assetsHelper = AssetsHelper(this) // Init Helper

        initUI()
        setupHeader()
        setupListeners()
        tampilkanSoal()
    }

    private fun initUI() {
        tvInstruksi = findViewById(R.id.tvInstruksi)

        btnBack = findViewById(R.id.btnBackGame)
        headerTitle = findViewById(R.id.title)
        headerSubtitle = findViewById(R.id.subtitle)
        headerIcon = findViewById(R.id.headerIcon)

        // Opsi Jawaban
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
    }

    private fun setupHeader() {
        headerTitle.text = "Pilih Benda"
        headerSubtitle.text = "Cari benda sesuai perintah"
        headerIcon.setImageResource(R.drawable.ic_hand)

        val colorGreen = ContextCompat.getColor(this, android.R.color.holo_green_dark)
        headerTitle.setTextColor(colorGreen)
        headerSubtitle.setTextColor(colorGreen)
    }

    private fun setupListeners() {
        btnBack.setOnClickListener { finish() }
    }

    private fun tampilkanSoal() {
        if (indexSoalAktif < daftarSoal.size) {
            val soal = daftarSoal[indexSoalAktif]
            tvInstruksi.text = soal.instruksi

            optionContainers.forEachIndexed { index, container ->
                val dataOpsi = soal.daftarOpsi[index]

                // Tambahkan pengecekan ini:
                val imageView = optionImages.getOrNull(index)
                if (imageView != null) {
                    assetsHelper.loadKuisImage(soal.folderGambar, dataOpsi.namaGambar, imageView)
                } else {
                    android.util.Log.e("DEBUG_ERROR", "ImageView pada index $index tidak ditemukan di layout!")
                }

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
        val soalSekarang = daftarSoal[indexSoalAktif]

        if (jawabanDipilih.equals(soalSekarang.kunciJawaban, ignoreCase = true)) {
            tampilkanPopUpBenar()
        } else {
            tampilkanPopUpSalah()
        }
    }

    // --- Pop Up Handlers ---
    private fun tampilkanPopUpBenar() {
        tampilkanDialogGambar(R.drawable.benar) {
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
        Toast.makeText(this, "Kamu pintar memilih benda!", Toast.LENGTH_SHORT).show()
        finish()
    }
}