package com.example.coursefinder.androidApp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coursefinder.androidApp.model.CourseView

class RecyclerViewAdapter(private var courses:List<CourseView>): RecyclerView.Adapter<RecyclerViewAdapter.CourseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        var itemView = LayoutInflater.from(parent.context).inflate(R.layout.course_list_item, parent, false)


        val title = itemView.findViewById<TextView>(R.id.course_title)
        val description = itemView.findViewById<TextView>(R.id.course_description)

//        context = parent.context

        return CourseViewHolder(itemView, title, description)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        var currentItem = courses[position]

        var itemView = holder.itemView


        holder.itemView.isEnabled = true
        holder.courseTitle.text = currentItem.title
        holder.courseDescription.text = currentItem.description
    }

    override fun getItemCount(): Int {
        return courses.size
    }

    class CourseViewHolder (itemView: View, internal var title:TextView, internal var description:TextView) : RecyclerView.ViewHolder(itemView) {
        var courseTitle: TextView = itemView.findViewById(R.id.course_title)
        var courseDescription:TextView = itemView.findViewById(R.id.course_description)

        //gets called once for each item that we want to show in recycler view
        //adds single row of the recycler view
        fun updateEvent (courseView: CourseView){


        }

    }
}


//
//class RecyclerViewAdapter {
//    private var courseList = arrayListOf<String>()
//    private lateinit var context: Context
//
//    fun removeCourse(position: Int) {
//        courseList.removeAt(position)
////            notifyItemRemoved(position)
//    }
//
//    fun getCourseAtPosition(position: Int) = courseList[position]
//}