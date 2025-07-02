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
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.jimi15.uas_nmp.databinding.FragmentProfileBinding
import org.json.JSONException
import org.json.JSONObject

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onResume() {
        super.onResume()
        loadProfileData()
    }

    private fun loadProfileData() {
        val sharedPreferences = requireActivity().getSharedPreferences("USER_SESSION",
            Context.MODE_PRIVATE)
        val idUser = sharedPreferences.getString("id_user", null)

        if (idUser == null) {
            Toast.makeText(context, "Gagal memuat profil, silakan login ulang.",
                Toast.LENGTH_LONG).show()
            return
        }

        val q = Volley.newRequestQueue(activity)
        val url = "http://10.0.2.2/api/get_user_profile.php?id_user=$idUser"

        val stringRequest = StringRequest(Request.Method.GET, url,
            { response ->
                try {
                    val obj = JSONObject(response)
                    if (obj.getString("status") == "success") {
                        val userData = obj.getJSONObject("data")

                        val name = userData.getString("name")
                        val email = userData.getString("email")
                        val join = userData.getString("created_at").toString()
                        val totalFavorites = userData.getInt("total_favorites")

                        updateUI(name, email, join, totalFavorites)
                    }
                } catch (e: JSONException) {
                    Log.e("ProfileError", "JSON parsing error: ${e.message}")
                }
            },
            { error ->
                Log.e("ProfileError", "Volley error: ${error.toString()}")
            }
        )
        q.add(stringRequest)
    }

    private fun updateUI(name: String, email: String, join: String, favoriteCount: Int) {
        with(binding) {
            txtName.text = "Name: $name"
            txtEmail.text = "Email: $email"
            txtJoin.text = "Join Since: $join"
            txtFav.text = "$favoriteCount Favorites"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}