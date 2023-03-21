package com.example.myqrscanapp.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myqrscanapp.Adapter.ProductAdapter
import com.example.myqrscanapp.Models.ProductViewModel
import com.example.myqrscanapp.R

private lateinit var viewModel : ProductViewModel
private lateinit var productRecyclerView: RecyclerView
lateinit var product_adapter: ProductAdapter

class Product : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productRecyclerView = view.findViewById(R.id.productRecyclerView)
        productRecyclerView.layoutManager = LinearLayoutManager(context)
        productRecyclerView.setHasFixedSize(true)
        product_adapter = ProductAdapter()
        productRecyclerView.adapter = product_adapter

        viewModel = ViewModelProvider(this)[ProductViewModel::class.java]

        viewModel.allProducts.observe(viewLifecycleOwner) {
            product_adapter.updateProductList(it)
        }
    }
}