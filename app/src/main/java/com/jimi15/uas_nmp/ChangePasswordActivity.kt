package com.jimi15.uas_nmp

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.jimi15.uas_nmp.databinding.ActivityChangePasswordBinding
import org.json.JSONObject

class ChangePasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChangePasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnChange.setOnClickListener {
            val oldPassword = binding.txtOld.text.toString()
            val newPassword = binding.txtNew.text.toString()
            val repeatPassword = binding.txtRepeat.text.toString()

            if (oldPassword.isEmpty() || newPassword.isEmpty() || repeatPassword.isEmpty()) {
                Toast.makeText(this, "Semua field harus diisi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (newPassword != repeatPassword) {
                binding.txtRepeat.error = "Password tidak cocok"
                return@setOnClickListener
            }

            val sharedPreferences = getSharedPreferences("USER_SESSION", Context.MODE_PRIVATE)
            val idUser = sharedPreferences.getString("id_user", null)

            if (idUser == null) {
                Toast.makeText(this, "Error: User tidak ditemukan. Silakan login ulang."
                    , Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            changePasswordOnServer(idUser, oldPassword, newPassword, repeatPassword)
        }
    }

    private fun changePasswordOnServer(idUser: String, oldPass: String, newPass: String, repeatPass: String) {
        val q = Volley.newRequestQueue(this)
        val url = "http://10.0.2.2/api/change_password.php"

        val request = object : StringRequest(
            Request.Method.POST, url,
            { response ->
                try {
                    val obj = JSONObject(response)
                    if (obj.getString("status") == "success") {

                        Toast.makeText(this, obj.getString("message"), Toast.LENGTH_LONG).show()
                        finish()
                    } else {

                        Toast.makeText(this, obj.getString("message"), Toast.LENGTH_LONG).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(this, "Format respons server salah.", Toast.LENGTH_SHORT).show()
                    Log.e("ChangePassError", "JSON Parsing Error: ${e.message}")
                }
            },
            { error ->
                Toast.makeText(this, "Koneksi ke server gagal.", Toast.LENGTH_SHORT).show()
                Log.e("ChangePassError", "Volley Error: ${error.toString()}")
            }) {
            override fun getParams(): MutableMap<String, String> {
                return hashMapOf(
                    "id_user" to idUser,
                    "old_password" to oldPass,
                    "new_password" to newPass,
                    "repeat_password" to repeatPass
                )
            }
        }
        q.add(request)
    }
}