package com.example.mapandmarkers.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.mapandmarkers.database.RoomMarker
import com.example.mapandmarkers.utilities.PermissionUtils.isPermissionGranted
import com.example.mapandmarkers.utilities.PermissionUtils.requestPermission
import com.example.mapandmarkers.databinding.MapFragmentBinding
import com.example.mapandmarkers.viewmodel.MapViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class MapFragment(private val activity: AppCompatActivity)
    : BaseFragment<MapFragmentBinding>(MapFragmentBinding::inflate), OnMapReadyCallback,
    ActivityCompat.OnRequestPermissionsResultCallback, GoogleMap.OnMarkerClickListener{

    private var permissionDenied = false
    private val omsk = LatLng(54.989347, 73.368221)
    private lateinit var googleMap: GoogleMap
    private val viewModel: MapViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getMarkersFromDB()
        binding.map.onCreate(savedInstanceState)
        binding.map.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
        with(googleMap){
            mapType = GoogleMap.MAP_TYPE_TERRAIN
            uiSettings.isCompassEnabled = true
            uiSettings.isIndoorLevelPickerEnabled = true
            setOnMarkerClickListener(this@MapFragment)

            addMarker(MarkerOptions().position(omsk).title("Omsk"))
            moveCamera(CameraUpdateFactory.zoomTo(10F))
            moveCamera(CameraUpdateFactory.newLatLng(omsk))
        }

        viewModel.mutableLiveData.observe(viewLifecycleOwner) { renderData(it) }
        setListeners()
        enableMyLocation()
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        googleMap.moveCamera(CameraUpdateFactory.zoomTo(10F))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(marker.position))
        return false
    }

    private fun renderData(markersList: List<RoomMarker>) {
        for (i in markersList.indices) {
            val marker = LatLng(markersList[i].lat, markersList[i].lng)
            googleMap.addMarker(MarkerOptions().position(marker).title(markersList[i].title))
        }
    }

    private fun setListeners() {
        with(binding){
            normal.setOnClickListener { googleMap.mapType = GoogleMap.MAP_TYPE_NORMAL }
            hybrid.setOnClickListener { googleMap.mapType = GoogleMap.MAP_TYPE_HYBRID }
            satellite.setOnClickListener { googleMap.mapType = GoogleMap.MAP_TYPE_SATELLITE }
            traffic.setOnClickListener { googleMap.isTrafficEnabled = binding.traffic.isChecked }
            clearButton.setOnClickListener { googleMap.clear() }
            menuButton.setOnClickListener {
                if (binding.menuLayout.isVisible) binding.menuLayout.visibility = View.GONE
                else binding.menuLayout.visibility = View.VISIBLE
                binding.menuButton.text = if (binding.menuLayout.isVisible) "HIDE" else "MENU"
            }
        }

        googleMap.setOnMapLongClickListener { position ->
            Toast.makeText(context, "Marker at ${position.latitude}, ${position.longitude} was added", Toast.LENGTH_SHORT).show()
            googleMap.addMarker(MarkerOptions().position(position).title(position.toString()))
            viewModel.addMarkerToDB(position)
        }
    }


    @SuppressLint("MissingPermission")
    private fun enableMyLocation() {
        if (!::googleMap.isInitialized) return
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            googleMap.isMyLocationEnabled = true
            googleMap.myLocation
        } else {
            // Permission to access the location is missing. Show rationale and request permission
            requestPermission(activity, LOCATION_PERMISSION_REQUEST_CODE,
                Manifest.permission.ACCESS_FINE_LOCATION, true
            )
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            return
        }
        if (isPermissionGranted(permissions, grantResults, Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation()
        } else {
            // Permission was denied. Display an error message
            // Display the missing permission error dialog when the fragments resume.
            permissionDenied = true
        }
    }

    override fun onResume() {
        super.onResume()
        binding.map.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.map.onDestroy()
    }

    override fun onPause() {
        super.onPause()
        binding.map.onPause()
    }

    companion object{
        fun newInstance(activity: AppCompatActivity): Fragment = MapFragment(activity)
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

}