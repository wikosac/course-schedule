package com.dicoding.courseschedule.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

// Define a local database table using the schema in app/schema/course.json
@Parcelize
@Entity(tableName = "course")
data class Course(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val courseName: String,
    val day: Int,
    val startTime: String,
    val endTime: String,
    val lecturer: String,
    val note: String
): Parcelable
