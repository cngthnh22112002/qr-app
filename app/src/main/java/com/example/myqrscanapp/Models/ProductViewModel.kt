package com.example.myqrscanapp.Models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myqrscanapp.Admin.model.ProductData
import com.example.myqrscanapp.Repository.ProductRepository

class ProductViewModel : ViewModel() {
    private val repository : ProductRepository = ProductRepository().getInstance()
    private val _allProducts = MutableLiveData<ArrayList<ProductData>>()
    val allProducts : LiveData<ArrayList<ProductData>> = _allProducts
    init {
        repository.loadStores(_allProducts)
    }
}