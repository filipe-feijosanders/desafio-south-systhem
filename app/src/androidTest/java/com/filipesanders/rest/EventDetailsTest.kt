package com.filipesanders.rest

import com.filipesanders.desafio_south_systhem.services.ServiceResponse
import com.filipesanders.desafio_south_systhem.services.rest.eventDetails.EventDetailsServiceRest
import com.filipesanders.desafio_south_systhem.services.rest.events.EventsServiceRest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.withContext
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import kotlin.random.Random

@ExperimentalCoroutinesApi
class EventDetailsTest() {

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

    //Cenário: Obtém os detalhes de um evento
    @Test
    fun eventDetailsOk() = runBlocking {
        withContext(Dispatchers.Main) {
            val eventsService = EventsServiceRest(Dispatchers.Main)
            val eventsRes = eventsService.events()
            if (eventsRes is ServiceResponse.Success && eventsRes.value?.size ?: 0 > 0) {
                val eventDetailsService = EventDetailsServiceRest(Dispatchers.Main)
                val res = eventDetailsService.eventDetails(eventsRes.value?.get(0)?.id)
                Assert.assertTrue(res is ServiceResponse.Success)
            } else {
                Assert.assertTrue(false)
            }
        }
    }

    //Cenário: Obtém os detalhes de um evento com id errado
    @Test
    fun eventDetailsNotOk() = runBlocking {
        withContext(Dispatchers.Main) {
            val service = EventDetailsServiceRest(Dispatchers.Main)
            val res = service.eventDetails(Random.nextInt().toString())
            Assert.assertTrue(res is ServiceResponse.Error)
        }
    }
}