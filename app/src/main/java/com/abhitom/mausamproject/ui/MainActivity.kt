package com.abhitom.mausamproject.ui

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.abhitom.mausamproject.R
import com.abhitom.mausamproject.databinding.ActivityMainBinding
import com.abhitom.mausamproject.internal.ToastMaker
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance

const val LOCATION_REQUEST_CODE=1

class MainActivity : AppCompatActivity(), KodeinAware {

    override val kodein by closestKodein()
    private val fusedLocationProviderClient:FusedLocationProviderClient by instance()

    private val locationCallback = object : LocationCallback(){
        override fun onLocationResult(p0: LocationResult) {
            super.onLocationResult(p0)
        }
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController:NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)

        navController = Navigation.findNavController(this,R.id.nav_host_fragment)

        binding.bottomNav.setupWithNavController(navController)

        //NavigationUI.setupActionBarWithNavController(this,navController)

        requestLocationPermission()

        if (hasLocationPermission()){
            bindLocationManager()
        }else{
            requestLocationPermission()
        }
    }

    private fun bindLocationManager() {
        LifecycleBoundLocationManager(this,
        fusedLocationProviderClient,
        locationCallback)
    }

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),LOCATION_REQUEST_CODE)
    }

    private fun hasLocationPermission():Boolean{
        return ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)==PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
       if (requestCode== LOCATION_REQUEST_CODE){
           if (grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                bindLocationManager()
           }else{
               Toast.makeText(this,"TURN ON LOCATION!!",Toast.LENGTH_LONG).show()
           }
       }

    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController,null)
    }
}