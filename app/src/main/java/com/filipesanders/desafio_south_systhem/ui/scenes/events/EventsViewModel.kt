package com.filipesanders.desafio_south_systhem.ui.scenes.events

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.filipesanders.desafio_south_systhem.businessLogic.models.EventsResponse
import com.filipesanders.desafio_south_systhem.services.ServiceResponse
import com.filipesanders.desafio_south_systhem.services.rest.events.EventsServiceInterface
import com.filipesanders.desafio_south_systhem.services.rest.events.EventsServiceRest
import com.filipesanders.desafio_south_systhem.utils.SingleLiveEvent
import com.filipesanders.desafio_south_systhem.utils.onValueChange
import kotlinx.coroutines.Dispatchers

class EventsViewModel(
    private val eventsService: EventsServiceInterface = EventsServiceRest()
) : ViewModel() {

    private val _isEventResponseSucess = SingleLiveEvent<Boolean>()
    val isEventResponseSucess: SingleLiveEvent<Boolean>
        get() = _isEventResponseSucess

    private val _isLoading = SingleLiveEvent<Boolean>()
    val isLoading: SingleLiveEvent<Boolean>
        get() = _isLoading

    fun getEvents(): LiveData<ServiceResponse<ArrayList<EventsResponse>>> {

        _isLoading.value = true

        return liveData(Dispatchers.IO) {
            val res = eventsService.events()
            emit(res)
        }.onValueChange {
            _isLoading.value = false
        }

    }

    fun setIsEventResponseSucess(value: Boolean) {
        _isEventResponseSucess.value = value
    }

}