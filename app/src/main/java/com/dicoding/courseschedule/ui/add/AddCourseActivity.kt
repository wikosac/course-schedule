package com.dicoding.courseschedule.ui.add

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.databinding.ActivityAddCourseBinding
import com.dicoding.courseschedule.util.TimePickerFragment
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddCourseActivity : AppCompatActivity(), TimePickerFragment.DialogTimeListener {
    private lateinit var binding: ActivityAddCourseBinding
    private lateinit var viewModel: AddCourseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddCourseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = AddCourseViewModelFactory.createFactory(this)
        viewModel = ViewModelProvider(this, factory)[AddCourseViewModel::class.java]

        viewModel.saved.observe(this) {
            if (it.getContentIfNotHandled()!!) {
                Toast.makeText(this, "Course inserted successfully", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        binding.ibStartTime.setOnClickListener { showTimePicker("startTimePicker") }
        binding.ibEndTime.setOnClickListener { showTimePicker("endTimePicker") }
    }

    private fun showTimePicker(tag: String) {
        val dialogFragment = TimePickerFragment()
        dialogFragment.show(supportFragmentManager, tag)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_insert -> {
                with(binding) {
                    val courseName = edCourseName.text.toString()
                    val day = spinnerDay.selectedItemPosition
                    val startTime = tvStartTime.text.toString()
                    val endTime = tvEndTime.text.toString()
                    val lecturer = edLecturer.text.toString()
                    val note = edNote.text.toString()

                    if (courseName.isEmpty() || startTime.isEmpty() || endTime.isEmpty()) {
                        Toast.makeText(this@AddCourseActivity, "Please fill in the blank", Toast.LENGTH_SHORT).show()
                    }
                    viewModel.insertCourse(courseName, day, startTime, endTime, lecturer, note)
                }

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDialogTimeSet(tag: String?, hour: Int, minute: Int) {
        when (tag) {
            "startTimePicker" -> {
                val startTime = String.format("%02d:%02d", hour, minute)
                binding.tvStartTime.text = startTime
            }
            "endTimePicker" -> {
                val endTime = String.format("%02d:%02d", hour, minute)
                binding.tvEndTime.text = endTime
            }
        }
    }
}