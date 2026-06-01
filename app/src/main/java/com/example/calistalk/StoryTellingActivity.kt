package com.example.calistalk

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class StoryTellingActivity : AppCompatActivity() {

    private lateinit var gridCerita: GridView
    private lateinit var menuCatatan: LinearLayout
    private lateinit var menuStory: LinearLayout
    private lateinit var menuPengaturan: LinearLayout
    private lateinit var assetsHelper: AssetsHelper

    // Gunakan StoryData.daftarCerita
    private val daftarCerita = StoryData.daftarCerita

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_story_telling)

        gridCerita = findViewById(R.id.gridCerita)
        menuCatatan = findViewById(R.id.menuCatatan)
        menuStory = findViewById(R.id.menuStory)
        menuPengaturan = findViewById(R.id.menuPengaturan)
        assetsHelper = AssetsHelper(this)

        setupSidebar()
        setupGridView()
    }

    private fun setupSidebar() {
        menuCatatan.setOnClickListener {
            startActivity(Intent(this, JurnalOrtuActivity::class.java))
            finish()
        }

        menuStory.setOnClickListener {
            // Already here
        }

        menuPengaturan.setOnClickListener {
            startActivity(Intent(this, PengaturanActivity::class.java))
            finish()
        }
    }

    private fun setupGridView() {
        val adapter = CeritaGridAdapter()
        gridCerita.adapter = adapter

        gridCerita.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val cerita = daftarCerita[position]
            val intent = Intent(this, BacaCeritaActivity::class.java)
            intent.putExtra("cerita_id", cerita.id)
            intent.putExtra("judul", cerita.judul)
            intent.putExtra("folder_name", cerita.folderName)
            startActivity(intent)
        }
    }

    inner class CeritaGridAdapter : BaseAdapter() {

        override fun getCount(): Int = daftarCerita.size

        override fun getItem(position: Int): Any = daftarCerita[position]

        override fun getItemId(position: Int): Long = position.toLong()

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var view = convertView
            if (view == null) {
                view = LayoutInflater.from(this@StoryTellingActivity)
                    .inflate(R.layout.item_cerita_grid, parent, false)
            }

            val cerita = daftarCerita[position]

            val ivCover = view!!.findViewById<ImageView>(R.id.ivCover)
            val tvJudul = view.findViewById<TextView>(R.id.tvJudul)
            val tvDeskripsi = view.findViewById<TextView>(R.id.tvDeskripsi)

            tvJudul.text = cerita.judul
            tvDeskripsi.text = cerita.deskripsi

            // Load cover dari folder masing-masing (cerita1, cerita2, dst)
            assetsHelper.loadCoverImage(cerita.folderName, ivCover)

            return view
        }
    }
}