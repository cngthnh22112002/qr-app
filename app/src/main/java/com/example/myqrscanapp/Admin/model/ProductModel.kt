package com.example.myqrscanapp.Admin.model

data class ProductModel(
    val id: String ?= null,
    val type: String ?= null,
    val OEM: String ?= null,
    val info:ProductData ?= null
)
