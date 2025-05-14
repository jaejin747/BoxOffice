package com.BoxOffice.androidlab

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // LoginActivity 또는 RegisterActivity에서 전달된 사용자 정보 받기
        val seat = intent.getStringExtra("seat")
        val name = intent.getStringExtra("name")
        val phone = intent.getStringExtra("phone")

        // 화면에 사용자 정보 출력
        val textSeat = findViewById<TextView>(R.id.textSeat)
        val textName = findViewById<TextView>(R.id.textName)
        val textPhone = findViewById<TextView>(R.id.textPhone)

        textSeat.text = "자리번호: $seat"
        textName.text = "이름: $name"
        textPhone.text = "전화번호: $phone"
    }
}
