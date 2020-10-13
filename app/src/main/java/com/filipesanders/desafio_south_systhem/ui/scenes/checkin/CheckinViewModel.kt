package com.filipesanders.desafio_south_systhem.ui.scenes.checkin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.filipesanders.desafio_south_systhem.businessLogic.models.CheckinRequest
import com.filipesanders.desafio_south_systhem.businessLogic.validation.CheckinValidation
import com.filipesanders.desafio_south_systhem.businessLogic.validation.CheckinValidationInterface
import com.filipesanders.desafio_south_systhem.services.ServiceResponse
import com.filipesanders.desafio_south_systhem.services.rest.checkin.CheckinServiceInterface
import com.filipesanders.desafio_south_systhem.services.rest.checkin.CheckinServiceRest
import com.filipesanders.desafio_south_systhem.utils.SingleLiveEvent
import com.filipesanders.desafio_south_systhem.utils.onValueChange
import kotlinx.coroutines.Dispatchers

class CheckinViewModel(
    private val checkinService: CheckinServiceInterface = CheckinServiceRest(),
    private val checkinValidator: CheckinValidationInterface = CheckinValidation()
) : ViewModel() {

    data class Checkin(
        var name: String? = null,
        var email: String? = null
    )

    private val _checkin: MutableLiveData<Checkin> = MutableLiveData(
        Checkin()
    )

    val checkin: LiveData<Checkin>
        get() = _checkin

    var errorMessage: String = ""

    private val _isLoading = SingleLiveEvent<Boolean>()
    val isLoading: SingleLiveEvent<Boolean>
        get() = _isLoading

    fun doCheckin(
        eventId: String?
    ): LiveData<ServiceResponse<Unit>> {

        _isLoading.value = true

        return liveData(Dispatchers.IO) {
            val res = checkinService.checkin(
                CheckinRequest(
                    eventId = eventId,
                    name = _checkin.value?.name,
                    email = _checkin.value?.email
                )
            )
            emit(res)
        }.onValueChange {
            _isLoading.value = false
        }
    }

    fun isFieldsValid(
        name: String?,
        email: String?
    ): Boolean {

        _isLoading.value = true

        _checkin.value?.name = name
        _checkin.value?.email = email

        if (!checkinValidator.isNameNotEmpty(_checkin.value?.name)) {
            errorMessage = "Nome inválido"
            _isLoading.value = false
            return false
        }

        if (!checkinValidator.isEmailValid(_checkin.value?.email)) {
            errorMessage = "E-mail inválido"
            _isLoading.value = false
            return false
        }

        _isLoading.value = false

        return true
    }

}