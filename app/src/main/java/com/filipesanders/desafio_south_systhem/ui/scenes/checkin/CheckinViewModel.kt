package com.filipesanders.desafio_south_systhem.ui.scenes.checkin

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.filipesanders.desafio_south_systhem.businessLogic.models.CheckinRequest
import com.filipesanders.desafio_south_systhem.businessLogic.validation.CheckinValidation
import com.filipesanders.desafio_south_systhem.businessLogic.validation.CheckinValidationInterface
import com.filipesanders.desafio_south_systhem.services.ServiceResponse
import com.filipesanders.desafio_south_systhem.services.rest.checkin.CheckinServiceInterface
import com.filipesanders.desafio_south_systhem.services.rest.checkin.CheckinServiceRest
import com.filipesanders.desafio_south_systhem.utils.SingleLiveEvent
import kotlinx.coroutines.Dispatchers

class CheckinViewModel(
    private val checkinService: CheckinServiceInterface = CheckinServiceRest(),
    private val checkinValidator: CheckinValidationInterface = CheckinValidation()
) : ViewModel() {

    var errorMessage: String = ""

    fun doCheckin(
        eventId: String?,
        name: String?,
        email: String?
    ): LiveData<ServiceResponse<Unit>> {
        return liveData(Dispatchers.IO) {
            val res = checkinService.checkin(
                CheckinRequest(eventId = eventId, name = name, email = email)
            )
            emit(res)
        }
    }

    fun isFieldsValid(
        name: String?,
        email: String?
    ): Boolean {

        if (!checkinValidator.isNameNotEmpty(name)) {
            errorMessage = "Nome inválido"
            return false
        }

        if (!checkinValidator.isEmailValid(email)) {
            errorMessage = "E-mail inválido"
            return false
        }

        return true
    }

}