package com.example.myqrscanapp.Fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.myqrscanapp.Activity.HistoryActivity
import com.example.myqrscanapp.Activity.ScanActivity
import com.example.myqrscanapp.R

//
class ScanMenu : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_scan_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        val scanButton = view.findViewById<Button>(R.id.scan_button)
        val historyButton = view.findViewById<Button>(R.id.history_button)

        scanButton.setOnClickListener {
            activity?.let {
                val intent = Intent(it, ScanActivity::class.java)
                startActivity(intent)
            }
        }

        historyButton.setOnClickListener {
            activity?.let {
                val intent = Intent(it, HistoryActivity::class.java)
                startActivity(intent)
            }
        }
    }
}