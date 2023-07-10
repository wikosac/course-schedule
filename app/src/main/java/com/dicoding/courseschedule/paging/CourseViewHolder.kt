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

            binding.tvCourse.text = courseName
            binding.tvTime.text = timeFormat
            binding.tvLecturer.text = lecturer
        }

        itemView.setOnClickListener {
            clickListener(course)
        }
    }

    fun clear() {
        // Clear the view elements if needed
        binding.tvCourse.text = null
        binding.tvTime.text = null
        binding.tvLecturer.text = null
    }

    fun getCourse(): Course = course
}