package com.filipesanders.desafio_south_systhem.services.rest.events

import com.filipesanders.desafio_south_systhem.businessLogic.models.EventsResponse
import com.filipesanders.desafio_south_systhem.services.ServiceResponse

interface EventsServiceInterface {

    suspend fun events(): ServiceResponse<ArrayList<EventsResponse>>

}