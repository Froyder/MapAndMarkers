package com.example.mapandmarkers.di

import androidx.room.Room
import com.example.mapandmarkers.database.MarkersDatabase
import com.example.mapandmarkers.interfaces.IMarkersManager
import com.example.mapandmarkers.utilities.MarkersManager
import com.example.mapandmarkers.viewmodel.ChangeInfoViewModel
import com.example.mapandmarkers.viewmodel.MapViewModel
import com.example.mapandmarkers.viewmodel.MarkersViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val application = module {
    single {
        Room.databaseBuilder(androidContext(), MarkersDatabase::class.java, "markers_database")
            .fallbackToDestructiveMigration()
            .build()
    }
    single <IMarkersManager> { MarkersManager() }
    viewModel { MapViewModel() }
    viewModel { MarkersViewModel() }
    viewModel { ChangeInfoViewModel() }
}