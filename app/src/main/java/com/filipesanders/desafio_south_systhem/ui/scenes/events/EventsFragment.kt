package com.filipesanders.desafio_south_systhem.ui.scenes.events

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.filipesanders.desafio_south_systhem.R
import com.filipesanders.desafio_south_systhem.businessLogic.models.EventsResponse
import com.filipesanders.desafio_south_systhem.services.ServiceResponse
import com.filipesanders.desafio_south_systhem.ui.base.BaseFragment
import kotlinx.android.synthetic.main.events_home.*

class EventsFragment : BaseFragment() {

    override val showButtonBack: Boolean = false

    override val showShare: Boolean = false

    override val title: String = "Eventos"

    private val viewModel: EventsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.events_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        eventsRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = EventsAdapter()
        }

        viewModel.isEventResponseSucess.observe(viewLifecycleOwner, Observer {

            if (it != null) {
                if (it) {
                    eventsError.visibility = View.GONE
                    eventsRecycler.visibility = View.VISIBLE
                } else {
                    eventsError.visibility = View.GONE
                    eventsRecycler.visibility = View.VISIBLE
                }

            }

        })

        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                eventsProgress.visibility = if (it) View.VISIBLE else View.GONE
            }
        })

        fetchEvents()
    }

    private fun fetchEvents() {
        viewModel.getEvents().observe(viewLifecycleOwner, Observer {

            onEventsResponse(it)

        })
    }

    private fun onEventsResponse(response: ServiceResponse<ArrayList<EventsResponse>>) {
        when (response) {
            is ServiceResponse.Success -> {
                onSucess(response)
            }
            is ServiceResponse.Error.GenericError -> {
                onError()
            }
            is ServiceResponse.Error.NetworkError -> {
                onNetworkError()
            }
            else -> {
                onUnknownError()
            }
        }
    }

    private fun onSucess(response: ServiceResponse.Success<ArrayList<EventsResponse>>) {
        viewModel.setIsEventResponseSucess(true)
        (eventsRecycler.adapter as EventsAdapter).submitList(response.value ?: ArrayList())
    }

    private fun onNetworkError() {
        viewModel.setIsEventResponseSucess(false)
        eventsError.text = getString(R.string.network_error)
    }

    private fun onUnknownError() {
        viewModel.setIsEventResponseSucess(false)
        eventsError.text = getString(R.string.unknown_error)
    }

    private fun onError() {
        viewModel.setIsEventResponseSucess(false)
        eventsError.text = getString(R.string.events_error)
    }

    /*
Com uma documentação da API, podendo saber os possíveis valores de erro, seria possível ainda
adicionar um comportamento específico para cada código de erro ou mensagem de erro.
ex:
... is ServiceResponse.Error.GenericError -> {
            when(response.statusCode){
                401 -> {

                }
                else -> {

                }
            }
        }

 ... is ServiceResponse.Error.GenericError -> {
            when(response.message){
                "message1" -> {

                }
                else -> {

                }
            }
        }
 */

}