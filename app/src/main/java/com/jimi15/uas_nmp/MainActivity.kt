package com.jimi15.uas_nmp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.jimi15.uas_nmp.databinding.ActivityMainBinding
import androidx.appcompat.app.ActionBarDrawerToggle
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var toggle: ActionBarDrawerToggle

    val fragments: ArrayList<Fragment> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupDrawer()

        fragments.add(ExploreFragment())
        fragments.add(FavoriteFragment())
        fragments.add(ProfileFragment())

        binding.viewPager.adapter = ViewPagerAdapter(this, fragments)

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback()
        {
            override fun onPageSelected(position: Int) {
                binding.bottomNav.selectedItemId =
                    binding.bottomNav.menu.getItem(position).itemId
            }
        })

        binding.bottomNav.setOnItemSelectedListener{
            binding.viewPager.currentItem = when(it.itemId) {
                R.id.itemExprole -> 0
                R.id.itemFavorite -> 1
                R.id.itemProfile -> 2
                else -> 0 // default to home
            }
            true
        }
    }

    private fun setupDrawer() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            R.string.drawer_open,
            R.string.drawer_close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        val sharedPreferences = getSharedPreferences("USER_SESSION", Context.MODE_PRIVATE)
        val name = sharedPreferences.getString("name", "Guest")
        val headerView = binding.navigationView.getHeaderView(0)
        val txtUser = headerView.findViewById<TextView>(R.id.txtUser)
        txtUser.text = "Welcome, $name"

        binding.navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.itemChange -> {
                    val intent = Intent(this, ChangePasswordActivity::class.java)
                    startActivity(intent)
                }

                R.id.itemOut -> {

                    sharedPreferences.edit().clear().apply()
                    val intent = Intent(this, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }
            }
            binding.drawerLayout.closeDrawers()
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}