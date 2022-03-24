package com.example.mapandmarkers.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import com.example.mapandmarkers.R
import com.example.mapandmarkers.database.RoomMarker
import com.example.mapandmarkers.databinding.ChangeInfoFragmentBinding
import com.example.mapandmarkers.interfaces.IMarkersManager
import com.example.mapandmarkers.viewmodel.ChangeInfoViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.*
import org.koin.android.ext.android.inject

class ChangeInfoFragment(private val marker: RoomMarker)
    : BottomSheetDialogFragment() {

    private val viewModel: ChangeInfoViewModel by activityViewModels()
    private var _binding: ChangeInfoFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ChangeInfoFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding){
            cancelButton.setOnClickListener { dialog?.dismiss() }
            saveButton.setOnClickListener { saveData(editTitle.text.toString(), editDescription.text.toString()) }
        }
    }

    private fun saveData(title: String, description: String) {
        if (title!=""){
            viewModel.changeMarkerDataInDB(marker, title, description)
            setFragmentResult(REQUEST_KEY, bundleOf(REQUEST to POSITIVE))
            dialog?.dismiss()
        } else Toast.makeText(context, getString(R.string.title_changing_error), Toast.LENGTH_SHORT).show()
    }

    companion object{
        private const val REQUEST_KEY = "request key"
        private const val REQUEST = "reload data?"
        private const val POSITIVE = "reload!"
    }
}