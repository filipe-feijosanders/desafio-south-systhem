package com.filipesanders.desafio_south_systhem.ui.scenes.events

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.filipesanders.desafio_south_systhem.R
import com.filipesanders.desafio_south_systhem.services.ServiceResponse

class EventsFragment : Fragment() {

    private val viewModel: EventsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.events_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getEvents().observe(viewLifecycleOwner, Observer {

            if (it is ServiceResponse.Success) {
                Log.e("events", "${it.value}")
            }

        })
    }

}