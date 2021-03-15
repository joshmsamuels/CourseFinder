package com.example.coursefinder.androidApp.course

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.coursefinder.androidApp.MainActivity
import com.example.coursefinder.shared.course.SelectDelegate
import com.example.coursefinder.shared.course.SelectModel
import com.example.coursefinder.shared.course.SelectViewModel
import com.example.coursefinder.shared.user.USER_ANON_FIELD
import com.example.coursefinder.shared.user.USER_EMAIL_FIELD
import com.example.coursefinder.shared.user.UserModel
import com.firebase.ui.auth.AuthUI

class SelectActivity : SelectDelegate, AppCompatActivity() {
    private lateinit var view: SelectView
    private var viewModel = SelectViewModel(this)
    private lateinit var user: UserModel

    private val launchMainActivity = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode != Activity.RESULT_OK) {
            Log.d(
                    "select activity",
                    "launch main activity result code: ${result.resultCode} --- result: $result"
            )

            TODO("result was not ok")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        view = SelectView(this)
        setContentView(view)

        user = UserModel(
            intent.getStringExtra(USER_EMAIL_FIELD),
            intent.getBooleanExtra(USER_ANON_FIELD, true)
        )

        if (user.isAnonymous) {
            Toast.makeText(this, "Signed in anonymously", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Signed in with ${user.email}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()

        view.apply(viewModel.generateModel())

        view.tmpSignOut.setOnClickListener{ signOut() }

    }

    override fun searchByCourseCodeButtonAction() {
        TODO("Add search by course code intent")
    }

    override fun searchByCourseNameButtonAction() {
        TODO("Add search by course name intent")
    }

    fun signOut() {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener {
                    finish()
                    launchMainActivity.launch(Intent(this, MainActivity::class.java))
                }
    }
}

class SelectView(context: Context) : LinearLayout(context) {
    private val primaryTextView: TextView
    private val primaryButton: Button
    private val secondaryButton: Button
    private val primarySpacer: Space
    private val secondarySpacer: Space
    val tmpSignOut = Button(context)
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

        // TODO: Delete once there is an app bar

        tmpSignOut.text = "sign out"
        tmpSignOut.gravity = Gravity.CENTER
        addView(tmpSignOut)
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
