package com.filipesanders.desafio_south_systhem.services.rest.eventDetails

import com.filipesanders.desafio_south_systhem.businessLogic.models.EventDetailsResponse
import com.filipesanders.desafio_south_systhem.services.RetrofitServices
import com.filipesanders.desafio_south_systhem.services.ServiceResponse
import com.filipesanders.desafio_south_systhem.services.safeApiCall
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class EventDetailsServiceRest(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : EventDetailsServiceInterface {

    private val eventDetailsService: EventDetailsServiceApi = RetrofitServices.getService()

    override suspend fun eventDetails(eventId: String?): ServiceResponse<EventDetailsResponse> {
        return safeApiCall(dispatcher) {
            eventDetailsService.eventDetails(eventId)
        }
    }
}