package com.filipesanders.desafio_south_systhem.pagination

import androidx.lifecycle.MutableLiveData
import com.filipesanders.desafio_south_systhem.services.ServiceResponse
import java.util.*

abstract class PaginatedDataSource<T> : MutableLiveData<PaginatedDataSource.PaginatedData<T>>() {

    data class PaginatedData<T>(
        val items: List<T>? = null,
        val error: ServiceResponse<*>? = null,
        val isLastPage: Boolean = true,
        val isLoadingInitial: Boolean = false
    )

    var isRequestingData = MutableLiveData(false)

    private var reachedLastPage: Boolean = false

    protected abstract fun loadFromServer(
        offset: Int,
        onDataLoaded: (data: PaginatedData<T>) -> Unit
    )

    //TODO: Melhoria: o load initial deveria ter prioridade ao o load normal.
    // Talvez usar um backed live data ou algo assim.

    fun loadInitialItems(silentRefresh: Boolean = false) {

        if (isRequestingData.value == true)
            return

        isRequestingData.value = true

        if (!silentRefresh) {
            postValue(PaginatedData(isLoadingInitial = true))
        }

        loadFromServer(0) { response ->

            reachedLastPage = response.isLastPage

            val mergedList = response.items?.let {
                appendReceivedItems(it, null)
            }

            val newServiceResponse = response.copy(
                items = mergedList,
                error = if (!reachedLastPage) response.error else null
            )

            isRequestingData.value = false

            postValue(newServiceResponse)
        }
    }


    fun loadMoreItems() {

        if (isRequestingData.value == true || reachedLastPage)
            return

        isRequestingData.value = true

        loadFromServer(value?.items?.size ?: 0) { response ->

            reachedLastPage = response.isLastPage

            val mergedList = response.items?.let {
                appendReceivedItems(it, value?.items)
            }

            val newServiceResponse = response.copy(
                items = mergedList,
                error = if (!reachedLastPage) response.error else null
            )

            isRequestingData.value = false

            postValue(newServiceResponse)
        }
    }

    /**
     * Faz a juncao da lista recebida da paginacao com os resultados existentes.
     * Normalmente, faz apenas um addAll
     */
    protected open fun appendReceivedItems(
        receivedItems: List<T>,
        currentItems: List<T>?
    ): List<T> {
        return (currentItems ?: LinkedList()) + receivedItems
    }

}
