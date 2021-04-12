package com.example.coursefinder.androidApp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coursefinder.androidApp.model.CourseView

class RecyclerViewAdapter(var context: Context, private var courses:List<CourseView>, private val rowListener:OnItemClickListener): RecyclerView.Adapter<RecyclerViewAdapter.CourseViewHolder>() {
//    private val courseList = ArrayList<CourseView>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.course_list_item, parent, false)
        val title = itemView.findViewById<TextView>(R.id.course_title)

        return CourseViewHolder(itemView, title, rowListener)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {

        holder.itemView.isEnabled = true
        holder.courseTitle.text = courses[position].title
    }

    override fun getItemCount(): Int {
        return courses.size
    }

    class CourseViewHolder (itemView: View, internal var title:TextView, private var rowListener: OnItemClickListener) : RecyclerView.ViewHolder(itemView),
    View.OnClickListener{
        var courseTitle: TextView = itemView.findViewById(R.id.course_title)

        init{
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
    fun filterList(filteredList: ArrayList<CourseView>) {
        courses= filteredList
        notifyDataSetChanged()
    }

    interface  OnItemClickListener{
        fun onItemClick(position: Int){

        }
    }
}
