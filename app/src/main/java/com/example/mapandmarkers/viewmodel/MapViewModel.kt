package com.example.mapandmarkers.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mapandmarkers.database.RoomMarker
import com.example.mapandmarkers.interfaces.IMarkersManager
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MapViewModel: ViewModel(), KoinComponent {

    private val markersManager: IMarkersManager by inject()
    private val _mutableLiveData = MutableLiveData<List<RoomMarker>>()
    val mutableLiveData: LiveData<List<RoomMarker>>
        get() = _mutableLiveData

    private val viewModelScope = CoroutineScope(
        Dispatchers.IO
                + SupervisorJob()
                + CoroutineExceptionHandler { _, throwable ->
            handleError(throwable)
        })

    private var job: Job? = null

    fun getMarkersFromDB() {
        job?.cancel()
        job = viewModelScope.launch {
            _mutableLiveData.postValue(markersManager.getAllAddedMarkers())
        }
    }

    fun addMarkerToDB(position: LatLng){
        job?.cancel()
        job = viewModelScope.launch {
            markersManager.addNewMarker(RoomMarker(position.toString(), position.latitude, position.longitude))
        }
    }

    private fun handleError(error: Throwable) {
        Log.e("TAG", "BaseFragment talks! An error occurred: $error")
    }

    override fun onCleared() {
        job?.cancel()
        viewModelScope.cancel()
        super.onCleared()
    }

}