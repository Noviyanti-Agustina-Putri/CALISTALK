package com.example.calistalk

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat

class MiniGameBicaraActivity : AppCompatActivity() {

    // --- 1. Deklarasi View ---
    private lateinit var btnBack: ImageView

    // List Card Menu
    private lateinit var card1: CardView
    private lateinit var card2: CardView
    private lateinit var card3: CardView

    // View Header
    private lateinit var headerTitle: TextView
    private lateinit var headerSubtitle: TextView
    private lateinit var headerIcon: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mini_game)

        initUI()
        setupHeader()
        setupListeners()
    }

    private fun initUI() {
        // Header (Diambil via layout include)
        btnBack = findViewById(R.id.btnBackGame)
        headerTitle = findViewById(R.id.title)
        headerSubtitle = findViewById(R.id.subtitle)
        headerIcon = findViewById(R.id.headerIcon)

        // Menu Cards
        card1 = findViewById(R.id.card1)
        card2 = findViewById(R.id.card2)
        card3 = findViewById(R.id.card3)
    }

    private fun setupHeader() {
        // Mengubah warna dan teks sesuai screenshot
        headerTitle.text = "Mini Game Bicara"
        headerSubtitle.text = "Main game untuk melatih kemampuan bicara"
        headerIcon.setImageResource(R.drawable.ic_game)

        val colorGreen = ContextCompat.getColor(this, android.R.color.holo_green_dark)

        headerTitle.setTextColor(colorGreen)
        headerSubtitle.setTextColor(colorGreen)
    }

    private fun setupListeners() {
        btnBack.setOnClickListener { finish() }

        // Pair (Card -> Activity)
        val menuList = listOf(
            Pair(card1, TebakGambarActivity::class.java),
            Pair(card2, CocokkanSuaraActivity::class.java),
            Pair(card3, PilihBendaActivity::class.java)
        )

        // Terapkan listener ke masing-masing card secara langsung
        menuList.forEach { (card, activityClass) ->
            card.setOnClickListener {
                // Efek visual
                card.animate().scaleX(0.95f).scaleY(0.95f).setDuration(100).withEndAction {
                    card.animate().scaleX(1f).scaleY(1f).setDuration(100).start()

                    // Pindah Activity
                    val intent = Intent(this, activityClass)
                    startActivity(intent)
                }.start()
            }
        }
    }
}