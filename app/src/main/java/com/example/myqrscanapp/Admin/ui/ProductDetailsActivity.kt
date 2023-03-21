package com.example.myqrscanapp.Admin.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.myqrscanapp.Admin.model.DetailsDt
import com.example.myqrscanapp.Admin.model.ProductData
import com.example.myqrscanapp.R
import com.google.firebase.database.FirebaseDatabase

class ProductDetailsActivity : AppCompatActivity() {
    private lateinit var tvPdId: TextView
    private lateinit var tvPdType: TextView
    private lateinit var tvPdOEM: TextView
    private lateinit var tvPdName: TextView
    private lateinit var tvPdPrice: TextView
    private lateinit var tvPdImage: TextView
    private lateinit var tvPdDetail1: TextView
    private lateinit var tvPdDetail2: TextView
    private lateinit var tvPdDetail3: TextView
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_details)
        initView()
        setValuesToViews()
        btnUpdate.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("pdName").toString()
            )
        }
        btnDelete.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("pdId").toString(),
                intent.getStringExtra("pdType").toString(),
                intent.getStringExtra("pdOEM").toString()
            )
        }
    }

    @SuppressLint("MissingInflatedId")
    private fun openUpdateDialog(
        pdName: String
    ) {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_dialog, null)

        mDialog.setView(mDialogView)

        val etPdId = mDialogView.findViewById<EditText>(R.id.etPdId)
        val etPdType = mDialogView.findViewById<EditText>(R.id.etPdType)
        val etPdOEM = mDialogView.findViewById<EditText>(R.id.etPdOEM)
        val etPdName = mDialogView.findViewById<EditText>(R.id.etPdName)
        val etPdPrice = mDialogView.findViewById<EditText>(R.id.etPdPrice)
        val etPdImage = mDialogView.findViewById<EditText>(R.id.etPdImage)
        val etPdDetail1 = mDialogView.findViewById<EditText>(R.id.etPdDetail1)
        val etPdDetail2 = mDialogView.findViewById<EditText>(R.id.etPdDetail2)
        val etPdDetail3 = mDialogView.findViewById<EditText>(R.id.etPdDetail3)

        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateData)

        etPdId.setText(intent.getStringExtra("pdId").toString())
        etPdType.setText(intent.getStringExtra("pdType").toString())
        etPdOEM.setText(intent.getStringExtra("pdOEM").toString())
        etPdName.setText(intent.getStringExtra("pdName").toString())
        etPdPrice.setText(intent.getStringExtra("pdPrice").toString())
        etPdImage.setText(intent.getStringExtra("pdImage").toString())
        etPdDetail1.setText(intent.getStringExtra("pdDetail1").toString())
        etPdDetail2.setText(intent.getStringExtra("pdDetail2").toString())
        etPdDetail3.setText(intent.getStringExtra("pdDetail3").toString())

        mDialog.setTitle("Updating $pdName Product")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener {
            updatePdData(
                etPdId.text.toString(),
                etPdType.text.toString(),
                etPdOEM.text.toString(),
                etPdName.text.toString(),
                etPdPrice.text.toString(),
                etPdImage.text.toString(),
                etPdDetail1.text.toString(),
                etPdDetail2.text.toString(),
                etPdDetail3.text.toString()
            )

            Toast.makeText(applicationContext, "Product Data Updated", Toast.LENGTH_LONG).show()

            //we are setting updated data to our textviews
            tvPdId.text = etPdId.text.toString()
            tvPdType.text = etPdType.text.toString()
            tvPdOEM.text = etPdOEM.text.toString()
            tvPdName.text = etPdName.text.toString()
            tvPdPrice.text = etPdPrice.text.toString()
            tvPdImage.text = etPdImage.text.toString()
            tvPdDetail1.text = etPdDetail1.text.toString()
            tvPdDetail2.text = etPdDetail2.text.toString()
            tvPdDetail3.text = etPdDetail3.text.toString()


            alertDialog.dismiss()
        }
    }

    private fun updatePdData(
        id: String,
        type: String,
        OEM: String,
        name: String,
        price: String,
        image: String,
        detail_1: String,
        detail_2: String,
        detail_3: String
    ) {
        val dbRef = FirebaseDatabase.getInstance().getReference("product").child(type).child(OEM).child(id)
        val productDetails = DetailsDt(detail_1, detail_2, detail_3)
        val productData = ProductData(name,price,image,productDetails)
        dbRef.setValue(productData)
    }

    private fun deleteRecord(
        id: String,
        type: String,
        OEM: String,
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("product").child(type).child(OEM).child(id)
        Log.d("CFR",id + type + OEM)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Product data deleted", Toast.LENGTH_LONG).show()

            val intent = Intent(this, FetchingActivity::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{ error ->
            Toast.makeText(this, "Deleting Err ${error.message}", Toast.LENGTH_LONG).show()
        }
    }


    private fun initView() {
        tvPdId = findViewById(R.id.tvPdId)
        tvPdType = findViewById(R.id.tvPdType)
        tvPdOEM = findViewById(R.id.tvPdOEM)
        tvPdName = findViewById(R.id.tvPdName)
        tvPdPrice = findViewById(R.id.tvPdPrice)
        tvPdImage = findViewById(R.id.tvPdImage)
        tvPdDetail1 = findViewById(R.id.tvPdDetail1)
        tvPdDetail2 = findViewById(R.id.tvPdDetail2)
        tvPdDetail3 = findViewById(R.id.tvPdDetail3)

        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
    }

    private fun setValuesToViews() {
        tvPdId.text = intent.getStringExtra("pdId")
        tvPdType.text = intent.getStringExtra("pdType")
        tvPdOEM.text = intent.getStringExtra("pdOEM")
        tvPdName.text = intent.getStringExtra("pdName")
        tvPdPrice.text = intent.getStringExtra("pdPrice")
        tvPdImage.text = intent.getStringExtra("pdImage")
        tvPdDetail1.text = intent.getStringExtra("pdDetail1")
        tvPdDetail2.text = intent.getStringExtra("pdDetail2")
        tvPdDetail3.text = intent.getStringExtra("pdDetail3")

    }
}