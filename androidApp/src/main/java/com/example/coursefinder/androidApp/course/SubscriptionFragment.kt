package com.example.coursefinder.androidApp.course

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.coursefinder.androidApp.R
import com.example.coursefinder.androidApp.databinding.SubscriptionFragmentViewBinding
import com.example.coursefinder.androidApp.databinding.SubscriptionViewRowBinding
import com.example.coursefinder.shared.course.SubscriptionDelegate
import com.example.coursefinder.shared.course.SubscriptionViewModel

class SubscriptionFragment: Fragment(), SubscriptionDelegate {
    private var viewModel = SubscriptionViewModel(this)
    private var binding: SubscriptionFragmentViewBinding? = null
    private val args: SubscriptionFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.subscription_fragment_view,
            container,
            false
        )

        binding?.lifecycleOwner = this
        binding?.viewModel = viewModel

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.notificationRows.ld().observe(viewLifecycleOwner, {
            binding?.subscriptionViewContentArea?.removeAllViews()

            it.forEach { row ->
                val rowView = layoutInflater.inflate(
                    R.layout.subscription_view_row,
                    binding?.subscriptionViewContentArea,
                    false
                )
                val rowBinding = SubscriptionViewRowBinding.bind(rowView)

                rowBinding.subscriptionViewRowTitle.text = row.title
                rowBinding.subscriptionViewRowSubtitle.text = row.subtitle
                rowBinding.subscriptionViewRowCheckbox.isChecked = row.value

                binding?.subscriptionViewContentArea?.addView(rowView)
            }
        })
    }

    override fun onStart() {
        super.onStart()
        viewModel.refresh(args.courseCode)
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    override fun navigateHome() {
        TODO("Not yet implemented")
    }

    override fun showError(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show()
    }
}