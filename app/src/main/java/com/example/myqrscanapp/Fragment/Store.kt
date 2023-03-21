package com.example.myqrscanapp.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myqrscanapp.Adapter.StoreAdapter
import com.example.myqrscanapp.Models.StoreViewModel
import com.example.myqrscanapp.R


private lateinit var viewModel : StoreViewModel
private lateinit var storeRecyclerView: RecyclerView
lateinit var store_adapter: StoreAdapter

class Store : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_store, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        storeRecyclerView = view.findViewById(R.id.recyclerView)
        storeRecyclerView.layoutManager = LinearLayoutManager(context)
        storeRecyclerView.setHasFixedSize(true)
        store_adapter = StoreAdapter()
        storeRecyclerView.adapter = store_adapter

        viewModel = ViewModelProvider(this)[StoreViewModel::class.java]

        viewModel.allStores.observe(viewLifecycleOwner) {
            store_adapter.updateStoreList(it)
        }
    }
}