package com.example.mapandmarkers.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import com.example.mapandmarkers.R
import com.example.mapandmarkers.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initMainView()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.bottom_navigation_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun initMainView() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.map -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_container, MapFragment.newInstance(this))
                        .addToBackStack(resources.getString(R.string.map))
                        .commit()
                    true
                }
                R.id.markers -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_container, MarkersFragment.newInstance())
                        .addToBackStack(resources.getString(R.string.map))
                        .commit()
                    true
                }
                else -> false
            }
        }
        binding.bottomNavigation.selectedItemId = R.id.map
    }
}