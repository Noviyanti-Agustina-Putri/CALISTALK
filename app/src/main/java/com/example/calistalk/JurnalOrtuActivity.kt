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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class JurnalOrtuActivity : AppCompatActivity() {

    private lateinit var dbHelper: JurnalDatabaseHelper
    private lateinit var rvTanggal: RecyclerView
    private lateinit var btnTambah: Button
    private lateinit var tanggalAdapter: TanggalAdapter

    // Sidebar menus
    private lateinit var menuCatatan: LinearLayout
    private lateinit var menuStory: LinearLayout
    private lateinit var menuPengaturan: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jurnal_ortu)

        dbHelper = JurnalDatabaseHelper(this)
        rvTanggal = findViewById(R.id.rvTanggal)
        btnTambah = findViewById(R.id.btnTambah)

        // Inisialisasi Sidebar
        menuCatatan = findViewById(R.id.menuCatatan)
        menuStory = findViewById(R.id.menuStory)
        menuPengaturan = findViewById(R.id.menuPengaturan)

        rvTanggal.layoutManager = LinearLayoutManager(this)

        loadData()

        // Tombol Tambah Catatan
        btnTambah.setOnClickListener {
            val tanggalSekarang = SimpleDateFormat("dd MMMM yyyy", Locale("id", "ID")).format(Date())
            val intent = Intent(this, DetailJurnalActivity::class.java)
            intent.putExtra("tanggal", tanggalSekarang)
            startActivity(intent)
        }

        // Sidebar Menu 1: Catatan Harian (aktif)
        menuCatatan.setOnClickListener {
            // Sudah di halaman ini
        }

        // Sidebar Menu 2: StoryTelling Time - LANGSUNG KE STORY TELLING
        menuStory.setOnClickListener {
            startActivity(Intent(this, StoryTellingActivity::class.java))
            finish()
        }

        // Sidebar Menu 3: Pengaturan
        menuPengaturan.setOnClickListener {
            startActivity(Intent(this, PengaturanActivity::class.java))
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    private fun loadData() {
        val tanggalList = dbHelper.getAllTanggal()
        tanggalAdapter = TanggalAdapter(tanggalList) { tanggal ->
            val intent = Intent(this, DetailJurnalActivity::class.java)
            intent.putExtra("tanggal", tanggal)
            startActivity(intent)
        }
        rvTanggal.adapter = tanggalAdapter
    }

    // ============= TANGGAL ADAPTER =============
    inner class TanggalAdapter(
        private val tanggalList: List<TanggalModel>,
        private val onClick: (String) -> Unit
    ) : RecyclerView.Adapter<TanggalAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_tanggal, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = tanggalList[position]
            holder.bind(item, onClick)
        }

        override fun getItemCount() = tanggalList.size

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val tvTanggal: TextView = itemView.findViewById(R.id.tvTanggal)
            private val tvJumlahCatatan: TextView = itemView.findViewById(R.id.tvJumlahCatatan)

            fun bind(item: TanggalModel, onClick: (String) -> Unit) {
                tvTanggal.text = item.tanggal
                tvJumlahCatatan.text = "${item.jumlahCatatan} catatan"
                itemView.setOnClickListener { onClick(item.tanggal) }
            }
        }
    }
}