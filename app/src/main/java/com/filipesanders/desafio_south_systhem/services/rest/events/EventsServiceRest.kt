package com.filipesanders.desafio_south_systhem.services.rest.events

import com.filipesanders.desafio_south_systhem.businessLogic.models.EventsResponse
import com.filipesanders.desafio_south_systhem.services.RetrofitServices
import com.filipesanders.desafio_south_systhem.services.ServiceResponse
import com.filipesanders.desafio_south_systhem.services.safeApiCall
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class EventsServiceRest(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : EventsServiceInterface {

    private val eventsService: EventsServiceApi = RetrofitServices.getService()

    override suspend fun events(): ServiceResponse<ArrayList<EventsResponse>> {
        return safeApiCall(dispatcher) {
            eventsService.events()
        }
    }

}