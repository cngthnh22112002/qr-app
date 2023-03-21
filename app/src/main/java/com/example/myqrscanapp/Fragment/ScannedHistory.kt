package com.example.myqrscanapp.Fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.myqrscanapp.R
import com.example.myqrscanapp.db.DBHelper
import com.example.myqrscanapp.db.DBHelperI
import com.example.myqrscanapp.db.database.QrResultDatabase
import com.example.myqrscanapp.db.entities.QrResult
import com.example.myqrscanapp.Adapter.ScannedResultListAdapter

class ScannedHistory : Fragment() {


      enum class ResultListType {
          ALL_RESULT, FAVOURITE_RESULT
      }


    private var resultListType: ResultListType? = null

    private lateinit var mView : View
    private lateinit var dbHelperI : DBHelperI

    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?,
        savedInstanceState : Bundle?
    ) : View {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_scanned_history, container, false)
        init()
        setSwipeRefresh()
        onClicks()
        showListOfResults()
        return mView.rootView

    }

    private fun showListOfResults() {
        when(resultListType){
            ResultListType.ALL_RESULT -> {
            showAllResults()
            }
            ResultListType.FAVOURITE_RESULT -> {
             showFavouriteResults()
            }
            else -> {showAllResults()}
        }
    }

    private fun showFavouriteResults() {
        val tvHeaderText = mView.findViewById<TextView>(R.id.tvHeaderText)
        val listFavouriteResult = dbHelperI.getAllFavouriteQrScannedResult()
        showResults(listFavouriteResult)
        tvHeaderText.text = "Favourite Results"
    }

    private fun showResults(listOfQRResults : List<QrResult>) {


        if (listOfQRResults.isEmpty()) {
            showEmptyState()
        }else{
            initRecyclerView(listOfQRResults)
        }

    }

    private fun initRecyclerView(listOfQRResults : List<QrResult>) {
        //val noResultFound = mView.findViewById<ImageView>(R.id.noResultFound)
        val scannedHistoryRecyclerView = mView.findViewById<RecyclerView>(R.id.scannedHistoryRecyclerView)

        scannedHistoryRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        scannedHistoryRecyclerView.adapter = ScannedResultListAdapter(dbHelperI,requireContext(),listOfQRResults.toMutableList())


    }

    private fun showEmptyState() {
        val noResultFound = mView.findViewById<ImageView>(R.id.noResultFound)
        val scannedHistoryRecyclerView = mView.findViewById<RecyclerView>(R.id.scannedHistoryRecyclerView)

        scannedHistoryRecyclerView.visibility = View.GONE
        noResultFound.visibility = View.VISIBLE
    }

    private fun showAllResults() {
        val tvHeaderText = mView.findViewById<TextView>(R.id.tvHeaderText)

        val listAllResult = dbHelperI.getAllScannedResult()
        showResults(listAllResult)
        tvHeaderText.text = "Recent Scanned History"

    }

    private fun onClicks() {
       val removeAll = mView.findViewById<ImageView>(R.id.removeAll)

        removeAll.setOnClickListener {
            showRemoveAllScannedResultDialog()
        }
    }

    private fun showRemoveAllScannedResultDialog() {
        AlertDialog.Builder(requireContext(), R.style.CustomAlertDialog).setTitle("Clear All")
            .setMessage("All Recent Records will be deleted")
            .setPositiveButton("Clear") { _, _ ->
                clearAllRecords()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.cancel()
            }.show()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun clearAllRecords() {
        dbHelperI = DBHelper(QrResultDatabase.getAppDatabase(requireContext())!!)



        when (resultListType) {
            ResultListType.ALL_RESULT -> dbHelperI.deleteAllQRScannedResult()
            ResultListType.FAVOURITE_RESULT -> dbHelperI.deleteAllFavouriteQRScannedResult()

            else -> {dbHelperI.deleteAllQRScannedResult()}
        }

    val scannedHistoryRecyclerView = mView.findViewById<RecyclerView>(R.id.scannedHistoryRecyclerView)

    scannedHistoryRecyclerView.adapter?.notifyDataSetChanged()
        showListOfResults()
    }

    private fun setSwipeRefresh() {
        val swipeRefresh = mView.findViewById<SwipeRefreshLayout>(R.id.swipeRefresh)

        swipeRefresh.setOnRefreshListener {
            swipeRefresh.isRefreshing = false
            showListOfResults()
        }
    }

    private fun init() {
        dbHelperI = DBHelper(QrResultDatabase.getAppDatabase(requireContext())!!)
    }


}