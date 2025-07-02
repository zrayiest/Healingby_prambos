package com.jimi15.uas_nmp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.jimi15.uas_nmp.databinding.ActivitySignUpBinding
import org.json.JSONObject

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSignUp.setOnClickListener {
            val name = binding.txtName.text.toString()
            val email = binding.txtEmail.text.toString()
            val password = binding.txtPassword.text.toString()
            val repeat = binding.txtRepeat.text.toString()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || repeat.isEmpty()){
                Toast.makeText(this, "Field tidak boleh kosong", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != repeat){
                binding.txtRepeat.error = "Password tidak sama"
                return@setOnClickListener
            }

            val q = Volley.newRequestQueue(this)
            val url = "http://10.0.2.2/api/register.php"
            val stringRequest = object : StringRequest(
                Request.Method.POST,
                url,
                {
                    response ->
                    Log.d("API_RESPONSE", response)
                    try {
                        val obj = JSONObject(response)
                        if (obj.getString("status") == "success"){
                            Toast.makeText(this, obj.getString("message"),
                                Toast.LENGTH_SHORT).show()

                            val intent = Intent(this, LoginActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this, obj.getString("message"),
                                Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        Log.e("JSON_ERROR", "Error parsing JSON: ${e.message}")
                        Toast.makeText(this, "Terjadi kesalahan pada format data",
                            Toast.LENGTH_LONG).show()
                    }
                },
                {
                    error ->
                    Log.e("API_ERROR", error.toString())
                    Toast.makeText(this, "Tidak dapat terhubung ke server: ${error.message}",
                        Toast.LENGTH_LONG).show()
                }
            ) {
                override fun getParams(): MutableMap<String, String> {
                    val params = HashMap<String, String>()
                    params["name"] = name
                    params["email"] = email
                    params["password"] = password
                    return params
                }
            }
            q.add(stringRequest)
        }
    }
}