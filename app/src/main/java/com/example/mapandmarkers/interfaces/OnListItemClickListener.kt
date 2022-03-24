package com.example.mapandmarkers.interfaces

import com.example.mapandmarkers.database.RoomMarker

interface OnListItemClickListener {
    fun onItemClick(dataModel: RoomMarker)
}