package com.filipesanders.desafio_south_systhem.ui.scenes.eventDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.filipesanders.desafio_south_systhem.businessLogic.models.EventDetailsResponse
import com.filipesanders.desafio_south_systhem.services.ServiceResponse
import com.filipesanders.desafio_south_systhem.services.rest.eventDetails.EventDetailsServiceInterface
import com.filipesanders.desafio_south_systhem.services.rest.eventDetails.EventDetailsServiceRest
import kotlinx.coroutines.Dispatchers

class EventDetailsViewModel(
    private val eventDetailsService: EventDetailsServiceInterface = EventDetailsServiceRest()
) : ViewModel() {

    fun getEventDetails(eventId: String?): LiveData<ServiceResponse<EventDetailsResponse>> {
        return liveData(Dispatchers.IO) {
            val res = eventDetailsService.eventDetails(eventId)
            emit(res)
        }
    }

}