package com.example.myqrscanapp.Admin.ui


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myqrscanapp.Admin.model.DetailsDt
import com.example.myqrscanapp.Admin.model.ProductData
import com.example.myqrscanapp.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


class InsertionActivity : AppCompatActivity() {


    private lateinit var productId: EditText
    private lateinit var productType: EditText
    private lateinit var productOEM: EditText
    private lateinit var productName: EditText
    private lateinit var productPrice: EditText
    private lateinit var productImage: EditText
    private lateinit var productDetail1: EditText
    private lateinit var productDetail2: EditText
    private lateinit var productDetail3: EditText
    private lateinit var btnSaveData: Button
    private lateinit var btnUploadImage: Button
    private val reference = FirebaseStorage.getInstance().reference
    private var imageUri: Uri? = null


    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insertion)

        productId = findViewById(R.id.productId)
        productType = findViewById(R.id.productType)
        productOEM = findViewById(R.id.productOEM)
        productName = findViewById(R.id.productName)
        productPrice = findViewById(R.id.productPrice)
        productImage = findViewById(R.id.productImage)
        productDetail1 = findViewById(R.id.productDetail1)
        productDetail2 = findViewById(R.id.productDetail2)
        productDetail3 = findViewById(R.id.productDetail3)
        btnSaveData = findViewById(R.id.btnSave)
        btnUploadImage = findViewById(R.id.btnUpload)



        dbRef = FirebaseDatabase.getInstance().getReference("product")

        btnSaveData.setOnClickListener {
            if (imageUri != null){
                uploadToFirebase(imageUri!!)
            }else{
                Toast.makeText(this, "Please Select Image", Toast.LENGTH_SHORT).show()
            }
            saveProductData()
        }

        btnUploadImage.setOnClickListener {
            val galleryIntent = Intent()
            galleryIntent.action = Intent.ACTION_GET_CONTENT
            galleryIntent.type = "image/*"
            startActivityForResult(galleryIntent, 2)
        }
    }

    private fun saveProductData() {
        val pdId = productId.text.toString()
        val pdType = productType.text.toString()
        val pdOEM = productOEM.text.toString()
        val pdName = productName.text.toString()
        val pdPrice = productPrice.text.toString()
        var pdImage = productImage.text.toString()
        val pdDetail1 = productDetail1.text.toString()
        val pdDetail2 = productDetail2.text.toString()
        val pdDetail3 = productDetail3.text.toString()
        var imageLink: String? = null

        val fileRef: StorageReference =  reference.child(System.currentTimeMillis().toString() + "." + imageUri?.let {
            getFileExtension(
                it
            )
        })
        imageUri?.let {
            fileRef.putFile(it).addOnSuccessListener {
                fileRef.downloadUrl.addOnSuccessListener { uri ->
                    Log.d("VV", uri.toString())
                    imageLink = uri.toString()
                    Log.d("GFR", imageLink!!)


                    if(pdImage == ""){
                        pdImage = imageLink.toString()
                        Log.d("GFV",pdImage)
                    }

                    val productDetails = DetailsDt(pdDetail1, pdDetail2, pdDetail3)
                    val productData = ProductData(pdName,pdPrice,pdImage,productDetails)


                    dbRef.child(pdType).child(pdOEM).child(pdId).setValue(productData)
                        .addOnCompleteListener {
                            Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()
                            productId.text.clear()
                            productType.text.clear()
                            productOEM.text.clear()
                            productName.text.clear()
                            productPrice.text.clear()
                            productImage.text.clear()
                            productDetail1.text.clear()
                            productDetail2.text.clear()
                            productDetail3.text.clear()


                        }.addOnFailureListener { err ->
                            Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
                        }

                }
            }
        }


    }

    private fun uploadToFirebase(uri: Uri) {
        val fileRef: StorageReference =  reference.child(System.currentTimeMillis().toString() + "." + getFileExtension(uri))
        fileRef.putFile(uri).addOnSuccessListener {
            fileRef.downloadUrl.addOnSuccessListener { uri ->
                Log.d("VFVV", uri.toString())
                //imageLink = uri.toString()
            }
        }
    }

    private fun getFileExtension(mUri: Uri): String? {
        val cr = contentResolver
        val mime = MimeTypeMap.getSingleton()
        return mime.getExtensionFromMimeType(cr.getType(mUri))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            imageUri = data.data
        }
    }
}