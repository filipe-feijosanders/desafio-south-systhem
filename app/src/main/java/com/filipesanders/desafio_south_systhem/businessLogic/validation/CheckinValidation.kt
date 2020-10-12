package com.filipesanders.desafio_south_systhem.businessLogic.validation

import android.util.Patterns

interface CheckinValidationInterface {

    fun isNameNotEmpty(name: String?): Boolean

    fun isEmailValid(email: String?): Boolean
}

class CheckinValidation() : CheckinValidationInterface {

    override fun isNameNotEmpty(name: String?): Boolean = !name.isNullOrEmpty()

    override fun isEmailValid(email: String?): Boolean {
        return email?.isNotBlank() == true && Patterns.EMAIL_ADDRESS.matcher(email)
            .matches()
    }
}