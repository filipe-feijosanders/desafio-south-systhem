package com.filipesanders.desafio_south_systhem.ui.scenes.checkin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.filipesanders.desafio_south_systhem.R
import com.filipesanders.desafio_south_systhem.services.ServiceResponse
import com.filipesanders.desafio_south_systhem.ui.base.BaseFragment
import com.filipesanders.desafio_south_systhem.ui.dialog.GenericDialogFragment
import kotlinx.android.synthetic.main.fragment_chekin.*

class CheckinFragment : BaseFragment() {

    override val showButtonBack: Boolean = true

    override val showShare: Boolean = false

    override val title: String = "Check-in"

    private val viewModel: CheckinViewModel by viewModels()

    private val args: CheckinFragmentArgs by navArgs()

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

        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                checkinUserName.isEnabled = !it
                checkinUserEmail.isEnabled = !it
                checkinProgress.visibility = if (it) View.VISIBLE else View.GONE
            }
        })

        //Nesse app, defini para portrait a orientação pois não fiz um layout pensando nele virado.
        //Caso pudesse ser virado, se o usuário já tivesse saldo algum dado então esse dado seria resgatado da viewModel
        viewModel.checkin.observe(viewLifecycleOwner, Observer {
            checkinUserName.setText(it.name)
            checkinUserEmail.setText(it.email)
        })

    }

    private fun doCheckin() {
        if (viewModel.isFieldsValid(
                checkinUserName.text.toString(),
                checkinUserEmail.text.toString()
            )
        ) {
            viewModel.doCheckin(
                args?.eventId,
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
        GenericDialogFragment.newInstance(
            getString(R.string.checkin_sucess_title),
            getString(R.string.checkin_sucess)
        )
            .setPositiveButtonOption(getString(R.string.checkin_back)) {
                findNavController().popBackStack()
            }
            .show(parentFragmentManager, "checkinSucess")
    }

    private fun onNetworkError() {
        GenericDialogFragment.newInstance(
            getString(R.string.checkin_fail_title),
            getString(R.string.network_error)
        )
            .setPositiveButtonOption(getString(R.string.btn_ok))
            .show(parentFragmentManager, "checkinError")
    }

    private fun onUnknownError() {
        GenericDialogFragment.newInstance(
            getString(R.string.checkin_fail_title),
            getString(R.string.unknown_error)
        )
            .setPositiveButtonOption(getString(R.string.btn_ok))
            .show(parentFragmentManager, "checkinError")
    }

    private fun onError() {
        GenericDialogFragment.newInstance(
            getString(R.string.checkin_fail_title),
            getString(R.string.checkin_fail)
        )
            .setPositiveButtonOption(getString(R.string.btn_ok))
            .show(parentFragmentManager, "checkinError")
    }
}