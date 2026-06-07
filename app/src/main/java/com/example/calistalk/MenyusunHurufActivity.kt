package com.example.calistalk

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MenyusunHurufActivity : AppCompatActivity() {

    private lateinit var txtSoal: TextView
    private lateinit var txtJawaban: TextView

    private lateinit var btn1: Button
    private lateinit var btn2: Button
    private lateinit var btn3: Button
    private lateinit var btn4: Button

    private lateinit var btnCek: Button
    private lateinit var btnAcak: Button
    private lateinit var btnHapus: Button

    private val daftarKata = listOf(
        "APEL",
        "BUKU",
        "MEJA",
        "BOLA",
        "SAPI",
        "IKAN",
        "ROTI"
    )

    private var kataBenar = ""
    private var jawabanUser = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menyusun_huruf)

        txtSoal = findViewById(R.id.txtSoal)
        txtJawaban = findViewById(R.id.txtJawaban)

        btn1 = findViewById(R.id.btn1)
        btn2 = findViewById(R.id.btn2)
        btn3 = findViewById(R.id.btn3)
        btn4 = findViewById(R.id.btn4)
        btnHapus = findViewById(R.id.btnHapus)

        btnHapus.setOnClickListener {

            if (jawabanUser.isNotEmpty()) {

                jawabanUser = jawabanUser.dropLast(1)

                if (jawabanUser.isEmpty()) {
                    txtJawaban.text = "_ _ _ _"
                } else {
                    txtJawaban.text = jawabanUser
                }
            }
        }

        btnCek = findViewById(R.id.btnCek)
        btnAcak = findViewById(R.id.btnAcak)

        mulaiSoalBaru()

        btn1.setOnClickListener { tambahHuruf(btn1.text.toString()) }
        btn2.setOnClickListener { tambahHuruf(btn2.text.toString()) }
        btn3.setOnClickListener { tambahHuruf(btn3.text.toString()) }
        btn4.setOnClickListener { tambahHuruf(btn4.text.toString()) }

        btnCek.setOnClickListener {

            if (jawabanUser == kataBenar) {

                Toast.makeText(
                    this,
                    "Jawaban Benar 🎉",
                    Toast.LENGTH_SHORT
                ).show()

            } else {

                Toast.makeText(
                    this,
                    "Jawaban Salah ❌",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        btnAcak.setOnClickListener {
            mulaiSoalBaru()
        }
    }

    private fun mulaiSoalBaru() {

        kataBenar = daftarKata.random()

        txtSoal.text = "Susun kata $kataBenar"

        jawabanUser = ""
        txtJawaban.text = "_ _ _ _"

        val hurufAcak = kataBenar.toList().shuffled()

        btn1.text = hurufAcak[0].toString()
        btn2.text = hurufAcak[1].toString()
        btn3.text = hurufAcak[2].toString()
        btn4.text = hurufAcak[3].toString()

        btn1.isEnabled = true
        btn2.isEnabled = true
        btn3.isEnabled = true
        btn4.isEnabled = true
    }

    private fun tambahHuruf(huruf: String) {

        if (jawabanUser.length < 4) {

            jawabanUser += huruf

            txtJawaban.text = jawabanUser
        }
    }
}