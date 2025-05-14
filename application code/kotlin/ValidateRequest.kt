package com.BoxOffice.androidlab

import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest

class ValidateRequest(
    seat: String,
    listener: Response.Listener<String?>?,
    error: Response.ErrorListener) : StringRequest(Method.POST, URL, listener, error) {
    private val map: MutableMap<String, String>

    @Throws(AuthFailureError::class)
    override fun getParams(): Map<String, String>? {
        return map
    }

    companion object {
        //apk 배포 시
        //private const val URL = "https://845e-210-125-112-206.ngrok-free.app/validate.php"

        //에뮬레이터 실행 시
        private const val URL = "http://10.0.2.2/validate.php"
    }

    init {
        map = HashMap()
        map["seat"] = seat
    }
}