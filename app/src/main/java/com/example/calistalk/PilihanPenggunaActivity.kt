package com.example.calistalk

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class PilihanPenggunaActivity : AppCompatActivity() {

    private lateinit var cardAnak: LinearLayout
    private lateinit var cardOrangTua: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pilihan_pengguna)

        cardAnak = findViewById(R.id.cardAnak)
        cardOrangTua = findViewById(R.id.cardOrangTua)

        // Klik card anak
        cardAnak.setOnClickListener {
            Toast.makeText(this, "Mode Anak Aktif", Toast.LENGTH_SHORT).show()
            // Lanjut ke halaman anak
            // startActivity(Intent(this, AnakDashboardActivity::class.java))
        }

        // Klik card orang tua - BUKA VERIFIKASI
        cardOrangTua.setOnClickListener {
            val intent = Intent(this, VerifikasiOrangTuaActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_VERIFIKASI)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_VERIFIKASI && resultCode == RESULT_OK) {
            val status = data?.getBooleanExtra("verifikasi_status", false) ?: false
            if (status) {
                Toast.makeText(this, "Verifikasi Berhasil! Mode Orang Tua Aktif", Toast.LENGTH_LONG).show()
                // Lanjut ke halaman orang tua
                // startActivity(Intent(this, OrangTuaDashboardActivity::class.java))
            }
        }
    }

    companion object {
        const val REQUEST_CODE_VERIFIKASI = 1001
    }
}