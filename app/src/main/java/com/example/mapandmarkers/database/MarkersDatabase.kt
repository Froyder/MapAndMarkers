package com.example.mapandmarkers.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [RoomMarker::class], version = 1, exportSchema = true)
abstract class MarkersDatabase: RoomDatabase() {
    abstract fun markersDao(): MarkersDao
}