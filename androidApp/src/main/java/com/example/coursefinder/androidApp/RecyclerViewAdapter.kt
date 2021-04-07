package com.example.coursefinder.androidApp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coursefinder.androidApp.model.CourseView

class RecyclerViewAdapter(var context: Context, private var courses:List<CourseView>): RecyclerView.Adapter<RecyclerViewAdapter.CourseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.course_list_item, parent, false)
        val title = itemView.findViewById<TextView>(R.id.course_title)
        val description = itemView.findViewById<TextView>(R.id.course_description)

        return CourseViewHolder(itemView, title, description)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        holder.itemView.isEnabled = true
        holder.courseTitle.text = courses[position].title
        holder.courseDescription.text = courses[position].description
    }

    override fun getItemCount(): Int {
        return courses.size
    }

    class CourseViewHolder (itemView: View, internal var title:TextView, internal var description:TextView) : RecyclerView.ViewHolder(itemView) {
        var courseTitle: TextView = itemView.findViewById(R.id.course_title)
        var courseDescription:TextView = itemView.findViewById(R.id.course_description)
    }

    fun updateCoursesList(coursesList: ArrayList<CourseView>) {
        courses= coursesList
        notifyDataSetChanged()
    }

}
