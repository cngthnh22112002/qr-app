package com.example.myqrscanapp.Admin.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.myqrscanapp.Activity.GenerateActivity
import com.example.myqrscanapp.Activity.LoginAcitivity
import com.example.myqrscanapp.R
import com.google.firebase.auth.FirebaseAuth

class AdminActivity : AppCompatActivity() {
    private lateinit var btnInsertData: Button
    private lateinit var btnFetchData: Button
    private lateinit var  btnGenQR : Button
    private lateinit var  btnLogOut : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homeadmin)

        var preferences : SharedPreferences = this.getSharedPreferences("SHARED_PREF",
            Context.MODE_PRIVATE)

        btnInsertData = findViewById(R.id.btnInsertData)
        btnFetchData = findViewById(R.id.btnFetchData)
        btnGenQR = findViewById(R.id.btn_genQR)
        btnLogOut = findViewById(R.id.logout)

        btnInsertData.setOnClickListener {
            val intent = Intent(this, InsertionActivity::class.java)
            startActivity(intent)
        }

        btnFetchData.setOnClickListener {
            val intent = Intent(this, FetchingActivity::class.java)
            startActivity(intent)
        }

        btnGenQR.setOnClickListener {
            val intent = Intent(this,GenerateActivity::class.java)
            startActivity(intent)
        }

        btnLogOut.setOnClickListener {
            val editor: SharedPreferences.Editor = preferences.edit()
            editor.clear()
            editor.apply()

            //logout from app.
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, LoginAcitivity::class.java)
            startActivity(intent)
        }
    }


}