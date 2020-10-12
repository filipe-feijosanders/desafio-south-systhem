package com.filipesanders

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.filipesanders.desafio_south_systhem.businessLogic.validation.CheckinValidation
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ValidationTest {

    @Test
    fun emailOk() {
        val validation = CheckinValidation()
        val email = "test123@gmail.com"
        Assert.assertTrue(validation.isEmailValid(email))
    }

    @Test
    fun emailNotOk() {
        val validation = CheckinValidation()
        val email = "test123gmaicom"
        Assert.assertTrue(!validation.isEmailValid(email))
    }
}