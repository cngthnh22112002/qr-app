package com.example.myqrscanapp.Admin.model

data class ProductData(
    var name : String ?= null,
    var price : String ?= null,
    var image : String ?= null,
    var details : DetailsDt ?= null
)
