package com.filipesanders.desafio_south_systhem.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import com.filipesanders.desafio_south_systhem.R
import kotlinx.android.synthetic.main.fragment_generic_dialog.*

class GenericDialogFragment : DialogFragment() {

    private var argTitle: String? = null
    private var argMessage: String? = null
    private var positiveButtonText: String? = null
    private var negativeButtonText: String? = null
    private var onPositiveClicked: (() -> Unit)? = null
    private var onNegativeClicked: (() -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        argTitle = arguments?.getString(ARG_TITLE)
        argMessage = arguments?.getString(ARG_MESSAGE)
        return inflater.inflate(R.layout.fragment_generic_dialog, container, false)
    }

    override fun onStart() {
        super.onStart()
        dialog!!.window?.let {
            it.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            it.setBackgroundDrawableResource(R.color.bg_primary)
            it.setDimAmount(0f)

            // set the animations to use on showing and hiding the dialog
            it.setWindowAnimations(R.style.dialog_animation_fade)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        animateEntrance()
        dialogCloseBtn.setOnClickListener { dismiss() }

        dialogTitle.isVisible = argTitle != null
        dialogTitle.text = argTitle
        dialogMessage.text = argMessage

        positiveButton.text = positiveButtonText

        dialogCloseBtn.isVisible = isCancelable

        if (negativeButtonText != null) {
            buttonDivider.isVisible = true
            negativeButton.isVisible = true
            negativeButton.text = negativeButtonText
        }

        positiveButton.setOnClickListener {
            onPositiveClicked?.invoke()
            dismiss()
        }

        negativeButton.setOnClickListener {
            onNegativeClicked?.invoke()
            dismiss()
        }
    }

    fun setPositiveButtonOption(
        text: String,
        onOptionPressed: (() -> Unit)? = null
    ): GenericDialogFragment {
        positiveButtonText = text
        onPositiveClicked = onOptionPressed
        return this
    }

    fun setNegativeButtonOption(
        text: String,
        onOptionPressed: (() -> Unit)? = null
    ): GenericDialogFragment {
        negativeButtonText = text
        onNegativeClicked = onOptionPressed
        return this
    }

    private fun animateEntrance() {
        dialogContent.let {
            it.scaleX = .75f
            it.scaleY = .75f
        }

        dialogContent.animate()
            .setInterpolator(DecelerateInterpolator())
            .scaleX(1f)
            .scaleY(1f)
            .setDuration(300)
            .start()
    }


    companion object {
        private const val ARG_TITLE = "arg_title"
        private const val ARG_MESSAGE = "arg_message"

        fun newInstance(message: String?) = newInstance(null, message)

        fun newInstance(title: String? = null, message: String? = null) =
            GenericDialogFragment().apply {
                arguments = bundleOf(ARG_TITLE to title, ARG_MESSAGE to message)
            }
    }

}

