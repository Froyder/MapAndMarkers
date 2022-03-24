package com.example.mapandmarkers.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mapandmarkers.database.RoomMarker
import com.example.mapandmarkers.databinding.MarkersFragmentBinding
import com.example.mapandmarkers.interfaces.OnDeleteItemClickListener
import com.example.mapandmarkers.interfaces.OnListItemClickListener
import com.example.mapandmarkers.utilities.MarkersAdapter
import com.example.mapandmarkers.viewmodel.MarkersViewModel

class MarkersFragment: BaseFragment<MarkersFragmentBinding>(MarkersFragmentBinding::inflate) {

    private lateinit var markersAdapter: MarkersAdapter
    private val viewModel: MarkersViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        markersAdapter = MarkersAdapter(onListItemClickListener, onDeleteItemClickListener)
        binding.markersRecycler.layoutManager = LinearLayoutManager(context)
        binding.markersRecycler.adapter = markersAdapter
        binding.deleteAllButton.setOnClickListener { deleteAllMarkers() }

        viewModel.mutableLiveData.observe(viewLifecycleOwner) { renderData(it) }
        setData()

        setFragmentResultListener(REQUEST_KEY) { _, bundle ->
            val request = bundle.getString(REQUEST).toString()
            if (request == POSITIVE) setData()
        }
    }

    private fun renderData(markersList: List<RoomMarker>) {
        markersAdapter.setMarkersList(markersList)
    }

    private fun setData() = viewModel.getMarkersFromDB()

    private fun deleteAllMarkers() = viewModel.deleteAllMarkersFromDB()

    private val onListItemClickListener: OnListItemClickListener =
        object : OnListItemClickListener {
            override fun onItemClick(dataModel: RoomMarker) {
                ChangeInfoFragment(dataModel).show(parentFragmentManager, "CHANGE_INFO")
            }
        }

    private val onDeleteItemClickListener: OnDeleteItemClickListener =
        object : OnDeleteItemClickListener {
            override fun onDeleteItem(dataModel: RoomMarker) = viewModel.deleteItemFromDB(dataModel)
        }

    companion object{
        fun newInstance(): Fragment = MarkersFragment()
        private const val REQUEST_KEY = "request key"
        private const val REQUEST = "reload data?"
        private const val POSITIVE = "reload!"
    }
}