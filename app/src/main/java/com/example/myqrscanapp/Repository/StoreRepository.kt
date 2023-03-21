package com.example.myqrscanapp.Repository

import androidx.lifecycle.MutableLiveData
import com.example.myqrscanapp.Data.StoreData
import com.google.firebase.database.*

class StoreRepository {

    private val databaseReference : DatabaseReference = FirebaseDatabase.getInstance().getReference("store")

    @Volatile private var INSTANCE : StoreRepository?= null

    fun getInstance() : StoreRepository {
        return INSTANCE ?: synchronized(this){
            val instance = StoreRepository()
            INSTANCE = instance
            instance
        }


    }


    fun loadStores(storeList : MutableLiveData<List<StoreData>>){
        databaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                try {

                    val _storeLists : List<StoreData> = snapshot.children.map { dataSnapshot ->
                        dataSnapshot.getValue(StoreData::class.java)!!
                    }
                    storeList.postValue(_storeLists)

                }catch (_: Exception){
                }


            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })


    }

}