package com.jimi15.uas_nmp

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.jimi15.uas_nmp.databinding.ActivityHealingDetailsBinding
import com.squareup.picasso.Picasso
import org.json.JSONException
import org.json.JSONObject

class HealingDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHealingDetailsBinding

    private var isFavorite = false
    private var idLocation: Int = -1
    private var idUser: Int = -1
    private var comesFromFavoritesPage = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHealingDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupInitialData()
        checkInitialFavoriteStatus()

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnFav.setOnClickListener {
            updateFavoriteStatus(isAdding = !isFavorite)
        }
    }

    private fun setupInitialData() {
        idLocation = intent.getIntExtra("id_location", -1)
        comesFromFavoritesPage = intent.getBooleanExtra("FROM_FAVORITES", false)
        val sharedPreferences = getSharedPreferences("USER_SESSION", Context.MODE_PRIVATE)
        idUser = sharedPreferences.getString("id_user", "-1")?.toInt() ?: -1

        binding.txtName.text = intent.getStringExtra("name")
        binding.txtCategory.text = intent.getStringExtra("category")
        binding.txtShort.text = intent.getStringExtra("short_description")
        binding.txtaddress.text = intent.getStringExtra("address")
        binding.txtLong.text = intent.getStringExtra("full_description")

        binding.txtHour.text = intent.getStringExtra("operating_hours")
        Picasso.get().load(intent.getStringExtra("image_url")).into(binding.imgPhoto)
    }

    private fun checkInitialFavoriteStatus() {
        binding.btnFav.isEnabled = false
        binding.btnFav.text = "Loading..."

        val checkUrl = "http://10.0.2.2/api/check_favorite.php?id_user=$idUser&id_location=$idLocation"
        val request = StringRequest(
            Request.Method.GET,
            checkUrl,
            { response ->
                try {
                    val jsonObj = JSONObject(response)
                    isFavorite = jsonObj.getBoolean("is_favorite")
                    binding.btnFav.text = if (isFavorite) "Remove from Favourite"
                    else "Add to Favourite"
                } catch (e: JSONException) {
                    binding.btnFav.text = "Error Status"
                    Log.e("CheckFavorite", "JSON Parsing Error: ${e.message}")
                } finally {
                    binding.btnFav.isEnabled = true
                }
            },
            { error ->
                binding.btnFav.text = "Gagal Memuat"
                binding.btnFav.isEnabled = true
                Log.e("CheckFavorite", "Volley Error: ${error.toString()}")
            }
        )
        Volley.newRequestQueue(this).add(request)
    }

    private fun updateFavoriteStatus(isAdding: Boolean) {
        val url = if (isAdding) {
            "http://10.0.2.2/api/add_favorite.php"
        } else {
            "http://10.0.2.2/api/remove_favorite.php"
        }

        val request = object : StringRequest(
            Request.Method.POST,
            url,
            { response ->
                try {
                    val jsonObj = JSONObject(response)
                    if (jsonObj.getString("status") == "success") {
                        // Jika request berhasil, update status dan UI
                        isFavorite = isAdding
                        val message = if (isAdding) {
                            binding.btnFav.text = "Remove from Favourite"
                            "Ditambahkan ke favorit"
                        } else {
                            binding.btnFav.text = "Add to Favourite"
                            if (comesFromFavoritesPage) {
                                finish()
                            }
                            "Dihapus dari favorit"
                        }
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, jsonObj.getString("message"),
                            Toast.LENGTH_SHORT).show()
                    }
                } catch (e: JSONException) {
                    Toast.makeText(this, "Format respons server salah.", Toast.LENGTH_SHORT).show()
                    Log.e("UpdateFavorite", "JSON Parsing Error: ${e.message}")
                }
            },
            { error ->
                Toast.makeText(this, "Koneksi ke server gagal.", Toast.LENGTH_SHORT).show()
                Log.e("UpdateFavorite", "Volley Error: ${error.toString()}")
            }) {
            override fun getParams(): MutableMap<String, String> {
                return hashMapOf(
                    "id_user" to idUser.toString(),
                    "id_location" to idLocation.toString())
            }
        }
        Volley.newRequestQueue(this).add(request)
    }
}