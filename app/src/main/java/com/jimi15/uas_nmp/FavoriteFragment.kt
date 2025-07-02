package com.jimi15.uas_nmp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jimi15.uas_nmp.databinding.FragmentFavoriteBinding
import org.json.JSONObject

class FavoriteFragment : Fragment() {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private val favorites = ArrayList<Location>()
    private lateinit var favoriteAdapter: FavoriteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        fetchFavoritesFromServer()
    }

    private fun setupRecyclerView() {

        favoriteAdapter = FavoriteAdapter(favorites)
        binding.recFav.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = favoriteAdapter
        }
    }

    private fun fetchFavoritesFromServer() {

        val sharedPreferences = requireActivity().getSharedPreferences("USER_SESSION", Context.MODE_PRIVATE)
        val idUser = sharedPreferences.getString("id_user", null)

        if (idUser == null) {
            Toast.makeText(context, "Silakan login terlebih dahulu", Toast.LENGTH_SHORT).show()

            favorites.clear()
            favoriteAdapter.notifyDataSetChanged()
            return
        }

        val q = Volley.newRequestQueue(activity)
        val url = "http://10.0.2.2/api/get_user_favorites.php?id_user=$idUser"

        val stringRequest = StringRequest(Request.Method.GET, url,
            { response ->
                Log.d("API_RESPONSE_FAV", response)
                try {
                    val obj = JSONObject(response)
                    if (obj.getString("status") == "success") {
                        val data = obj.getJSONArray("data")
                        val sType = object : TypeToken<List<Location>>() {}.type
                        val newFavorites = Gson().fromJson<List<Location>>(data.toString(), sType)

                        favorites.clear()
                        favorites.addAll(newFavorites)
                        favoriteAdapter.notifyDataSetChanged()
                    }
                } catch (e: Exception) {
                    Log.e("API_ERROR_FAV", "Error parsing data: " + e.message)
                }
            },
            { error ->
                Log.e("API_ERROR_FAV", error.toString())
            }
        )
        q.add(stringRequest)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}