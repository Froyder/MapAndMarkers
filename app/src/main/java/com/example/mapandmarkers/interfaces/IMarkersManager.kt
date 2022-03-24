package com.example.mapandmarkers.interfaces

import com.example.mapandmarkers.database.RoomMarker

interface IMarkersManager {
    suspend fun addNewMarker(marker: RoomMarker)
    suspend fun updateMarker(marker: RoomMarker, newTitle: String, newDescription: String)
    suspend fun deleteMarker(marker: RoomMarker)
    suspend fun getAllAddedMarkers(): List<RoomMarker>
    suspend fun deleteAllMarkers()
}