package com.example.test_app

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.util.jar.Manifest

class TestMapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient : FusedLocationProviderClient
    private lateinit var myLatLng : LatLng

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mMap = googleMap
        myLatLng = LatLng(0.0, 0.0)

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        val koeln = LatLng(50.937719143309074, 6.965257231165387)
        val cameraPos = CameraPosition.builder()
            .target(koeln)
            .zoom(10.0f)
            .build()
        mMap.addMarker(MarkerOptions().position(koeln).title("Köln :D"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(koeln))
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPos))
        permissionCheck()
        getCurrentLocation()
        mMap.addMarker(MarkerOptions().position(myLatLng).title("Test :D"))

    }

    private fun permissionCheck(){
        Log.d("Test", "Permission Check")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    applicationContext,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(
                    applicationContext,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                Toast.makeText(applicationContext, "GEIL, GEHT :D", Toast.LENGTH_SHORT)
                    .show()//FIXME Text ändern!
                Log.d("Test", "Toast")
            } else {
                val builder = AlertDialog.Builder(this)
                builder.apply {
                    setMessage("App benötigt Positions Daten")
                    setTitle("Rechte benötigt")
                    setPositiveButton("OK") { dialog, which ->
                        ActivityCompat.requestPermissions(
                            this@TestMapsActivity,
                            arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                            PackageManager.PERMISSION_GRANTED
                        )
                    }
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation()/* : LatLng*/{
        //Log.d("Test", fusedLocationClient.lastLocation.result.latitude.toString())
        //Log.d("Test", fusedLocationClient.lastLocation.result.longitude.toString())
        //var myLatLng = LatLng(0.0, 0.0)
        //fusedLocationClient.lastLocation.addOnSuccessListener { location : Location -> myLatLng = LatLng(location.latitude, location.longitude) }
        //myLatLng = LatLng(fusedLocationClient.lastLocation.result.latitude, fusedLocationClient.lastLocation.result.longitude)
        //return myLatLng
        fusedLocationClient.lastLocation.addOnSuccessListener { location : Location -> onLocationReceived(location) }
    }

    private fun onLocationReceived(location: Location?){
        val locationText = location?.latitude.toString() + "|" + location?.longitude.toString()
        Log.d("Test", "Loc = $locationText")
        val lat = location?.latitude ?: 0.0
        val lng = location?.longitude ?: 0.0
        myLatLng = LatLng(lat, lng)
    }
}