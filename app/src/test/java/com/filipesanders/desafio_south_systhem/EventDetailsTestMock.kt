package com.filipesanders.desafio_south_systhem

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.filipesanders.desafio_south_systhem.businessLogic.models.EventDetailsResponse
import com.filipesanders.desafio_south_systhem.services.ServiceResponse
import com.filipesanders.desafio_south_systhem.services.rest.eventDetails.EventDetailsServiceRest
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

//Embora tenha já feito nos testes istrumentais, adicionei essa classe para exemplificar um teste usando mock

@RunWith(JUnit4::class)
class EventDetailsTestMock {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var service: EventDetailsServiceRest

    @Before
    fun before() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun eventDetailsOk() {
        GlobalScope.launch {

            Mockito.`when`(
                service.eventDetails(ArgumentMatchers.anyString())
            ).thenAnswer {
                return@thenAnswer ServiceResponse.Success<EventDetailsResponse>(null)
            }

            val result =
                service.eventDetails(ArgumentMatchers.anyString())

            Assert.assertTrue(result is ServiceResponse.Success)
        }
    }

}