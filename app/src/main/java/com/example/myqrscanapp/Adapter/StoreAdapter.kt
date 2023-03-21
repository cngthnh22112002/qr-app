package com.example.myqrscanapp.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myqrscanapp.Data.StoreData
import com.example.myqrscanapp.R
import com.squareup.picasso.Picasso

class StoreAdapter : RecyclerView.Adapter<StoreAdapter.StoreViewHolder>() {

    private val storeList = ArrayList<StoreData>()

    class  StoreViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val location : TextView = itemView.findViewById(R.id.tvlastName)
        val distance : TextView = itemView.findViewById(R.id.tvage)
        val image: ImageView = itemView.findViewById(R.id.store_image)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.store_item,
            parent,false
        )
        return StoreViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: StoreViewHolder, position: Int) {

        val currentitem = storeList[position]

        holder.location.text = currentitem.location
        holder.distance.text = currentitem.distance
        Picasso.get()
            .load(currentitem.image)
            .resize(100,100)
            .into(holder.image)
    }

    override fun getItemCount(): Int {
        return storeList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateStoreList(storeList : List<StoreData>){
        this.storeList.clear()
        this.storeList.addAll(storeList)
        notifyDataSetChanged()
    }


}