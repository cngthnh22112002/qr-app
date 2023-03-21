package com.example.myqrscanapp.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.myqrscanapp.R

class WelcomeActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)


        val login_button = findViewById<Button>(R.id.login_button)
        val signup_button = findViewById<Button>(R.id.signup_button)

        login_button.setOnClickListener {

            val intent = Intent(this,LoginAcitivity::class.java)
            startActivity(intent)
        }

        signup_button.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }





}