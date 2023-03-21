package com.example.myqrscanapp.Activity

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.KeyEvent
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.budiyev.android.codescanner.*
import com.example.myqrscanapp.R
import com.example.myqrscanapp.db.DBHelper
import com.example.myqrscanapp.db.DBHelperI
import com.example.myqrscanapp.db.database.QrResultDatabase
import com.example.myqrscanapp.Dialogs.QrCodeResultDialog


class ScanActivity : AppCompatActivity() {



    private lateinit var codeScanner: CodeScanner

    private lateinit var resultDialog : QrCodeResultDialog
    private lateinit var dbHelperI : DBHelperI
    private  var result = ""

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish()
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)

        init()
        initViews()

    }

    private fun init() {
        dbHelperI = DBHelper(QrResultDatabase.getAppDatabase(this)!!)
    }
    private fun initViews() {
        startScanning()
        setResultDialog()
    }

    private fun setResultDialog() {
        resultDialog = QrCodeResultDialog(this)
    }

    private fun startScanning() {
        findViewById<TextView>(R.id.scannedText)
        val scannerView: CodeScannerView = findViewById(R.id.scanner_view)

        // Parameters (default values)
        codeScanner = CodeScanner(this,scannerView)
        codeScanner.camera = CodeScanner.CAMERA_BACK // or CAMERA_FRONT or specific camera id
        codeScanner.formats = CodeScanner.ALL_FORMATS // list of type BarcodeFormat,
        codeScanner.autoFocusMode = AutoFocusMode.SAFE // or CONTINUOUS
        codeScanner.scanMode = ScanMode.SINGLE // or CONTINUOUS or PREVIEW
        codeScanner.isAutoFocusEnabled = true // Whether to enable auto focus or not
        codeScanner.isFlashEnabled = false // Whether to enable flash or not

        // Callbacks
        codeScanner.decodeCallback = DecodeCallback {
            runOnUiThread {
                result = it.text
                onQrResult(result)
            }
        }

        codeScanner.errorCallback = ErrorCallback {
            runOnUiThread {
                Toast.makeText(this, "Camera initialization error: ${it.message}", Toast.LENGTH_SHORT).show()
            }
        }

        scannerView.setOnClickListener {
            codeScanner.startPreview()
        }
    }

    private fun onQrResult(text : String?) {
        if(text.isNullOrEmpty()){
            Toast.makeText(this,"Empty Qr Code",Toast.LENGTH_SHORT ).show()
        } else {
            if(text.contains("https://myqrscanapp-default-rtdb.firebaseio.com/")){
                val intent = Intent(this, DisplayProduct::class.java)
                val getpath = text.replace("https://myqrscanapp-default-rtdb.firebaseio.com/","")
                intent.putExtra("path", getpath)
                startActivity(intent)
            }
            saveToDatabase()
        }
    }

    private fun saveToDatabase() {
        val insertedRowId = dbHelperI.insertQRResult(result)
        val qrResult = dbHelperI.getQrResult(insertedRowId)
        resultDialog.show(qrResult)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ){ super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 123){
            if(grantResults[0] == PackageManager.PERMISSION_DENIED){
                Toast.makeText(this, "Camera permission granted", Toast.LENGTH_SHORT).show()
                startScanning()
            }else{
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (::codeScanner.isInitialized){
            codeScanner.startPreview()
        }
    }

    override fun onPause() {
        if (::codeScanner.isInitialized){
            codeScanner.releaseResources()
        }
        super.onPause()
    }
}