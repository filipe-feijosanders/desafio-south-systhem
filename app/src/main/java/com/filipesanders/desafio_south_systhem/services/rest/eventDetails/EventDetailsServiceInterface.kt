package com.filipesanders.desafio_south_systhem.services.rest.eventDetails

import com.filipesanders.desafio_south_systhem.businessLogic.models.EventDetailsResponse
import com.filipesanders.desafio_south_systhem.services.ServiceResponse

interface EventDetailsServiceInterface {

    suspend fun eventDetails(eventId: String?): ServiceResponse<EventDetailsResponse>

}