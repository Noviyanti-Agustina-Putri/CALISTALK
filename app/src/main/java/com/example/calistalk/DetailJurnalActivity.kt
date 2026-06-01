package com.example.calistalk

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class DetailJurnalActivity : AppCompatActivity() {

    private lateinit var dbHelper: JurnalDatabaseHelper
    private lateinit var tvHeaderTanggal: TextView
    private lateinit var etCatatan: EditText
    private lateinit var btnSimpan: Button
    private lateinit var btnBack: Button
    private lateinit var rvCatatan: RecyclerView

    private lateinit var menuCatatan: LinearLayout
    private lateinit var menuStory: LinearLayout
    private lateinit var menuPengaturan: LinearLayout

    private var currentTanggal: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_jurnal)

        dbHelper = JurnalDatabaseHelper(this)

        tvHeaderTanggal = findViewById(R.id.tvHeaderTanggal)
        etCatatan = findViewById(R.id.etCatatan)
        btnSimpan = findViewById(R.id.btnSimpan)
        btnBack = findViewById(R.id.btnBack)
        rvCatatan = findViewById(R.id.rvCatatan)

        menuCatatan = findViewById(R.id.menuCatatan)
        menuStory = findViewById(R.id.menuStory)
        menuPengaturan = findViewById(R.id.menuPengaturan)

        currentTanggal = intent.getStringExtra("tanggal") ?: ""
        tvHeaderTanggal.text = currentTanggal

        rvCatatan.layoutManager = LinearLayoutManager(this)
        loadCatatan()

        btnBack.setOnClickListener {
            finish()
        }

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

        btnSimpan.setOnClickListener {
            val catatanBaru = etCatatan.text.toString().trim()
            if (catatanBaru.isNotEmpty()) {
                dbHelper.tambahCatatan(currentTanggal, catatanBaru)
                Toast.makeText(this, "Catatan disimpan", Toast.LENGTH_SHORT).show()
                etCatatan.text.clear()
                loadCatatan()
            } else {
                Toast.makeText(this, "Catatan tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadCatatan() {
        val catatanList = dbHelper.getCatatanByTanggal(currentTanggal)
        val adapter = CatatanAdapter(catatanList,
            onEditClick = { catatan ->
                showEditDialog(catatan)
            },
            onDeleteClick = { catatan ->
                showDeleteConfirmation(catatan)
            }
        )
        rvCatatan.adapter = adapter
    }

    private fun showEditDialog(catatan: JurnalModel) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_edit_catatan, null)
        val etEditCatatan = dialogView.findViewById<EditText>(R.id.etEditCatatan)
        etEditCatatan.setText(catatan.catatan)

        AlertDialog.Builder(this)
            .setTitle("Edit Catatan")
            .setView(dialogView)
            .setPositiveButton("Simpan") { _, _ ->
                val catatanBaru = etEditCatatan.text.toString().trim()
                if (catatanBaru.isNotEmpty()) {
                    dbHelper.updateCatatan(catatan.id, catatanBaru)
                    Toast.makeText(this, "Catatan diupdate", Toast.LENGTH_SHORT).show()
                    loadCatatan()
                } else {
                    Toast.makeText(this, "Catatan tidak boleh kosong", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    private fun showDeleteConfirmation(catatan: JurnalModel) {
        AlertDialog.Builder(this)
            .setTitle("Hapus Catatan")
            .setMessage("Apakah Anda yakin ingin menghapus catatan ini?")
            .setPositiveButton("Hapus") { _, _ ->
                dbHelper.hapusCatatan(catatan.id)
                Toast.makeText(this, "Catatan dihapus", Toast.LENGTH_SHORT).show()
                loadCatatan()
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    // ============= INNER CLASS ADAPTER =============
    inner class CatatanAdapter(
        private val catatanList: List<JurnalModel>,
        private val onEditClick: (JurnalModel) -> Unit,
        private val onDeleteClick: (JurnalModel) -> Unit
    ) : RecyclerView.Adapter<CatatanAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_catatan, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = catatanList[position]
            holder.bind(item, onEditClick, onDeleteClick)
        }

        override fun getItemCount(): Int = catatanList.size

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val tvWaktu: TextView = itemView.findViewById(R.id.tvWaktu)
            private val tvCatatan: TextView = itemView.findViewById(R.id.tvCatatan)
            private val btnEdit: Button = itemView.findViewById(R.id.btnEdit)
            private val btnHapus: Button = itemView.findViewById(R.id.btnHapus)

            fun bind(item: JurnalModel, onEditClick: (JurnalModel) -> Unit, onDeleteClick: (JurnalModel) -> Unit) {
                // Perbaikan format timestamp
                val sdf = SimpleDateFormat("dd MMM yyyy, HH:mm:ss", Locale("id", "ID"))
                val formattedDateTime = sdf.format(Date(item.timestamp))

                tvWaktu.text = formattedDateTime
                tvCatatan.text = item.catatan

                btnEdit.setOnClickListener { onEditClick(item) }
                btnHapus.setOnClickListener { onDeleteClick(item) }
            }
        }
    }
}