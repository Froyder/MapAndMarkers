package com.example.mapandmarkers.interfaces

import com.example.mapandmarkers.database.RoomMarker

interface OnDeleteItemClickListener{
    fun onDeleteItem(dataModel: RoomMarker)
}