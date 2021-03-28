package com.example.coursefinder.androidApp

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coursefinder.androidApp.model.CourseView
import java.util.Locale.filter

class SearchViewActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter
    private lateinit var editText: EditText
    private val courses= ArrayList<CourseView>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.searchview_activity)
        editText = findViewById(R.id.editText)

        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                filter(s.toString());
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        }

        editText.addTextChangedListener(textWatcher)

        for(i in 0 until 20){
            val item = CourseView("course $i", "description $i")
            courses += item
        }

        createRecyclerView()

    }

    private fun filter(text: String) {
        var filteredList = ArrayList<CourseView>()
        for( course in courses){
            if(course.title.toLowerCase().contains(text.toLowerCase())){
                filteredList.add(course);
            }
        }
        recyclerViewAdapter.filterList(filteredList)

    }

    private fun createRecyclerView(){
        recyclerView = findViewById(R.id.courseListView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerViewAdapter = RecyclerViewAdapter(this, courses)
        recyclerView.adapter = recyclerViewAdapter;

    }


}