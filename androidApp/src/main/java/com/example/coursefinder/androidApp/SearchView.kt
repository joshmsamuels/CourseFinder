package com.example.coursefinder.androidApp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coursefinder.androidApp.model.CourseView
import androidx.navigation.fragment.navArgs
import com.example.coursefinder.androidApp.course.SubscriptionFragmentArgs


class SearchViewFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter
    private lateinit var editText: EditText
    private val courses= ArrayList<CourseView>()
    private val args: SearchViewFragmentArgs by navArgs()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                          savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        var view = inflater.inflate(R.layout.searchview_fragment, container, false)
            editText = view.findViewById(R.id.editText)

        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                filter(s.toString());
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        }
        println(args.searchType)
        editText.addTextChangedListener(textWatcher)

        for(i in 0 until 20){
            val item = CourseView("course $i", "description $i")
            courses += item
        }

        recyclerView = view.findViewById(R.id.courseListView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        recyclerViewAdapter = RecyclerViewAdapter(view.context, courses)
        recyclerView.adapter = recyclerViewAdapter;

        return view;

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



}