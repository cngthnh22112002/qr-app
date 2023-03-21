package com.example.myqrscanapp.Activity

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myqrscanapp.Admin.model.ProductData
import com.example.myqrscanapp.R
import com.google.firebase.database.*
import com.squareup.picasso.Picasso

class DisplayProduct : AppCompatActivity() {
    private lateinit var src: DatabaseReference
    private lateinit var pdData: ProductData
    private lateinit var info:String
    private lateinit var type:String
    private lateinit var OEM:String
    private lateinit var id:String
    private lateinit var partList:ArrayList<String>

    private lateinit var productName: TextView
    private lateinit var productImage: ImageView
    private lateinit var productPrice: TextView
    private lateinit var detail1: TextView
    private lateinit var detail2: TextView
    private lateinit var detail3: TextView

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish()
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_displayproduct)

        productName = findViewById(R.id.product_name)
        productImage = findViewById(R.id.product_image)
        productPrice = findViewById(R.id.product_price)
        detail1 = findViewById(R.id.detail1)
        detail2 = findViewById(R.id.detail2)
        detail3 = findViewById(R.id.detail3)
        //getProductList()

        type = "None"
        OEM = "None"
        id = "None"
        partList = arrayListOf()
        val path = intent.getStringExtra("path")
        if (path != null) {
            var part = ""
            for (character in path){
                if(character == '/'){
                    partList.add(part)
                    part = ""
                } else {
                    part += character
                }
            }
            partList.add(part)
        }
        Log.d("NH",partList[0] + partList[1] + partList[2])

        info = partList[0]
        type = partList[1]
        OEM = partList[2]
        id = partList[3]

        Log.d("CCGT",info + type + OEM + id)

        src = FirebaseDatabase.getInstance().getReference(info).child(type).child(OEM).child(id)
        src.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(products: DataSnapshot) {
                if (products.exists()) {
                        pdData = products.getValue(ProductData::class.java)!!
                }
                productName.text = pdData.name
                productPrice.text = pdData.price
                Picasso.get()
                    .load(pdData.image)
                    .resize(500,500)
                    .into(productImage)
                detail1.text = pdData.details?.detail1
                detail2.text = pdData.details?.detail2
                detail3.text = pdData.details?.detail3

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }

    /*
    private fun getProductList() {
        val retrofit = RetrofitClient.getInstance()
        val apiInterface = retrofit.create(ApiInterface::class.java)
        val path = intent.getStringExtra("path")
        lifecycleScope.launchWhenCreated {
            try {
                val response = apiInterface.getAllProducts(path)
                if (response.isSuccessful) {
                    if (response.body()?.name == "") {
                        Toast.makeText(
                            this@DisplayProduct,
                            "No Data ",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    else {
                        productName.text = response.body()?.name
                        Picasso.get()
                            .load(response.body()?.image)
                            .resize(500,500)
                            .into(productImage)
                        productName.text = response.body()?.name
                        productPrice.text = response.body()?.price
                        detail1.text = response.body()?.details?.detail1 ?: ""
                        detail2.text = response.body()?.details?.detail2 ?: ""
                        detail3.text = response.body()?.details?.detail3 ?: ""
                    }
                } else {
                    Toast.makeText(
                        this@DisplayProduct,
                        response.errorBody().toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }catch (Ex:Exception){
                Ex.localizedMessage?.let { Log.e("Error", it) }
            }
        }
    }
     */

}