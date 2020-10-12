package com.filipesanders.desafio_south_systhem.ui.scenes.checkin

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.filipesanders.desafio_south_systhem.R
import com.filipesanders.desafio_south_systhem.businessLogic.models.EventsResponse
import com.filipesanders.desafio_south_systhem.services.ServiceResponse
import com.filipesanders.desafio_south_systhem.ui.dialog.GenericDialogFragment
import com.filipesanders.desafio_south_systhem.ui.scenes.events.EventsAdapter
import kotlinx.android.synthetic.main.events_home.*
import kotlinx.android.synthetic.main.fragment_chekin.*

class ChekinFragment : Fragment() {

    private val viewModel: CheckinViewModel by viewModels()

    private val args: ChekinFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chekin, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        doCheckin.setOnClickListener {
            doCheckin()
        }
    }

    private fun doCheckin() {
        if (viewModel.isFieldsValid(
                checkinUserName.text.toString(),
                checkinUserEmail.text.toString()
            )
        ) {
            viewModel.doCheckin(
                args?.eventId,
                checkinUserName.text.toString(),
                checkinUserEmail.text.toString()
            ).observe(viewLifecycleOwner, Observer {
                onCheckinResponse(it)
            })
        } else {
            Toast.makeText(requireContext(), viewModel.errorMessage, Toast.LENGTH_SHORT).show()
        }
    }

    private fun onCheckinResponse(response: ServiceResponse<Unit>) {
        when (response) {
            is ServiceResponse.Success -> {
                onSucess()
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

    private fun onSucess() {
        GenericDialogFragment.newInstance("Sucesso", "Checkin com sucesso")
            .setPositiveButtonOption("Voltar") {
                findNavController().popBackStack()
            }
            .show(parentFragmentManager, "checkinSucess")
    }

    private fun onNetworkError() {

    }

    private fun onUnknownError() {

    }

    private fun onError() {

    }
}