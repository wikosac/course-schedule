package com.dicoding.courseschedule.paging

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.data.Course
import com.dicoding.courseschedule.databinding.ItemCourseBinding
import com.dicoding.courseschedule.util.DayName.Companion.getByNumber

class CourseViewHolder(private val binding: ItemCourseBinding): RecyclerView.ViewHolder(binding.root) {

    private lateinit var course: Course
    private val timeString = itemView.context.resources.getString(R.string.time_format)

    // Complete ViewHolder to show item
    fun bind(course: Course, clickListener: (Course) -> Unit) {
        this.course = course

        course.apply {
            val dayName = getByNumber(day)
            val timeFormat = String.format(timeString, dayName, startTime, endTime)

            with(binding) {
                tvCourse.text = courseName
                tvTime.text = timeFormat
                tvLecturer.text = lecturer
            }
        }

        itemView.setOnClickListener {
            clickListener(course)
        }
    }

    fun getCourse(): Course = course
}