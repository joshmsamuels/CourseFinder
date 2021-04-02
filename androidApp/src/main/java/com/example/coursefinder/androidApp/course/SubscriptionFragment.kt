package com.example.coursefinder.androidApp.course

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.coursefinder.androidApp.R
import com.example.coursefinder.androidApp.databinding.SubscriptionFragmentViewBinding
import com.example.coursefinder.androidApp.databinding.SubscriptionViewRowBinding
import com.example.coursefinder.shared.course.SubscriptionDelegate
import com.example.coursefinder.shared.course.SubscriptionModel
import com.example.coursefinder.shared.course.SubscriptionViewModel

class SubscriptionFragment: Fragment(R.layout.subscription_fragment_view), SubscriptionDelegate {
    private var viewModel = SubscriptionViewModel(this)
    private var binding: SubscriptionFragmentViewBinding? = null
    private val args: SubscriptionFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = SubscriptionFragmentViewBinding.bind(view)
        binding?.setContent(
            viewModel.generateModel(args.courseCode)
        )
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    override fun saveAction() {
        TODO("Not yet implemented")
    }

    override fun cancelAction() {
        TODO("Not yet implemented")
    }

    private fun SubscriptionFragmentViewBinding.setContent(model: SubscriptionModel) {
        this.subscriptionViewTitle.text = model.title

        model.contentRows.forEach {
            val rowView = layoutInflater.inflate(R.layout.subscription_view_row, null, false)
            this.subscriptionViewContentArea.addView(rowView)

            val rowViewBinding = SubscriptionViewRowBinding.bind(rowView)
            rowViewBinding.subscriptionViewRowTitle.text = it.title
            rowViewBinding.subscriptionViewRowSubtitle.text = it.subtitle
        }

        this.subscriptionViewTextboxTitle.text = model.textFieldPrompt

        this.subscriptionViewPrimaryButton.text = model.primaryButtonText
        this.subscriptionViewPrimaryButton.setOnClickListener { model.saveAction ()}

        this.subscriptionViewSecondaryButton.text = model.secondaryButtonText
        this.subscriptionViewSecondaryButton.setOnClickListener { model.cancelAction() }
    }

}