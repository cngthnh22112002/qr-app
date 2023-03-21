package com.example.myqrscanapp.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.myqrscanapp.R
import com.example.myqrscanapp.databinding.ActivityMainBinding
import com.example.myqrscanapp.Fragment.Homepage
import com.example.myqrscanapp.Fragment.ProfileFragment
import com.example.myqrscanapp.Fragment.ScanMenu
import com.example.myqrscanapp.Fragment.Store


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Inflate layout
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //Set default selected menu
        binding.bottomNavigationView.menu.findItem(R.id.homepage).isChecked = true
        makeCurrentFragment(Homepage())

        //Set navigation for fragment
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.homepage -> makeCurrentFragment(Homepage())
                R.id.store -> makeCurrentFragment(Store())
                R.id.scan -> makeCurrentFragment(ScanMenu())
                //R.id.buy -> print("x == 1")
                R.id.profile -> makeCurrentFragment(ProfileFragment())
            }
            return@setOnItemSelectedListener true
        }
    }

    private fun makeCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.homepage_layout,fragment)
            commit()
        }

}


