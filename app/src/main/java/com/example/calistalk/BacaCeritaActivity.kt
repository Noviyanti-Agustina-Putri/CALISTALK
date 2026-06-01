package com.example.calistalk

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2

class BacaCeritaActivity : AppCompatActivity() {

    private lateinit var tvJudulCerita: TextView
    private lateinit var tvHalaman: TextView
    private lateinit var viewPager: ViewPager2
    private lateinit var btnPrevious: Button
    private lateinit var btnNext: Button
    private lateinit var assetsHelper: AssetsHelper

    // Sidebar menus
    private lateinit var menuCatatan: LinearLayout
    private lateinit var menuStory: LinearLayout
    private lateinit var menuPengaturan: LinearLayout

    private var ceritaFolder: String = ""
    private var judulCerita: String = ""
    private var daftarHalaman: List<String> = emptyList()
    private var currentPage: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_baca_cerita)

        tvJudulCerita = findViewById(R.id.tvJudulCerita)
        tvHalaman = findViewById(R.id.tvHalaman)
        viewPager = findViewById(R.id.viewPager)
        btnPrevious = findViewById(R.id.btnPrevious)
        btnNext = findViewById(R.id.btnNext)

        // Sidebar
        menuCatatan = findViewById(R.id.menuCatatan)
        menuStory = findViewById(R.id.menuStory)
        menuPengaturan = findViewById(R.id.menuPengaturan)

        assetsHelper = AssetsHelper(this)

        // Ambil data dari intent
        ceritaFolder = intent.getStringExtra("folder_name") ?: ""
        judulCerita = intent.getStringExtra("judul") ?: ""

        tvJudulCerita.text = judulCerita

        // Setup Sidebar
        setupSidebar()

        // Dapatkan daftar semua file halaman (1.png, 2.png, dst) exclude awal.png
        daftarHalaman = assetsHelper.getDaftarHalaman(ceritaFolder)

        if (daftarHalaman.isEmpty()) {
            tvHalaman.text = "0/0"
            return
        }

        // Setup ViewPager
        val adapter = HalamanAdapter()
        viewPager.adapter = adapter

        updatePageIndicator()

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                currentPage = position
                updatePageIndicator()
            }
        })

        btnPrevious.setOnClickListener {
            if (currentPage > 0) {
                viewPager.currentItem = currentPage - 1
            }
        }

        btnNext.setOnClickListener {
            if (currentPage < daftarHalaman.size - 1) {
                viewPager.currentItem = currentPage + 1
            }
        }
    }

    private fun setupSidebar() {
        menuCatatan.setOnClickListener {
            startActivity(Intent(this, JurnalOrtuActivity::class.java))
            finish()
        }

        menuStory.setOnClickListener {
            startActivity(Intent(this, StoryTellingActivity::class.java))
            finish()
        }

        menuPengaturan.setOnClickListener {
            startActivity(Intent(this, PengaturanActivity::class.java))
            finish()
        }
    }

    private fun updatePageIndicator() {
        tvHalaman.text = "${currentPage + 1}/${daftarHalaman.size}"

        btnPrevious.isEnabled = currentPage > 0
        btnNext.isEnabled = currentPage < daftarHalaman.size - 1

        btnPrevious.alpha = if (currentPage > 0) 1.0f else 0.5f
        btnNext.alpha = if (currentPage < daftarHalaman.size - 1) 1.0f else 0.5f
    }

    inner class HalamanAdapter : RecyclerView.Adapter<HalamanAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_halaman_cerita, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val fileName = daftarHalaman[position]
            assetsHelper.loadHalamanImage(ceritaFolder, fileName, holder.ivHalaman)
        }

        override fun getItemCount(): Int = daftarHalaman.size

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val ivHalaman: ImageView = itemView.findViewById(R.id.ivHalaman)
        }
    }
}