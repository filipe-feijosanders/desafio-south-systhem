package com.filipesanders.rest

import com.filipesanders.desafio_south_systhem.businessLogic.models.CheckinRequest
import com.filipesanders.desafio_south_systhem.services.ServiceResponse
import com.filipesanders.desafio_south_systhem.services.rest.checkin.CheckinServiceRest
import com.filipesanders.desafio_south_systhem.services.rest.events.EventsServiceRest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class CheckinTest() {

    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun before() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun afeter() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    //Cenário: Faz check-in em um evento existente
    @Test
    fun checkinOk() = runBlocking {
        with(Dispatchers.Main) {
            val eventsService = EventsServiceRest(Dispatchers.Main)
            val eventsRes = eventsService.events()
            if (eventsRes is ServiceResponse.Success && eventsRes.value?.size ?: 0 > 0) {
                val checkinService = CheckinServiceRest(Dispatchers.Main)
                val body = CheckinRequest(
                    eventId = eventsRes.value?.get(0)?.id,
                    name = "teste",
                    email = "email@email.com"
                )
                val res = checkinService.checkin(body)
                Assert.assertTrue(res is ServiceResponse.Success)
            } else {
                Assert.assertTrue(false)
            }
        }
    }
}