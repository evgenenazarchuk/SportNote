package com.evgenynaz.sportnote.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.IntentSender
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.SyncStateContract.Helpers.update
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.evgenynaz.sportnote.databinding.ActivityMapsBinding
import com.evgenynaz.sportnote.weather.WeatherViewModel
import com.evgenynaz.sportnote.weather.data.WeatherResult
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.Task
import org.koin.androidx.viewmodel.ext.android.viewModel


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private val viewModel: WeatherViewModel by viewModel()
    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private lateinit var locationRequest: LocationRequest

    private var myLocation: LatLng? = null
        set(value) {
            field = value
            Toast.makeText(this, "My location was updated", Toast.LENGTH_SHORT).show()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.getMapAsync(this)


        binding.btnMap.setOnClickListener {
            goToMe()
        }
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                myLocation = LatLng(p0.lastLocation.latitude, p0.lastLocation.longitude)
            }
        }


        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_NETWORK_STATE
                ), 2
            )
        } else {
            locationWizardry()
        }

        //wether
        viewModel.liveData.observe(this, {
            update(it)
        })
        binding.wetherLl.setOnClickListener{
            viewModel.getResultWeather()
        }


    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        locationWizardry()
    }

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.mapView.onDestroy()
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isZoomControlsEnabled = true
    }

    private fun goToMe() {
        mMap.addMarker(
            MarkerOptions().position(LatLng(53.963943663742874, 27.591979179046493)).icon(
                BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_BLUE),
            )
                .title("Спортивная площадка")
        )
        mMap.addMarker(
            MarkerOptions().position(LatLng(53.95768197472207, 27.58408275575062)).icon(
                BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_AZURE),
            )
                .title("Спортивная площадка")
        )
        mMap.addMarker(
            MarkerOptions().position(LatLng(53.93767866934014, 27.57069316842285)).icon(
                BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_GREEN),
            )
                .title("Workout площадка")
        )
        mMap.addMarker(
            MarkerOptions().position(LatLng(53.92453996828941, 27.557303581095073)).icon(
                BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_VIOLET),
            )
                .title("WorkOut Place Спорт-площадка")
        )
        mMap.addMarker(
            MarkerOptions().position(LatLng(53.857366986631774, 27.462203178503962)).icon(
                BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_VIOLET),
            )
                .title("Workout-площадка")
        )
        if (::mMap.isInitialized && myLocation != null) {

            mMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(myLocation!!, 17f),
                object : GoogleMap.CancelableCallback {
                    override fun onFinish() {
                        val minsk = LatLng(53.902334, 27.561879)
                        mMap.addMarker(MarkerOptions().position(minsk).title("Hello from Minsk"))
                    }

                    override fun onCancel() = Unit

                })

        } else {
            Toast.makeText(this, "Not initialized", Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("MissingPermission")
    private fun locationWizardry() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        //Initially, get last known location. We can refine this estimate later
        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                myLocation = LatLng(location.latitude, location.longitude)
            }
        }

        //now for receiving constant location updates:
        createLocRequest()
        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)

        val client = LocationServices.getSettingsClient(this)
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())
        task.addOnFailureListener { e ->
            if (e is ResolvableApiException) {
                try {
                    e.startResolutionForResult(this, 500)
                } catch (sendEx: IntentSender.SendIntentException) {
                }
            }
        }

        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null)
        //actually start listening for updates: See on Resume(). It's done there so that conveniently we can stop listening in onPause
    }

    private fun createLocRequest() {
        locationRequest = LocationRequest.create()
        locationRequest.interval = 10000 //time in ms; every ~10 seconds
        locationRequest.fastestInterval = 5000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }
    private fun update(resultWeather: WeatherResult) {

        binding.tvCity.text = resultWeather?.name
        binding.tvTemp.text = "${resultWeather?.temp?.toInt()} °C"
            //    binding.tvCloud.text = resultWeather?.description
            ?.replace("[", "")
            ?.replace("]", "")
        val url = "https://openweathermap.org/img/wn/${resultWeather?.iconId}@2x.png"
            .replace("[", "")
            .replace("]", "")

        Glide
            .with(binding.root)
            .load(url)
            //.placeholder(R.drawable.img)
            .into(binding.iconWeather)
    }
}

