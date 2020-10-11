package com.filipesanders.desafio_south_systhem.ui.scenes.eventDetails

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.filipesanders.desafio_south_systhem.R
import com.filipesanders.desafio_south_systhem.services.ServiceResponse
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.launch
import java.lang.Exception

class EventDetailsFragment : Fragment(), OnMapReadyCallback,
    GoogleMap.OnMarkerClickListener {

    private val viewModel: EventDetailsViewModel by viewModels()

    private var maps: GoogleMap? = null

    private val args: EventDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_event_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment =
            childFragmentManager.findFragmentById(R.id.fragmentMap) as SupportMapFragment
        mapFragment.getMapAsync(this)

        viewModel.getEventDetails(args?.eventId).observe(viewLifecycleOwner, Observer {

            if (it is ServiceResponse.Success) {

            }

        })

    }

    override fun onMapReady(googleMap: GoogleMap?) {

        var latitude = args?.latitude?.toDoubleOrNull()
        var longitude = args?.latitude?.toDoubleOrNull()

        if (googleMap != null && latitude != null && longitude != null) {

            maps = googleMap

            var local = LatLng(latitude, longitude)

            maps?.addMarker(MarkerOptions().position(local).title("Evento"))
            maps?.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    local,
                    15.0f
                )
            )

            maps?.getUiSettings()?.setZoomControlsEnabled(true)
            maps?.setOnMarkerClickListener(this)
            maps?.mapType = GoogleMap.MAP_TYPE_TERRAIN

        } else {
            //Adicionar falha a localização
        }
    }

    override fun onMarkerClick(p0: Marker?) = false

}