package com.example.mapandmarkers.database

import androidx.room.Delete
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MarkersDao {

    @Query("SELECT * FROM markers")
    fun getMarkersListFromDB(): List<RoomMarker>

    @Query("DELETE FROM markers")
    fun deleteAllMarkersFromDB()

    @Query("UPDATE markers SET title=:newTitle, description= :newDescription WHERE title=:oldTitle")
    fun updateMarker(oldTitle: String, newTitle: String, newDescription: String)

    @Delete
    fun deleteMarker(marker: RoomMarker)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMarker(marker: RoomMarker)
}