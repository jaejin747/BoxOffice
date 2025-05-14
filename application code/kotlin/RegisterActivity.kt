package com.BoxOffice.androidlab

import DBHelper
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.Volley

class RegisterActivity : AppCompatActivity() {

    var DB: DBHelper? = null
    lateinit var editTextSeat: EditText
    lateinit var editTextPassword: EditText
    lateinit var editTextRePassword: EditText
    lateinit var editTextName: EditText
    lateinit var editTextPhone: EditText
    lateinit var btnRegister: ImageButton
    lateinit var btnCheckSeat: Button
    var CheckSeat: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        DB = DBHelper(this)

        editTextSeat = findViewById(R.id.editTextSeat_Reg)
        editTextPassword = findViewById(R.id.editTextPass_Reg)
        editTextRePassword = findViewById(R.id.editTextRePass_Reg)
        editTextName = findViewById(R.id.editTextName_Reg)
        editTextPhone = findViewById(R.id.editTextPhone_Reg)
        btnRegister = findViewById(R.id.btnRegister_Reg)
        btnCheckSeat = findViewById(R.id.btnCheckSeat_Reg)

        // 자리번호 중복확인
        btnCheckSeat.setOnClickListener {
            val seat = editTextSeat.text.toString()
            val seatPattern = "^[A-Z]{1,2}[0-9]$".toRegex()

            Log.d("ValidateButton", "Clicked!")

            if (seat.isEmpty()) {
                Toast.makeText(this, "자리번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
            } else if (!seatPattern.matches(seat)) {
                Toast.makeText(this, "자리번호 형식이 옳지 않습니다.", Toast.LENGTH_SHORT).show()
            } else {
                val check = DB!!.checkSeat(seat)
                if (!check) {
                    CheckSeat = true
                    Toast.makeText(this, "사용 가능한 자리번호입니다.", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "이미 존재하는 자리번호입니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // 회원가입 완료 버튼 클릭
        btnRegister.setOnClickListener {

            Log.d("RegisterButton", "Clicked!")

            val seat = editTextSeat.text.toString()
            val pass = editTextPassword.text.toString()
            val repass = editTextRePassword.text.toString()
            val name = editTextName.text.toString()
            val phone = editTextPhone.text.toString()

            val pwPattern = "^[0-9]{4}$".toRegex()
            val namePattern = "^[ㄱ-ㅣ가-힣]*$".toRegex()
            val phonePattern = "^(\\+[0-9]+)?[0-9]{10,15}$".toRegex()

            // 빈 입력 검사
            if (seat.isEmpty() || pass.isEmpty() || repass.isEmpty() || name.isEmpty() || phone.isEmpty()) {
                Toast.makeText(this, "회원정보를 모두 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // 자리번호 중복 검사
            if (!CheckSeat) {
                Toast.makeText(this, "자리번호 중복확인을 해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // 비밀번호 형식 검사
            if (!pwPattern.matches(pass)) {
                Toast.makeText(this, "비밀번호 형식이 옳지 않습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // 비밀번호 일치 검사
            if (pass != repass) {
                Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // 이름 형식 검사
            if (!namePattern.matches(name)) {
                Toast.makeText(this, "이름 형식이 옳지 않습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // 전화번호 형식 검사
            if (!phonePattern.matches(phone)) {
                Toast.makeText(this, "전화번호 형식이 옳지 않습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 서버에 회원가입 요청 보내기
            val responseListener = Response.Listener<String?> { response ->
                try {
                    val json = org.json.JSONObject(response)
                    val success = json.getBoolean("success")

                    if (success) {
                        Toast.makeText(this, "가입되었습니다.", Toast.LENGTH_SHORT).show()
                        val intent = Intent(applicationContext, LoginActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "가입 실패하였습니다.", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(this, "서버 응답 오류", Toast.LENGTH_SHORT).show()
                }
            }

            val registerRequest = RegisterRequest(seat, pass, name, phone, responseListener)
            val queue: RequestQueue = Volley.newRequestQueue(this)
            queue.add(registerRequest)
        }
    }
}
