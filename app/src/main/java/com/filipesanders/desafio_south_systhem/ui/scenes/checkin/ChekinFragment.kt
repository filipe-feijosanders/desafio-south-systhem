package com.filipesanders.desafio_south_systhem.ui.scenes.checkin

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


class ChekinFragment : Fragment() {

    private val viewModel: CheckinViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chekin, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.doCheckin().observe(viewLifecycleOwner, Observer {
            if (it is ServiceResponse.Success) {

            }
        })
    }
}