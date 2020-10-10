package com.filipesanders.desafio_south_systhem.ui.scenes.checkin

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.filipesanders.desafio_south_systhem.businessLogic.models.CheckinRequest
import com.filipesanders.desafio_south_systhem.services.ServiceResponse
import com.filipesanders.desafio_south_systhem.services.rest.checkin.CheckinServiceInterface
import com.filipesanders.desafio_south_systhem.services.rest.checkin.CheckinServiceRest
import kotlinx.coroutines.Dispatchers

class CheckinViewModel(
    private val checkinService: CheckinServiceInterface = CheckinServiceRest()
) : ViewModel() {

    fun doCheckin(): LiveData<ServiceResponse<Unit>> {
        return liveData(Dispatchers.IO) {
            val res = checkinService.checkin(
                CheckinRequest("1", "filipe", "filipe.sandersf@gmail.com")
            )
            emit(res)
        }
    }

}