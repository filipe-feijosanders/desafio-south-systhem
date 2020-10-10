package com.filipesanders.desafio_south_systhem.services.rest.checkin

import com.filipesanders.desafio_south_systhem.businessLogic.models.CheckinRequest
import com.filipesanders.desafio_south_systhem.services.ServiceResponse

interface CheckinServiceInterface {

    suspend fun checkin(checkinResquest: CheckinRequest): ServiceResponse<Unit>

}