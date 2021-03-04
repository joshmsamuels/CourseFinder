package com.example.coursefinder.androidApp

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Space
import androidx.appcompat.app.AppCompatActivity
import com.example.coursefinder.shared.login.Model
import com.example.coursefinder.shared.login.generateModel

class MainActivity : AppCompatActivity() {
    private lateinit var view: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        view = View(this)
        setContentView(view)
    }

    override fun onResume() {
        super.onResume()

        view.apply(generateModel())
    }
}

class View(context: Context?) : LinearLayout(context) {
    private val primaryButton: Button
    private val secondaryButton: Button
    private val buttonSpacer: Space

    init {
        orientation = VERTICAL
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        gravity = Gravity.CENTER

        primaryButton = Button(context)
        secondaryButton = Button(context)
        buttonSpacer = Space(context)

        buttonSpacer.minimumHeight = 50

        addView(primaryButton)
        addView(buttonSpacer)
        addView(secondaryButton)
    }

    fun apply(model: Model) {
        primaryButton.text = model.primaryButtonText
        secondaryButton.text = model.secondaryButtonText

        invalidate()
    }
}
