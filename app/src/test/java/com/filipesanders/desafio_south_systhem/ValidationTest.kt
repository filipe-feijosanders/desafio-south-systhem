package com.filipesanders.desafio_south_systhem

import com.filipesanders.desafio_south_systhem.businessLogic.validation.CheckinValidation
import org.junit.Assert
import org.junit.Test

class ValidationTest {

    @Test
    fun NameOk() {
        val validator = CheckinValidation()
        val name = "Filipe"
        Assert.assertTrue(validator.isNameNotEmpty(name))
    }

    @Test
    fun NameNotOk() {
        val validator = CheckinValidation()
        val name = ""
        Assert.assertTrue(!validator.isNameNotEmpty(name))
    }
}