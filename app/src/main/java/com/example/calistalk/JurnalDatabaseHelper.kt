package com.example.calistalk

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.text.SimpleDateFormat
import java.util.*

class JurnalDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "jurnal_ortu.db"
        private const val DATABASE_VERSION = 2  // Update version
        private const val TABLE_NAME = "jurnal"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TANGGAL = "tanggal"
        private const val COLUMN_CATATAN = "catatan"
        private const val COLUMN_TIMESTAMP = "timestamp"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_TANGGAL TEXT NOT NULL,
                $COLUMN_CATATAN TEXT NOT NULL,
                $COLUMN_TIMESTAMP INTEGER DEFAULT 0
            )
        """.trimIndent()
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    // Tambah catatan baru - dengan timestamp REAL TIME
    fun tambahCatatan(tanggal: String, catatan: String): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TANGGAL, tanggal)
            put(COLUMN_CATATAN, catatan)
            // Gunakan waktu sekarang dalam milidetik
            put(COLUMN_TIMESTAMP, System.currentTimeMillis())
        }
        return db.insert(TABLE_NAME, null, values)
    }

    // Update catatan - update timestamp juga
    fun updateCatatan(id: Int, catatan: String): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_CATATAN, catatan)
            // Update timestamp menjadi waktu sekarang
            put(COLUMN_TIMESTAMP, System.currentTimeMillis())
        }
        return db.update(TABLE_NAME, values, "$COLUMN_ID = ?", arrayOf(id.toString()))
    }

    // Hapus catatan
    fun hapusCatatan(id: Int): Int {
        val db = writableDatabase
        return db.delete(TABLE_NAME, "$COLUMN_ID = ?", arrayOf(id.toString()))
    }

    // Ambil semua catatan berdasarkan tanggal
    fun getCatatanByTanggal(tanggal: String): List<JurnalModel> {
        val catatanList = mutableListOf<JurnalModel>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_TANGGAL = ? ORDER BY $COLUMN_TIMESTAMP DESC"
        val cursor = db.rawQuery(query, arrayOf(tanggal))

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val catatan = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATATAN))
            val timestamp = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_TIMESTAMP))
            catatanList.add(JurnalModel(id, tanggal, catatan, timestamp))
        }
        cursor.close()
        return catatanList
    }

    // Ambil semua tanggal unik yang memiliki catatan
    fun getAllTanggal(): List<TanggalModel> {
        val tanggalList = mutableListOf<TanggalModel>()
        val db = readableDatabase
        val query = """
            SELECT $COLUMN_TANGGAL, COUNT(*) as jumlah_catatan 
            FROM $TABLE_NAME 
            GROUP BY $COLUMN_TANGGAL 
            ORDER BY $COLUMN_TIMESTAMP DESC
        """
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()) {
            val tanggal = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TANGGAL))
            val jumlah = cursor.getInt(cursor.getColumnIndexOrThrow("jumlah_catatan"))
            tanggalList.add(TanggalModel(tanggal, jumlah))
        }
        cursor.close()
        return tanggalList
    }
}

data class JurnalModel(
    val id: Int,
    val tanggal: String,
    val catatan: String,
    val timestamp: Long  // ini dalam milidetik
)

data class TanggalModel(
    val tanggal: String,
    val jumlahCatatan: Int
)