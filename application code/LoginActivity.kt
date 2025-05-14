package com.BoxOffice.androidlab

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

class LoginActivity : AppCompatActivity() {

    lateinit var btnLogin: ImageButton
    lateinit var editTextSeat: EditText
    lateinit var editTextPassword: EditText
    lateinit var btnRegister: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnLogin = findViewById(R.id.btnLogin)
        editTextSeat = findViewById(R.id.editTextSeat)
        editTextPassword = findViewById(R.id.editTextPassword)
        btnRegister = findViewById(R.id.btnRegister)

        // 로그인 버튼 클릭
        btnLogin.setOnClickListener {

            val seat = editTextSeat.text.toString()
            val password = editTextPassword.text.toString()

            Log.d("LoginButton", "Clicked! seat=$seat, pw=$password")

            if (seat.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "자리번호와 비밀번호를 모두 입력해주세요.", Toast.LENGTH_SHORT).show()
            } else {
                val responseListener = Response.Listener<String?> { response ->
                    try {
                        val json = org.json.JSONObject(response)
                        val success = json.getBoolean("success")

                        if (success) {
                            val seat = json.getString("seat")
                            val name = json.getString("name")
                            val phone = json.getString("phone")

                            Toast.makeText(this, "로그인 되었습니다.", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, HomeActivity::class.java)
                            intent.putExtra("seat", seat)
                            intent.putExtra("name", name)
                            intent.putExtra("phone", phone)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this, "자리번호와 비밀번호를 확인해 주세요.", Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        Toast.makeText(this, "서버 응답 오류", Toast.LENGTH_SHORT).show()
                    }
                }

                val loginRequest = LoginRequest(seat, password, responseListener)
                val queue: RequestQueue = Volley.newRequestQueue(this)
                queue.add(loginRequest)
            }
        }

        // 회원가입 버튼 클릭 시
        btnRegister.setOnClickListener {

            Log.d("RegisterButton", "Clicked!")

            val loginIntent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(loginIntent)
        }
    }
}