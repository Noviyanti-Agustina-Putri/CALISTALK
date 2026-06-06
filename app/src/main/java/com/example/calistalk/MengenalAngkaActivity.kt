package com.example.calistalk

import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MengenalAngkaActivity : AppCompatActivity() {

    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val level = intent.getStringExtra("LEVEL")

        when (level) {
            "DASAR" -> setContentView(R.layout.mengenal_angka_dasar)
            "LANJUTAN" -> setContentView(R.layout.mengenal_angka_lanjut)
            "SULIT" -> setContentView(R.layout.mengenal_angka_sulit)
            else -> setContentView(R.layout.mengenal_angka_dasar)
        }

        val angkaMap = when (level) {

            "DASAR" -> mapOf(
                R.id.card1 to R.raw.angka_1,
                R.id.card2 to R.raw.angka_2,
                R.id.card3 to R.raw.angka_3,
                R.id.card4 to R.raw.angka_4,
                R.id.card5 to R.raw.angka_5,
                R.id.card6 to R.raw.angka_6,
                R.id.card7 to R.raw.angka_7,
                R.id.card8 to R.raw.angka_8,
                R.id.card9 to R.raw.angka_9,
                R.id.card10 to R.raw.angka_10
            )

            "LANJUTAN" -> mapOf(
                R.id.card11 to R.raw.angka_11,
                R.id.card12 to R.raw.angka_12,
                R.id.card13 to R.raw.angka_13,
                R.id.card14 to R.raw.angka_14,
                R.id.card15 to R.raw.angka_15,
                R.id.card16 to R.raw.angka_16,
                R.id.card17 to R.raw.angka_17,
                R.id.card18 to R.raw.angka_18,
                R.id.card19 to R.raw.angka_19,
                R.id.card20 to R.raw.angka_20
            )

            "SULIT" -> mapOf(
                R.id.card30 to R.raw.angka_30,
                R.id.card34 to R.raw.angka_34,
                R.id.card48 to R.raw.angka_48,
                R.id.card50 to R.raw.angka_50,
                R.id.card69 to R.raw.angka_69,
                R.id.card70 to R.raw.angka_70,
                R.id.card80 to R.raw.angka_80,
                R.id.card90 to R.raw.angka_90,
                R.id.card91 to R.raw.angka_91,
                R.id.card100 to R.raw.angka_100
            )

            else -> emptyMap()
        }

        angkaMap.forEach { (cardId, soundId) ->
            findViewById<View>(cardId)?.setOnClickListener {
                playSound(soundId)
            }
        }
    }

    private fun playSound(soundId: Int) {
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer.create(this, soundId)
        mediaPlayer?.start()
    }

    override fun onDestroy() {
        mediaPlayer?.release()
        super.onDestroy()
    }
}