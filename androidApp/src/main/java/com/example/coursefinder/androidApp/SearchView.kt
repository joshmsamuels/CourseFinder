package com.example.coursefinder.androidApp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coursefinder.androidApp.model.CourseView

class SearchViewActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerAdapter: RecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        this.supportActionBar?.hide()
        setContentView(R.layout.searchview_activity)


        val courses = ArrayList<CourseView>()

        for(i in 0 until 10){
            val item = CourseView("course $i", "description $i")
            courses += item
        }


        recyclerView = findViewById<RecyclerView>(R.id.courseListView)
        recyclerView.adapter = RecyclerViewAdapter(courses)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

    }

    fun populateRecyclerView(courses: List<String>) {
        val courseList : ArrayList<String> = ArrayList()
        for(course in courses){
            courseList.add(course)
        }

    }

}