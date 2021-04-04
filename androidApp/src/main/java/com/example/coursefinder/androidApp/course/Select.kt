package com.example.coursefinder.androidApp.course

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.coursefinder.androidApp.R
import com.example.coursefinder.androidApp.databinding.SelectSearchTypeBinding
import com.example.coursefinder.shared.course.SelectDelegate
import com.example.coursefinder.shared.course.SelectModel
import com.example.coursefinder.shared.course.SelectViewModel

class SelectSearchFragment: Fragment(R.layout.select_search_type), SelectDelegate {
    private var viewModel = SelectViewModel(this)

    private var selectSearchTypeBinding: SelectSearchTypeBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        selectSearchTypeBinding = SelectSearchTypeBinding.bind(view)
        selectSearchTypeBinding?.setContent(viewModel.generateModel())
    }

    override fun onDestroyView() {
        selectSearchTypeBinding = null
        super.onDestroyView()
    }

    override fun searchByCourseCodeButtonAction() {
       findNavController().navigate(
            SelectSearchFragmentDirections.actionSelectSearchToSearchView("courseCode")
        )
    }

    override fun searchByCourseNameButtonAction() {
        findNavController().navigate(
            SelectSearchFragmentDirections.actionSelectSearchToSearchView("courseName")
        )
    }

    private fun SelectSearchTypeBinding.setContent(model: SelectModel) {
        this.primaryTextView.text = model.selectSearchMethodText

        this.primaryButton.text = model.searchByCourseCodeButtonText
        this.primaryButton.setOnClickListener { model.searchByCourseCodeButtonAction() }

        this.secondaryButton.text = model.searchByCourseNameButtonText
        this.secondaryButton.setOnClickListener { model.searchByCourseNameButtonAction() }

        // TODO - remove from here down when the subscription fragment is wired up
        val btn = Button(context)
        btn.text = "TEST - navigate to\nsubscription fragment"
        btn.setOnClickListener {
            findNavController().navigate(
                SelectSearchFragmentDirections.actionSelectSearchToSubscriptionSelection("TEST COURSE CODE")
            )
        }
        this.root.addView(btn)

    }

}