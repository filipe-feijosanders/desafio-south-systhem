package com.filipesanders.desafio_south_systhem.services.rest.events

import com.filipesanders.desafio_south_systhem.businessLogic.models.EventsResponse
import retrofit2.http.GET

interface EventsServiceApi {

    @GET("events")
    suspend fun events(): ArrayList<EventsResponse>

}