package com.example.mapandmarkers.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.mapandmarkers.database.RoomMarker
import com.example.mapandmarkers.interfaces.IMarkersManager
import kotlinx.coroutines.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ChangeInfoViewModel: ViewModel(), KoinComponent {

    private val markersManager: IMarkersManager by inject()
    private val viewModelScope = CoroutineScope(
        Dispatchers.IO
                + SupervisorJob()
                + CoroutineExceptionHandler { _, throwable ->
            handleError(throwable)
        })

    private var job: Job? = null

    fun changeMarkerDataInDB(marker: RoomMarker, title: String, description: String) {
        job?.cancel()
        job = viewModelScope.launch {
            markersManager.updateMarker(marker, title, description)
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