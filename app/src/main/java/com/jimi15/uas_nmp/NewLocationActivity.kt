package com.jimi15.uas_nmp

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.jimi15.uas_nmp.databinding.ActivityNewLocationBinding
import org.json.JSONObject

class NewLocationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewLocationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val items = arrayOf(
            "Cafe", "Resto", "Wisata Alam", "Wisata Sejarah", "Wisata Budaya",
            "Taman Hiburan", "Hotel", "Arcade", "Bar"
        )
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)
        binding.spinnerCategory.adapter = adapter

        binding.btnAdd.setOnClickListener {
            val name = binding.txtName.text.toString()
            val category = binding.spinnerCategory.selectedItem.toString()
            val url = binding.txtUrl.text.toString()
            val shortDesc = binding.txtShort.text.toString()
            val fullDesc = binding.txtFull.text.toString()
            val address = binding.txtAddress.text.toString()
            val hour = binding.txtHour.text.toString()

            if (name.isEmpty() || url.isEmpty() || address.isEmpty()) {
                Toast.makeText(this, "Nama, URL, dan Alamat tidak boleh kosong.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            val newLocation = Location(
                id_location = 0,
                name = name,
                image_url = url,
                short_description = shortDesc,
                category = category,
                full_description = fullDesc,
                address = address,
                operating_hours = hour
            )
            addLocation(newLocation)
        }
    }

    private fun addLocation(location: Location) {
        val q = Volley.newRequestQueue(this)
        val url = "http://10.0.2.2/api/add_location.php"
        val stringRequest = object : StringRequest(
            Request.Method.POST, url,
            { response ->
                Log.d("API_RESPONSE", response)
                try {
                    val obj = JSONObject(response)
                    if (obj.getString("status") == "success") {
                        Toast.makeText(this, "Lokasi berhasil ditambahkan!",
                            Toast.LENGTH_SHORT).show()
                        setResult(Activity.RESULT_OK)
                        finish()
                    } else {
                        Toast.makeText(this, "Error: ${obj.getString("message")}",
                            Toast.LENGTH_SHORT).show()
                        Log.e("API_ERROR", obj.getString("message"))
                    }
                } catch (e: Exception) {
                    Toast.makeText(this, "Format respons server salah.",
                        Toast.LENGTH_SHORT).show()
                    Log.e("API_ERROR", "Error parsing JSON: ${e.message}")
                }
            },
            { error ->
                Toast.makeText(this, "Koneksi ke server gagal.",
                    Toast.LENGTH_SHORT).show()
                Log.e("API_ERROR", error.toString())
            }) {

            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["name"] = location.name
                params["image_url"] = location.image_url
                params["short_description"] = location.short_description
                params["category"] = location.category
                params["full_description"] = location.full_description
                params["address"] = location.address
                params["operating_hours"] = location.operating_hours
                Log.d("AddLocationParams", "Sending params: $params")
                return params
            }
        }
        q.add(stringRequest)
    }
}