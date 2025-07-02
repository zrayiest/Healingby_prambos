package com.jimi15.uas_nmp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jimi15.uas_nmp.databinding.FragmentExploreBinding
import org.json.JSONObject

class ExploreFragment : Fragment() {
    private var _binding: FragmentExploreBinding? = null
    private val binding get() = _binding!!

    private val locations = ArrayList<Location>()
    private lateinit var locationAdapter: LocationAdapter

    private val addLocationLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        result ->
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            Toast.makeText(context, "Memperbarui daftar...", Toast.LENGTH_SHORT).show()
            fetchLocationsFromServer()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExploreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        fetchLocationsFromServer()

        binding.fabAdd.setOnClickListener {
            val intent = Intent(activity, NewLocationActivity::class.java)
            addLocationLauncher.launch(intent)
        }
    }

    private fun setupRecyclerView() {
        locationAdapter = LocationAdapter(locations)
        binding.recLocation.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = locationAdapter
        }
    }

    private fun fetchLocationsFromServer() {
        val q = Volley.newRequestQueue(activity)
        val url = "http://10.0.2.2/api/get_locations.php"

        val stringRequest = StringRequest(Request.Method.GET, url,
            { response ->
                Log.d("API_RESPONSE", response)
                try {
                    val obj = JSONObject(response)
                    if (obj.getString("status") == "success") {
                        val data = obj.getJSONArray("data")
                        val sType = object : TypeToken<List<Location>>() {}.type
                        val newLocations = Gson().fromJson<List<Location>>(data.toString(), sType)

                        locations.clear()
                        locations.addAll(newLocations)

                        // Ganti pemanggilan updatePlaylist() dengan ini
                        locationAdapter.notifyDataSetChanged()

                    } else {
                        Toast.makeText(context, "Gagal memuat data", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Log.e("API_ERROR", "Error parsing data: " + e.message)
                    Toast.makeText(context, "Format data salah", Toast.LENGTH_SHORT).show()
                }
            },
            { error ->
                Log.e("API_ERROR", error.toString())
                Toast.makeText(context, "Koneksi ke server gagal", Toast.LENGTH_SHORT).show()
            }
        )
        q.add(stringRequest)
    }

    // Tambahan (Best Practice): Selalu tambahkan ini untuk menghindari memory leak
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}