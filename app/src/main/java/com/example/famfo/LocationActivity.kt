package com.example.famfo

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import java.util.*

class LocationActivity : AppCompatActivity() {

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest

    private var PERMISSION_ID = 52

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)

        val getPos : Button = findViewById(R.id.getPos)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        getPos.setOnClickListener {
//            Log.d("check ", "Clicked")
            val loc = getLastLocation()
//            Log.d("loc", "loc = "+ loc)

        }

    }

    // Function to get the last location
    @SuppressLint("MissingPermission", "SetTextI18n")
    private fun getLastLocation() {
        val textVw : TextView = findViewById(R.id.locationtxt)
        if(CheckPermission()){
            if(isLocationEnabled()) {
                fusedLocationProviderClient.lastLocation.addOnCompleteListener {task ->
                    var location:Location? = task.result
                    if(location == null) {
                        getNewLocation()
                    }
                    else {
                        textVw.text = "Your Current Coordinates are :" +
                                "\nLattitude: " + location.latitude +
                                "\nLongitude: "+ location.longitude +
                                "\nYour City: " + getCityName(location.latitude, location.longitude) +
                                "\nYour Country: " +getCountryName(location.latitude, location.longitude)
                    }
                }
            }
            else {
                Toast.makeText(this, "Please Enable your Location Service", Toast.LENGTH_SHORT).show()
            }
        }
        else{
            RequestPermission()
        }
    }

    @SuppressLint("MissingPermission")
    private fun getNewLocation() {
        var locationRequest = com.google.android.gms.location.LocationRequest()
        locationRequest.priority = LocationRequest.QUALITY_HIGH_ACCURACY
        locationRequest.interval = 0
        locationRequest.fastestInterval = 0
        locationRequest.numUpdates = 1
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationProviderClient!!.requestLocationUpdates(
            locationRequest, locationCallback, Looper.myLooper()
        )

    }

    private val locationCallback = object : LocationCallback() {
        @SuppressLint("SetTextI18n")
        override fun onLocationResult(p0: LocationResult) {
//            super.onLocationResult(p0)
            val textVw : TextView? = findViewById(R.id.locationtxt)
            var lastLocation:Location? = p0.lastLocation
            textVw?.text = "Your Current Coordinates are :" +
                    "\nLattitude: " + lastLocation?.latitude +
                    "\nLongitude: "+ lastLocation?.longitude +
                    "\nYour City: " + getCityName(lastLocation!!.latitude, lastLocation.longitude) +
                    "\nYour Country: " +getCountryName(lastLocation.latitude, lastLocation.longitude)
        }
    }

    //First we need to create a function that will check the uses permission
    private fun CheckPermission():Boolean {
        if(
            ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
        ){
            return true
        }
        return false
    }

    //Now we need to create a function that will allow us to get user permissions
    private fun RequestPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION), PERMISSION_ID
        )
    }

    // Now we need a function that check if the location service of the device is enabled
    private fun isLocationEnabled():Boolean {
        var locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    // Function to get the city name
    private fun getCityName(lat: Double, long: Double): String {
        var cityName = ""
        var geoCoder = Geocoder(this, Locale.getDefault())
        var addressList : List<Address>? = geoCoder.getFromLocation(lat, long, 1)
        if(addressList != null && addressList.isNotEmpty()) {
            cityName = addressList[0].locality ?: ""
        }
        return cityName
    }

    private fun getCountryName(lat: Double, long: Double): String {
        var countryName = ""
        var geoCoder = Geocoder(this, Locale.getDefault())
        var addressList : List<Address>? = geoCoder.getFromLocation(lat, long, 1)
        if(addressList != null && addressList.isNotEmpty()) {
            countryName = addressList[0].countryName ?: ""
        }
        return countryName
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        // this is a built in function that check the permission results
        (super.onRequestPermissionsResult(requestCode, permissions, grantResults))
        if(requestCode == PERMISSION_ID) {
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("Debug: ", "You have the Permissions")
            }
        }
    }
}