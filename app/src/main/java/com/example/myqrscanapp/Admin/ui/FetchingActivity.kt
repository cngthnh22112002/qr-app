package com.example.myqrscanapp.Admin.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myqrscanapp.Admin.adapter.PdAdapter
import com.example.myqrscanapp.Admin.model.ProductData
import com.example.myqrscanapp.Admin.model.ProductModel
import com.example.myqrscanapp.R
import com.google.firebase.database.*


class FetchingActivity : AppCompatActivity() {
    private lateinit var pdRecyclerView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var pdList: ArrayList<ProductModel>
    private lateinit var src: DatabaseReference
    private lateinit var id:String
    private lateinit var type:String
    private lateinit var OEM:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetching)

        pdRecyclerView = findViewById(R.id.rvEmp)
        pdRecyclerView.layoutManager = LinearLayoutManager(this)
        pdRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)
        pdList = arrayListOf()
        id = "0"
        type = "unknow"
        OEM = "unknow"
        getPdData()

    }

    private fun getPdData() {

        pdRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE
        src = FirebaseDatabase.getInstance().getReference("product")

        src.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(products: DataSnapshot) {
                pdList.clear()
                if (products.exists()) {
                    for (item in products.children) {
                        type = item.key.toString()
                        for (cc in item.children) {
                            OEM = cc.key.toString()
                            for (a in cc.children) {
                                Log.d("GF", a.key.toString())
                                id = a.key.toString()
                                val pdData = a.getValue(ProductData::class.java)
                                val display = ProductModel(id,type,OEM,pdData)
                                pdList.add(display)
                            }
                        }
                    }
                }
                val mAdapter = PdAdapter(pdList)
                pdRecyclerView.adapter = mAdapter

                mAdapter.setOnItemClickListener(object : PdAdapter.onItemClickListener {
                    override fun onItemClick(position: Int) {
                        val intent =
                            Intent(this@FetchingActivity, ProductDetailsActivity::class.java)

                        //put extras
                        intent.putExtra("pdId", pdList[position].id.toString())
                        intent.putExtra("pdType", pdList[position].type.toString())
                        intent.putExtra("pdOEM", pdList[position].OEM.toString())
                        intent.putExtra("pdName", pdList[position].info?.name)
                        intent.putExtra("pdPrice", pdList[position].info?.price)
                        intent.putExtra("pdImage", pdList[position].info?.image)
                        intent.putExtra("pdDetail1", pdList[position].info?.details?.detail1)
                        intent.putExtra("pdDetail2", pdList[position].info?.details?.detail2)
                        intent.putExtra("pdDetail3", pdList[position].info?.details?.detail3)
                        startActivity(intent)
                        finish()
                    }

                })

                pdRecyclerView.visibility = View.VISIBLE
                tvLoadingData.visibility = View.GONE
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}