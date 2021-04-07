package com.example.coursefinder.androidApp.course

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coursefinder.androidApp.R
import com.example.coursefinder.androidApp.model.CourseView

class CourseListViewAdapter(
    private var courses:List<CourseView>,
    private val rowListener: OnItemClickListener
    ): RecyclerView.Adapter<CourseListViewAdapter.CourseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.course_list_item, parent, false)
        val title = itemView.findViewById<TextView>(R.id.course_title)
        val description = itemView.findViewById<TextView>(R.id.course_description)

        return CourseViewHolder(itemView, title, description, rowListener)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        holder.itemView.isEnabled = true
        holder.courseTitle.text = courses[position].title
        holder.courseDescription.text = courses[position].description
    }

    override fun getItemCount(): Int {
        return courses.size
    }

    class CourseViewHolder (itemView: View, internal var title:TextView, internal var description:TextView, private var rowListener: OnItemClickListener) : RecyclerView.ViewHolder(itemView),
    View.OnClickListener {
        var courseTitle: TextView = itemView.findViewById(R.id.course_title)
        var courseDescription:TextView = itemView.findViewById(R.id.course_description)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            //check to see if position exists in recyclerview
            if(position != RecyclerView.NO_POSITION){
                rowListener.onItemClick(position)
            }
        }
    }

    fun updateCoursesList(coursesList: ArrayList<CourseView>) {
        courses = coursesList
        notifyDataSetChanged()
    }

    interface  OnItemClickListener{
        fun onItemClick(position: Int) {}
    }
}
