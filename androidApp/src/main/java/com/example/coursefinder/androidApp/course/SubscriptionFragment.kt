package com.example.coursefinder.androidApp.course

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.coursefinder.androidApp.R
import com.example.coursefinder.androidApp.databinding.SubscriptionFragmentViewBinding
import com.example.coursefinder.androidApp.databinding.SubscriptionViewRowBinding
import com.example.coursefinder.shared.course.SubscriptionDelegate
import com.example.coursefinder.shared.course.SubscriptionViewModel
import com.google.firebase.auth.FirebaseAuth

class SubscriptionFragment: Fragment(), SubscriptionDelegate {
    private var binding: SubscriptionFragmentViewBinding? = null
    private val args: SubscriptionFragmentArgs by navArgs()

    private lateinit var viewModel: SubscriptionViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = SubscriptionViewModel(
            delegate = this,
            courseId = args.courseCode,
            email = FirebaseAuth.getInstance().currentUser?.email ?: ""
        )
         if (args.notificationRows.isNotEmpty()) {
             viewModel.notificationRows.value = args.notificationRows.toList()
         }

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.subscription_fragment_view,
            container,
            false
        )

        val subscriptionFragmentBinding = binding.takeUnless {
            binding == null
        } ?: return null

        subscriptionFragmentBinding.lifecycleOwner = this
        subscriptionFragmentBinding.viewModel = viewModel
        subscriptionFragmentBinding.subscriptionViewPrimaryButton.setOnClickListener { viewModel.saveNotifications(term = "Winter 2021") }
        subscriptionFragmentBinding.subscriptionViewSecondaryButton.setOnClickListener { viewModel.cancel() }

        return subscriptionFragmentBinding.root
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

                // Updates the row information when the view model is changed
                rowBinding.subscriptionViewRowTitle.text = row.notificationName
                rowBinding.subscriptionViewRowSubtitle.text = row.courseRowDetail
                rowBinding.subscriptionViewRowCheckbox.isChecked = row.checked

                if (row.courseRowDetail.contains("Loading")) {
                    rowBinding.subscriptionViewRowCheckbox.isEnabled = false
                }

                // Updates the view model when the checkbox state changes
                rowBinding.subscriptionViewRowCheckbox.setOnClickListener { row.checked = !row.checked }

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
        findNavController().navigate(SubscriptionFragmentDirections.goToSelectSearchFragment())
    }

    override fun showToast(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show()
    }
}