package com.example.myqrscanapp.Activity

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myqrscanapp.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter

class GenerateActivity : AppCompatActivity() {
    private val rotateOpen: Animation by lazy { AnimationUtils.loadAnimation(this,R.anim.rotate_open_anim) }
    private val rotateClose: Animation by lazy { AnimationUtils.loadAnimation(this,R.anim.rotate_close_anim) }
    private val fromBottom: Animation by lazy { AnimationUtils.loadAnimation(this,R.anim.from_bottom_anim) }
    private val toBottom: Animation by lazy { AnimationUtils.loadAnimation(this,R.anim.to_bottom_anim) }

    //    private lateinit var binding: FragmentGenerateBinding
    private lateinit var qrcode : ImageView
    private lateinit var txt_content : EditText
    private lateinit var btn_generate : Button
    private var qrImage  : Bitmap? = null





    private var clicked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_generate)

        val btn_generate = findViewById<Button>(R.id.btn_generate)

        qrcode = findViewById(R.id.qrcode)
        txt_content = findViewById(R.id.txt_content)

        val regular_share = findViewById<FloatingActionButton>(R.id.regular_share)
        val share_whatsapp = findViewById<FloatingActionButton>(R.id.share_whatsapp)
        val share_mail = findViewById<FloatingActionButton>(R.id.share_mail)
        val share_other = findViewById<FloatingActionButton>(R.id.share_other)

        btn_generate.setOnClickListener {
            val data = txt_content.text.toString().trim()

            if (data.isEmpty()){
                Toast.makeText(this,"Field is Empty", Toast.LENGTH_SHORT).show()
            }else{
                val writer = QRCodeWriter()
                try {
                    val bitMatrix = writer.encode(data, BarcodeFormat.QR_CODE,512,512)
                    val width = bitMatrix.width
                    val height = bitMatrix.height
                    val bmp  = Bitmap.createBitmap(width,height, Bitmap.Config.RGB_565)
                    for(x in 0  until width){
                        for(y in 0 until height){
                            bmp.setPixel(x,y, if(bitMatrix[x,y]) Color.BLACK else Color.WHITE)
                        }
                    }
                    qrcode.setImageBitmap(bmp)

                }catch (e: WriterException){
                    e.printStackTrace()
                }
            }
        }




        regular_share.setOnClickListener {
            onAddButtonClicked()
        }

        share_mail.setOnClickListener {
            Toast.makeText(this,"will be update late", Toast.LENGTH_SHORT).show()
        }
        share_other.setOnClickListener {
            Toast.makeText(this,"will be update late", Toast.LENGTH_SHORT).show()
        }
        share_whatsapp.setOnClickListener {
            Toast.makeText(this,"will be update late", Toast.LENGTH_SHORT).show()
        }


    }

    private fun onAddButtonClicked() {
        setVisibility(clicked)
        setAnimation(clicked)
        clicked = !clicked
    }

    private fun setAnimation(clicked: Boolean) {

        var share_whatsapp = findViewById<FloatingActionButton>(R.id.share_whatsapp)
        val share_mail = findViewById<FloatingActionButton>(R.id.share_mail)
        val share_other = findViewById<FloatingActionButton>(R.id.share_other)




        if(!clicked){
            share_mail?.visibility = View.VISIBLE
            share_whatsapp?.visibility = View.VISIBLE
            share_other?.visibility = View.VISIBLE
        }else{
            share_mail?.visibility = View.INVISIBLE
            share_whatsapp?.visibility = View.INVISIBLE
            share_other?.visibility = View.INVISIBLE
        }
    }

    private fun setVisibility(clicked: Boolean) {

        var share_whatsapp = findViewById<FloatingActionButton>(R.id.share_whatsapp)
        val share_mail = findViewById<FloatingActionButton>(R.id.share_mail)
        val share_other = findViewById<FloatingActionButton>(R.id.share_other)
        var regular_share = findViewById<FloatingActionButton>(R.id.regular_share)
        if (!clicked){
            share_mail?.startAnimation(fromBottom)
            share_other?.startAnimation(fromBottom)
            share_whatsapp?.startAnimation(fromBottom)
            regular_share?.startAnimation(rotateOpen)
        }else{
            share_mail?.startAnimation(toBottom)
            share_other?.startAnimation(toBottom)
            share_whatsapp?.startAnimation(toBottom)
            regular_share?.startAnimation(rotateClose)
        }
    }

}