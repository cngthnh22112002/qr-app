package com.example.myqrscanapp.Fragment

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils.replace
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import com.example.myqrscanapp.Activity.ScanActivity
import com.example.myqrscanapp.R
import com.example.myqrscanapp.Adapter.SliderAdapter
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView

class Homepage : Fragment() {

    private val promotions: IntArray = intArrayOf(
        R.drawable.promotion_1,
        R.drawable.promotion_2,
        R.drawable.promotion_3,
        R.drawable.promotion_4,
        R.drawable.promotion_5
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_homepage, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sliderView = view.findViewById<SliderView>(R.id.promotion_slide)
        val sliderAdapter = SliderAdapter(promotions)
        sliderView.setSliderAdapter(sliderAdapter)
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM)
        sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION)
        sliderView.startAutoCycle()

        val btnProduct = view.findViewById<RelativeLayout>(R.id.products_list)
        btnProduct.setOnClickListener {
            makeCurrentFragment(Product())
        }
    }

    private fun makeCurrentFragment(fragment: Fragment) =
        parentFragmentManager.beginTransaction().apply {
            replace(R.id.homepage_layout,fragment)
            commit()
        }
}