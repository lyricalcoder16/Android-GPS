package com.charles.androidgps

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity(), LocationListener {
    lateinit var txtLat: TextView
    lateinit var txtLong: TextView
    lateinit var txtSpd: TextView
    lateinit var txtAlt: TextView

    private val locationPermissionCode = 342
    private lateinit var locationManager: LocationManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        txtLat = findViewById(R.id.textViewLat)
        txtLong = findViewById(R.id.textViewLong)
        txtSpd = findViewById(R.id.textViewSpd)
        txtAlt = findViewById(R.id.textViewAlt)
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        //checking if app has been granted permission to check fine location
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), locationPermissionCode)
        }
        var gpsStatus  = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        if (!gpsStatus){
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(intent)
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,5f,this)
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == locationPermissionCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun xyz(){}
    override fun onLocationChanged(p0: Location) {
        txtLat.text = "Lat ${p0.latitude}"
        txtLong.text = "Long ${p0.longitude}"
        txtSpd.text = "Spd ${p0.speed}"
        txtAlt.text = "Alt ${p0.altitude}"
    }
}