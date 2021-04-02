package com.example.coursefinder.androidApp.course

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Space
import android.widget.TextView
import androidx.fragment.app.Fragment
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
        selectSearchTypeBinding?.let { selectSearchTypeBinding ->
            selectSearchTypeBinding.setContent(viewModel.generateModel())
            selectSearchTypeBinding.primaryButton.setOnClickListener {
                searchByCourseCodeButtonAction()
            }
            selectSearchTypeBinding.secondaryButton.setOnClickListener {
                searchByCourseNameButtonAction()
            }
        }
    }

    override fun onDestroyView() {
        selectSearchTypeBinding = null
        super.onDestroyView()
    }

    override fun searchByCourseCodeButtonAction() {
        TODO("Not yet implemented")
    }

    override fun searchByCourseNameButtonAction() {
        TODO("Not yet implemented")
    }

    private fun SelectSearchTypeBinding.setContent(model: SelectModel) {
        this.primaryTextView.text = model.selectSearchMethodText

        this.primaryButton.text = model.searchByCourseCodeButtonText
        this.secondaryButton.text = model.searchByCourseNameButtonText
    }

}