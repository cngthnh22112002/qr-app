package com.example.myqrscanapp.Activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.myqrscanapp.Admin.ui.AdminActivity
import com.example.myqrscanapp.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginAcitivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var auth: FirebaseAuth


    private var isRemebered = false
    private var isAdmin = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_acitivity)

        sharedPreferences = getSharedPreferences("SHARED_PREF",Context.MODE_PRIVATE)

        isRemebered = sharedPreferences.getBoolean("CHECKBOX",false)
        isAdmin = sharedPreferences.getBoolean("ISADMIN",false)

        if (isRemebered && isAdmin) {
            val intent = Intent(this, AdminActivity::class.java)
            startActivity(intent)
            finish()
        }else if(isRemebered){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

       val checkBox = findViewById<CheckBox>(R.id.checkBox)
        val et_login_email = findViewById<EditText>(R.id.editTextTextEmailAddress)
        val et_login_password = findViewById<EditText>(R.id.editTextTextPassword)
        val back_image  = findViewById<ImageView>(R.id.back_image)
        val login_main_button = findViewById<Button>(R.id.login_main_button)

        val to_signup_activity = findViewById<TextView>(R.id.to_signup_activity)


        back_image.setOnClickListener {
            val intent = Intent(this, WelcomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        login_main_button.setOnClickListener {

            val checked: Boolean = checkBox.isChecked
            val loginid: String = et_login_email.text.toString()
            val loginpassword: String = et_login_password.text.toString()

            val editor: SharedPreferences.Editor = sharedPreferences.edit()


            editor.putString("LOGINID", loginid)
            editor.putString("LOGINPASSWORD", loginpassword)
            editor.putBoolean("CHECKBOX", checked)
            if(loginid == "admin@gmail.com"){
                editor.putBoolean("ISADMIN", true)
            }
            editor.apply()

            Toast.makeText(this,"Information Saved", Toast.LENGTH_LONG).show()



            when{
                TextUtils.isEmpty(et_login_email.text.toString().trim {  it <= ' '  }) -> {
                    Toast.makeText(this,"Please enter email.",Toast.LENGTH_SHORT).show()
                }
                TextUtils.isEmpty(et_login_password.text.toString().trim {  it <= ' ' }) -> {
                    Toast.makeText(this,"Please enter password.",Toast.LENGTH_SHORT).show()
                }

                else -> {
                    val email: String = et_login_email.text.toString().trim { it <= ' '}
                    val password: String = et_login_password.text.toString().trim { it <= ' '}


                    //create an instance and create a register a user with email and password
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(
                            OnCompleteListener<AuthResult> { task ->
                                //if the registration is successful done
                                if (task.isSuccessful) {
                                    //Firebase registered user
                                    task.result!!.user!!

                                    Toast.makeText(this, "You were logged in successfully.",Toast.LENGTH_SHORT).show()

                                    if(email == "admin@gmail.com"){
                                        val intent =
                                            Intent(this, AdminActivity::class.java)
                                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                        startActivity(intent)
                                        finish()

                                    }else {
                                        val intent =
                                            Intent(this,MainActivity::class.java)
                                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                        startActivity(intent)
                                        finish()
                                    }
                                } else {
                                    //If the logging is not successful then show error message.
                                    Toast.makeText(this,task.exception!!.message.toString(),Toast.LENGTH_SHORT).show()
                                }
                            })
                }

            }
        }


        //handle click open signup activity
        to_signup_activity.setOnClickListener {
            val intent = Intent(this,SignupActivity::class.java)
            startActivity(intent)
        }

    }

}