

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) :
    SQLiteOpenHelper(context, "Login.db", null, 1) {
    // users 테이블 생성
    override fun onCreate(MyDB: SQLiteDatabase?) {
        MyDB!!.execSQL("create Table users(seat TEXT primary key, password TEXT, name TEXT, phone TEXT)")
    }

    // 정보 갱신
    override fun onUpgrade(MyDB: SQLiteDatabase?, i: Int, i1: Int) {
        MyDB!!.execSQL("drop Table if exists users")
    }

    // seat, password, name, phone 삽입 (성공시 true, 실패시 false)
    fun insertData (seat: String?, password: String?, name: String?, phone: String?): Boolean {
        val MyDB = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("seat", seat)
        contentValues.put("password", password)
        contentValues.put("name", name)
        contentValues.put("phone", phone)
        val result = MyDB.insert("users", null, contentValues)
        MyDB.close()
        return if (result == -1L) false else true
    }

    // 사용자 자리번호가 없으면 false, 이미 존재하면 true
    fun checkSeat(seat: String?): Boolean {
        val MyDB = this.readableDatabase
        var res = true
        val cursor = MyDB.rawQuery("Select * from users where seat =?", arrayOf(seat))
        if (cursor.count <= 0) res = false
        return res
    }

    // 사용자 이름이 없으면 false, 이미 존재하면 true
    fun checkName(name: String?): Boolean {
        val MyDB = this.readableDatabase
        var res = true
        val cursor = MyDB.rawQuery("Select * from users where name =?", arrayOf(name))
        if (cursor.count <= 0) res = false
        return res
    }

    // 해당 seat, password가 있는지 확인 (없다면 false)
    fun checkSeatpass(seat: String, password: String) : Boolean {
        val MyDB = this.writableDatabase
        var res = true
        val cursor = MyDB.rawQuery(
            "Select * from users where seat = ? and password = ?",
            arrayOf(seat, password)
        )
        if (cursor.count <= 0) res = false
        return res
    }

    // DB name을 Login.db로 설정
    companion object {
        const val DBNAME = "Login.db"
    }
}