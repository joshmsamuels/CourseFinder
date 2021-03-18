package com.example.coursefinder.androidApp.course

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Space
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.coursefinder.androidApp.SearchViewActivity
import com.example.coursefinder.shared.course.SelectModel
import com.example.coursefinder.shared.course.SelectDelegate
import com.example.coursefinder.shared.course.SelectViewModel

class SelectActivity : SelectDelegate, AppCompatActivity() {
    private lateinit var view: SelectView
    private var viewModel = SelectViewModel(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        view = SelectView(this)
        setContentView(view)
    }

    override fun onResume() {
        super.onResume()

        view.apply(viewModel.generateModel())
    }

    override fun searchByCourseCodeButtonAction() {
        startActivity(Intent(this, SearchViewActivity::class.java))
    }

    override fun searchByCourseNameButtonAction() {
        TODO("Add search by course name intent")
    }
}

class SelectView(context: Context?) : LinearLayout(context) {
    private val primaryTextView: TextView
    private val primaryButton: Button
    private val secondaryButton: Button
    private val primarySpacer: Space
    private val secondarySpacer: Space

    init {
        orientation = VERTICAL
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        gravity = Gravity.CENTER

        primaryTextView = TextView(context)
        primaryButton = Button(context)
        secondaryButton = Button(context)
        primarySpacer = Space(context)
        secondarySpacer = Space(context)

        primaryTextView.gravity = Gravity.CENTER
        primarySpacer.minimumHeight = 50
        secondarySpacer.minimumHeight = 50

        addView(primaryTextView)
        addView(primarySpacer)
        addView(primaryButton)
        addView(secondarySpacer)
        addView(secondaryButton)
    }

    fun apply(model: SelectModel) {
        primaryTextView.text = model.selectSearchMethodText
        primaryButton.text = model.searchByCourseCodeButtonText
        secondaryButton.text = model.searchByCourseNameButtonText

        primaryButton.setOnClickListener { model.searchByCourseCodeButtonAction() }
        secondaryButton.setOnClickListener { model.searchByCourseNameButtonAction() }

        invalidate()
    }
}
