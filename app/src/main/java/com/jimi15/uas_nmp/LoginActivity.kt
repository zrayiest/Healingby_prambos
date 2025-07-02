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
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.jimi15.uas_nmp.databinding.ActivityLoginBinding
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    var users:ArrayList<Users> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreferences = getSharedPreferences("USER_SESSION", MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean("logged_in", false)
        if (isLoggedIn) {
            // Jika sudah login, langsung arahkan ke MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
            return // Hentikan eksekusi sisa onCreate() di LoginActivity
        }

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSignup.setOnClickListener {
           val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.txtEmail.text.toString()
            val password = binding.txtPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Email dan password tidak boleh kosong", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val q = Volley.newRequestQueue(this)
            val url = "http://10.0.2.2/api/login.php"
            val stringRequest = object : StringRequest(
                Request.Method.POST,
                url,
                { response ->
                    Log.d("API_RESPONSE", response)
                    try {
                        val obj = JSONObject(response)
                        if (obj.getString("status") == "success") {
                            Toast.makeText(this, obj.getString("message"),
                                Toast.LENGTH_SHORT).show()

                            val userData = obj.getJSONObject("data")
                            val id_user = userData.getString("id_user")
                            val email = userData.getString("email")
                            val name = userData.getString("name")

                            val sharedPreferences = getSharedPreferences("USER_SESSION", MODE_PRIVATE)
                            val editor = sharedPreferences.edit()
                            editor.putString("id_user", id_user)
                            editor.putString("email", email)
                            editor.putString("name", name)
                            editor.putBoolean("logged_in", true)
                            editor.apply()

                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this, obj.getString("message"), Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        Log.e("JSON_ERROR", "Error parsing JSON: ${e.message}")
                        Toast.makeText(this, "Terjadi kesalahan pada format data", Toast.LENGTH_LONG).show()
                    }
                },
                { error ->
                    Log.e("API_ERROR", error.toString())
                    Toast.makeText(this, "Tidak dapat terhubung ke server: ${error.message}",
                        Toast.LENGTH_LONG).show()
                }
            ) {
                override fun getParams(): MutableMap<String, String> {
                    val params = HashMap<String, String>()
                    params["email"] = email
                    params["password"] = password
                    return params
                }
            }
            q.add(stringRequest)
        }
    }
}