package com.example.calistalk

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Setelah login sukses, langsung ke halaman pilihan pengguna
        startActivity(Intent(this, PilihanPenggunaActivity::class.java))
        finish()
    }
}