package com.example.mapandmarkers.utilities

import com.example.mapandmarkers.database.MarkersDatabase
import com.example.mapandmarkers.database.RoomMarker
import com.example.mapandmarkers.interfaces.IMarkersManager
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MarkersManager: IMarkersManager, KoinComponent {

    private val db: MarkersDatabase by inject()

    override suspend fun addNewMarker(marker: RoomMarker) {
        db.markersDao().addMarker(marker)
    }

    override suspend fun updateMarker(marker: RoomMarker, newTitle: String, newDescription: String) {
        db.markersDao().updateMarker(marker.title, newTitle, newDescription)
    }

    override suspend fun deleteMarker(marker: RoomMarker) {
        db.markersDao().deleteMarker(marker)
    }

    override suspend fun getAllAddedMarkers(): List<RoomMarker> {
        return db.markersDao().getMarkersListFromDB()
    }

    override suspend fun deleteAllMarkers() {
        db.markersDao().deleteAllMarkersFromDB()
    }
}