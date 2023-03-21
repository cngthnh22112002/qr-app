package com.example.myqrscanapp.db

import com.example.myqrscanapp.db.entities.QrResult

interface DBHelperI {
    fun insertQRResult(result: String) : Int

    fun getQrResult(id : Int) : QrResult

    fun addToFavourite(id : Int) : Int

    fun removeFromFavourite(id : Int) : Int

    fun getAllScannedResult() : List<QrResult>

    fun getAllFavouriteQrScannedResult() : List<QrResult>

    fun deleteQrResult(id : Int) : Int

    fun deleteAllQRScannedResult()

    fun deleteAllFavouriteQRScannedResult()

}