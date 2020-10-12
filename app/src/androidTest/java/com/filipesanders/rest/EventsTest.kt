package com.filipesanders.rest

import com.filipesanders.desafio_south_systhem.services.ServiceResponse
import com.filipesanders.desafio_south_systhem.services.rest.events.EventsServiceRest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.withContext
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class EventsTest() {

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

    //Se a request recebesse parametros, seria possível enviar dados errados afim de forçar um erro e observar a captura desse erro
    @Test
    fun fetchEvents() = runBlocking {
        withContext(Dispatchers.Main) {
            val service = EventsServiceRest(Dispatchers.Main)
            val res = service.events()
            Assert.assertTrue(res is ServiceResponse.Success)
        }
    }

}