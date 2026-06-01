package com.example.calistalk

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.EmailAuthProvider
import java.util.*

class PengaturanActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var sharedPref: SharedPreferences

    // Sidebar menus
    private lateinit var menuCatatan: LinearLayout
    private lateinit var menuStory: LinearLayout
    private lateinit var menuPengaturan: LinearLayout

    // Views
    private lateinit var tvUsername: TextView
    private lateinit var tvEmail: TextView
    private lateinit var tvPassword: TextView
    private lateinit var etNamaOrtu: EditText
    private lateinit var etNamaAnak: EditText
    private lateinit var etUmurAnak: EditText
    private lateinit var etTglLahir: EditText
    private lateinit var btnPilihTanggal: Button
    private lateinit var btnGantiPassword: Button
    private lateinit var btnSimpan: Button
    private lateinit var btnLogout: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pengaturan)

        auth = FirebaseAuth.getInstance()
        sharedPref = getSharedPreferences("calistalk_pref", Context.MODE_PRIVATE)

        initViews()
        setupSidebar()
        loadUserData()
        loadProfilData()
        setupClickListeners()
    }

    private fun initViews() {
        menuCatatan = findViewById(R.id.menuCatatan)
        menuStory = findViewById(R.id.menuStory)
        menuPengaturan = findViewById(R.id.menuPengaturan)

        tvUsername = findViewById(R.id.tvUsername)
        tvEmail = findViewById(R.id.tvEmail)
        tvPassword = findViewById(R.id.tvPassword)
        etNamaOrtu = findViewById(R.id.etNamaOrtu)
        etNamaAnak = findViewById(R.id.etNamaAnak)
        etUmurAnak = findViewById(R.id.etUmurAnak)
        etTglLahir = findViewById(R.id.etTglLahir)
        btnPilihTanggal = findViewById(R.id.btnPilihTanggal)
        btnGantiPassword = findViewById(R.id.btnGantiPassword)
        btnSimpan = findViewById(R.id.btnSimpan)
        btnLogout = findViewById(R.id.btnLogout)
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

        // menuPengaturan sudah aktif (halaman ini)
    }

    private fun loadUserData() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val email = currentUser.email ?: "-"
            val username = email.substringBefore("@")
            tvUsername.text = username
            tvEmail.text = email
        }
    }

    private fun loadProfilData() {
        etNamaOrtu.setText(sharedPref.getString("nama_ortu", ""))
        etNamaAnak.setText(sharedPref.getString("nama_anak", ""))
        etUmurAnak.setText(sharedPref.getString("umur_anak", ""))
        etTglLahir.setText(sharedPref.getString("tgl_lahir_anak", ""))
    }

    private fun setupClickListeners() {
        btnPilihTanggal.setOnClickListener {
            showDatePicker()
        }

        btnGantiPassword.setOnClickListener {
            showChangePasswordDialog()
        }

        btnSimpan.setOnClickListener {
            saveProfilData()
        }

        btnLogout.setOnClickListener {
            logout()
        }
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            val tanggal = "$selectedDay/${selectedMonth + 1}/$selectedYear"
            etTglLahir.setText(tanggal)
        }, year, month, day)

        datePickerDialog.show()
    }

    private fun showChangePasswordDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_ganti_password, null)
        val etPasswordLama = dialogView.findViewById<EditText>(R.id.etPasswordLama)
        val etPasswordBaru = dialogView.findViewById<EditText>(R.id.etPasswordBaru)
        val etKonfirmasiPassword = dialogView.findViewById<EditText>(R.id.etKonfirmasiPassword)

        AlertDialog.Builder(this)
            .setTitle("Ganti Password")
            .setView(dialogView)
            .setPositiveButton("Simpan") { _, _ ->
                val passwordLama = etPasswordLama.text.toString()
                val passwordBaru = etPasswordBaru.text.toString()
                val konfirmasi = etKonfirmasiPassword.text.toString()

                if (passwordLama.isEmpty() || passwordBaru.isEmpty() || konfirmasi.isEmpty()) {
                    Toast.makeText(this, "Semua field harus diisi", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                if (passwordBaru != konfirmasi) {
                    Toast.makeText(this, "Password baru tidak cocok", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                if (passwordBaru.length < 6) {
                    Toast.makeText(this, "Password minimal 6 karakter", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                changePassword(passwordLama, passwordBaru)
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    private fun changePassword(oldPassword: String, newPassword: String) {
        val currentUser = auth.currentUser ?: return

        val credential = EmailAuthProvider.getCredential(currentUser.email!!, oldPassword)
        currentUser.reauthenticate(credential)
            .addOnCompleteListener { reauthTask ->
                if (reauthTask.isSuccessful) {
                    currentUser.updatePassword(newPassword)
                        .addOnCompleteListener { updateTask ->
                            if (updateTask.isSuccessful) {
                                Toast.makeText(this, "Password berhasil diubah", Toast.LENGTH_LONG).show()
                            } else {
                                Toast.makeText(this, "Gagal mengubah password: ${updateTask.exception?.message}", Toast.LENGTH_LONG).show()
                            }
                        }
                } else {
                    Toast.makeText(this, "Password lama salah", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun saveProfilData() {
        val editor = sharedPref.edit()
        editor.putString("nama_ortu", etNamaOrtu.text.toString())
        editor.putString("nama_anak", etNamaAnak.text.toString())
        editor.putString("umur_anak", etUmurAnak.text.toString())
        editor.putString("tgl_lahir_anak", etTglLahir.text.toString())
        editor.apply()

        Toast.makeText(this, "Data profil berhasil disimpan", Toast.LENGTH_SHORT).show()
    }

    private fun logout() {
        AlertDialog.Builder(this)
            .setTitle("Logout")
            .setMessage("Apakah Anda yakin ingin logout?")
            .setPositiveButton("Ya") { _, _ ->
                auth.signOut()

                // Hapus semua data shared preferences
                sharedPref.edit().clear().apply()

                startActivity(Intent(this, LoginActivity::class.java))
                finishAffinity()
            }
            .setNegativeButton("Tidak", null)
            .show()
    }
}