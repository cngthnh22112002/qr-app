package com.example.myqrscanapp.Models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myqrscanapp.Data.StoreData
import com.example.myqrscanapp.Repository.StoreRepository

class StoreViewModel : ViewModel() {

    private val repository : StoreRepository = StoreRepository().getInstance()
    private val _allStores = MutableLiveData<List<StoreData>>()
    val allStores : LiveData<List<StoreData>> = _allStores


    init {
        repository.loadStores(_allStores)
    }

}