package com.filipesanders.desafio_south_systhem.ui.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.toolbar.*

open class BaseFragment : Fragment() {

    open val showButtonBack: Boolean = true

    open val title: String = ""

    open val showShare: Boolean = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (showButtonBack) {
            toolbarBack.visibility = View.VISIBLE
        } else {
            toolbarBack.visibility = View.GONE
        }

        if (showShare) {
            toolbarShare.visibility = View.VISIBLE
        } else {
            toolbarShare.visibility = View.GONE
        }

        toolbarTitle.text = title

        toolbarBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

}