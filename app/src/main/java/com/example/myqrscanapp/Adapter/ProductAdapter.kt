package com.example.myqrscanapp.Adapter

import android.annotation.SuppressLint
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myqrscanapp.Admin.model.ProductData
import com.example.myqrscanapp.Data.StoreData
import com.example.myqrscanapp.R
import com.squareup.picasso.Picasso

class ProductAdapter : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    private val productList = ArrayList<ProductData>()

    class  ProductViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val name : TextView = itemView.findViewById(R.id.name)
        val price : TextView = itemView.findViewById(R.id.price)
        val image: ImageView = itemView.findViewById(R.id.products_image)
        val detail_1 : TextView = itemView.findViewById(R.id.dt1)
        val detail_2 : TextView = itemView.findViewById(R.id.dt2)
        val detail_3 : TextView = itemView.findViewById(R.id.dt3)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.product_item,
            parent,false
        )
        return ProductViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {

        val currentitem = productList[position]

        holder.name.text = currentitem.name
        holder.price.text = currentitem.price
        Picasso.get()
            .load(currentitem.image)
            .resize(100,100)
            .into(holder.image)
        holder.detail_1.text = currentitem.details?.detail1
        holder.detail_2.text = currentitem.details?.detail2
        holder.detail_3.text = currentitem.details?.detail3
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateProductList(productList : ArrayList<ProductData>){
        this.productList.clear()
        this.productList.addAll(productList)
        notifyDataSetChanged()
    }

    


}