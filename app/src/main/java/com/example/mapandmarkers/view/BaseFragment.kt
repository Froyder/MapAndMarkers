package com.example.mapandmarkers.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewbinding.ViewBinding
import com.example.mapandmarkers.interfaces.IMarkersManager
import com.example.mapandmarkers.viewmodel.MapViewModel
import kotlinx.coroutines.*
import org.koin.android.ext.android.inject

open class BaseFragment<T : ViewBinding>(private val inflateMethod : (LayoutInflater, ViewGroup?, Boolean) -> T) : Fragment(){

    private var _binding : T? = null
    val markersManager: IMarkersManager by inject()

    val binding : T get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = inflateMethod.invoke(inflater, container, false)
        return binding.root
    }

    private fun handleError(error: Throwable) {
        Log.e("TAG", "BaseFragment talks! An error occurred: $error")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}