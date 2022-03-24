package com.example.mapandmarkers.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import com.google.gson.annotations.SerializedName

@Entity(tableName = "markers")
@Parcelize
data class RoomMarker (
    @PrimaryKey
    @ColumnInfo(name = "title")
    @field:SerializedName("title") var title: String,
    @ColumnInfo(name = "lat")
    @field:SerializedName("lat") var lat: Double,
    @ColumnInfo(name = "lng")
    @field:SerializedName("lng") var lng: Double,
    @ColumnInfo(name = "description")
    @field:SerializedName("description") var description: String? = "",
): Parcelable