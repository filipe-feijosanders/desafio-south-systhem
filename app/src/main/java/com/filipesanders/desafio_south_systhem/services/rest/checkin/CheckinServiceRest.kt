package com.filipesanders.desafio_south_systhem.services.rest.checkin

import com.filipesanders.desafio_south_systhem.businessLogic.models.CheckinRequest
import com.filipesanders.desafio_south_systhem.services.RetrofitServices
import com.filipesanders.desafio_south_systhem.services.ServiceResponse
import com.filipesanders.desafio_south_systhem.services.safeApiCall
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class CheckinServiceRest(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : CheckinServiceInterface {

    private val checkinService: CheckinServiceApi = RetrofitServices.getService()

    override suspend fun checkin(checkinResquest: CheckinRequest): ServiceResponse<Unit> {
        return safeApiCall(dispatcher) {
            checkinService.checkin(checkinResquest)
        }
    }
}