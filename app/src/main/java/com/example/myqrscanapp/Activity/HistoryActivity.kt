package com.example.myqrscanapp.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.myqrscanapp.Fragment.ScannedHistory
import com.example.myqrscanapp.R

class HistoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState : Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        val scannedHistory = ScannedHistory()
        makeCurrentFragment(scannedHistory)
    }

    private fun makeCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.history_fl_wrapper,fragment)
            commit()
        }

    }


}