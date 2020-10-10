package com.filipesanders.desafio_south_systhem.services.rest.eventDetails

import com.filipesanders.desafio_south_systhem.businessLogic.models.EventDetailsResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface EventDetailsServiceApi {

    @GET("events/{id}")
    suspend fun eventDetails(
        @Path("id") eventId: String?
    ): EventDetailsResponse

}