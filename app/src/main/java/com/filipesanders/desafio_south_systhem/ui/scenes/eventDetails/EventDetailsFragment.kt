package com.filipesanders.desafio_south_systhem.ui.scenes.eventDetails

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ShareCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.filipesanders.desafio_south_systhem.R
import com.filipesanders.desafio_south_systhem.businessLogic.models.EventDetailsResponse
import com.filipesanders.desafio_south_systhem.businessLogic.models.EventsResponse
import com.filipesanders.desafio_south_systhem.services.ServiceResponse
import com.filipesanders.desafio_south_systhem.ui.base.BaseFragment
import com.filipesanders.desafio_south_systhem.ui.scenes.events.EventsAdapter
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.events_home.*
import kotlinx.android.synthetic.main.events_list_cell.view.*
import kotlinx.android.synthetic.main.fragment_event_details.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.coroutines.launch
import java.lang.Exception

class EventDetailsFragment : BaseFragment(), OnMapReadyCallback,
    GoogleMap.OnMarkerClickListener {

    override val showButtonBack: Boolean = true

    override val showShare: Boolean = true

    override val title: String = "Detalhes do evento"

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

        eventPeople.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = PeopleAdapter()
        }

        viewModel.isEventResponseSucess.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                if (it) {
                    eventContainer.visibility = View.VISIBLE
                    eventError.visibility = View.GONE
                } else {
                    eventContainer.visibility = View.GONE
                    eventError.visibility = View.VISIBLE
                }
            }
        })

        fetchEvent()
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

    private fun fetchEvent() {
        viewModel.getEventDetails(args?.eventId).observe(viewLifecycleOwner, Observer {

            onEventResponse(it)

        })
    }

    private fun onEventResponse(response: ServiceResponse<EventDetailsResponse>) {
        when (response) {
            is ServiceResponse.Success -> {
                onSucess(response)
            }
            is ServiceResponse.Error.GenericError -> {
                onError()
            }
            is ServiceResponse.Error.NetworkError -> {
                onNetworkError()
            }
            else -> {
                onUnknownError()
            }
        }
    }

    private fun onSucess(response: ServiceResponse.Success<EventDetailsResponse>) {
        viewModel.setIsEventResponseSucess(true)
        eventTitle.text = response.value?.title
        eventPrice.text = "R$"+response.value?.price
        eventDate.text = response.value?.date

        eventDescription.text = response.value?.description

        Glide.with(eventImage)
            .load(response?.value?.image)
            .error(R.drawable.image_error)
            .transform(
                MultiTransformation(
                    CenterCrop()
                )
            )
            .into(eventImage)

        (eventPeople.adapter as PeopleAdapter).submitList(response?.value?.people ?: ArrayList())

        eventCheckin.setOnClickListener {
            findNavController().navigate(
                EventDetailsFragmentDirections.actionEventDetailsFragmentToChekinFragment(
                    args?.eventId
                )
            )
        }

        toolbarShare.setOnClickListener {
            shareEvent(
                response.value?.title.toString(),
                response.value?.title.toString() + "\n \n" + response.value?.description.toString()
            )
        }
    }

    private fun onNetworkError() {
        viewModel.setIsEventResponseSucess(false)
        eventError.text = getString(R.string.network_error)
    }

    private fun onUnknownError() {
        viewModel.setIsEventResponseSucess(false)
        eventError.text = getString(R.string.unknown_error)
    }

    private fun onError() {
        viewModel.setIsEventResponseSucess(false)
        eventError.text = getString(R.string.event_error)
    }

    private fun shareEvent(title: String, textToShare: String) {

        val shareIntent = ShareCompat.IntentBuilder.from(requireActivity())
            .setChooserTitle(title)
            .setType("text/plan")
            .setText(textToShare)
            .createChooserIntent()
        startActivity(shareIntent)
    }

}