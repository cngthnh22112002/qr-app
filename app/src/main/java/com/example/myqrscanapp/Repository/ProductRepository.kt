package com.example.myqrscanapp.Repository

import androidx.lifecycle.MutableLiveData
import com.example.myqrscanapp.Admin.model.ProductData
import com.google.firebase.database.*

class ProductRepository {
    private val databaseReference : DatabaseReference = FirebaseDatabase.getInstance().getReference("product")

    @Volatile private var INSTANCE : ProductRepository?= null


    //Creat instance
    fun getInstance() : ProductRepository {
        return INSTANCE ?: synchronized(this){
            val instance = ProductRepository()
            INSTANCE = instance
            instance
        }
    }

    //Handle retrive data from database
    fun loadStores(productList: MutableLiveData<ArrayList<ProductData>>){
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    val _productLists : ArrayList<ProductData>  = arrayListOf()
                    for (item in snapshot.children) {
                        for (cc in item.children) {
                            for (a in cc.children) {
                                val pdData = a.getValue(ProductData::class.java)
                                _productLists.add(pdData!!)
                            }
                        }
                    }
                    productList.postValue(_productLists)
                }catch (_: Exception){
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}