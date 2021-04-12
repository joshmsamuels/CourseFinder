package com.example.coursefinder.androidApp.course

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coursefinder.androidApp.model.CourseView
import androidx.navigation.fragment.navArgs
import com.example.coursefinder.androidApp.R
import com.example.coursefinder.shared.course.RetrievalType
import com.example.coursefinder.shared.course.SearchCourseDelegate
import com.example.coursefinder.shared.course.RetrieveCoursesViewModel
import com.example.coursefinder.shared.model.NotificationRow


class CourseListViewFragment : Fragment(), SearchCourseDelegate, CourseListViewAdapter.OnItemClickListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var courseListViewAdapter: CourseListViewAdapter
    private lateinit var editText: EditText
    private val courseList = ArrayList<CourseView>()
    private val args: CourseListViewFragmentArgs by navArgs()
    private lateinit var viewModel: RetrieveCoursesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        val view = inflater.inflate(R.layout.course_list_view_fragment, container, false)
            editText = view.findViewById(R.id.editText)

        //if user is signed in and wants to view their courses, set viewModel to their list of courses
        viewModel = if (args.searchType == "userCourses") {
            RetrieveCoursesViewModel(this, RetrievalType.Subscriptions(args.email))
        } else {
            RetrieveCoursesViewModel(this, RetrievalType.AvailableCourses)
        }

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

        viewModel.courses.ld().observe(viewLifecycleOwner, {
            courseList.clear()

            for (i in viewModel.courses.value.indices) {
                if (args.searchType == "courseCode") {
                    courseList += CourseView(viewModel.courses.value[i].courseCode, "description $i")
                } else if (args.searchType == "courseName") {
                    courseList += CourseView(viewModel.courses.value[i].courseName, "description $i")
                } else {
                    courseList += CourseView(viewModel.courses.value[i].courseCode, "description $i")
                }
            }

            courseListViewAdapter.updateCoursesList(courseList)
        })

        recyclerView = view.findViewById(R.id.courseListView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        courseListViewAdapter = CourseListViewAdapter(courseList, this)
        recyclerView.adapter = courseListViewAdapter

        return view
    }

    override fun onResume() {
        super.onResume()

        viewModel.refresh()
    }

    override fun onItemClick(position: Int) {
        super.onItemClick(position)
//        showCourseDetails(viewModel.courses.value[position].courseCode)
        viewModel.rowAction(position)
    }


    private fun filter(text: String) {
        val filteredList = ArrayList<CourseView>()
        for( course in courseList){
            if(course.title.toLowerCase().contains(text.toLowerCase())){
                filteredList.add(course)
            }
        }
        courseListViewAdapter.updateCoursesList(filteredList)

    }

    override fun showCourseDetails(courseCode: String, notificationRows: List<NotificationRow>) {
        findNavController().navigate(
                CourseListViewFragmentDirections.goToSubscriptionFragment(
                    courseCode = courseCode,
                    notificationRows = notificationRows.toTypedArray()
                )
        )
    }

}