package com.filipesanders.desafio_south_systhem.services.rest.checkin

import com.filipesanders.desafio_south_systhem.businessLogic.models.CheckinRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface CheckinServiceApi {

    @POST("checkin")
    suspend fun checkin(
        @Body checkinRequest: CheckinRequest
    )

}