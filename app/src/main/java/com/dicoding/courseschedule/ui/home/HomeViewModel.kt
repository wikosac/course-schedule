package com.dicoding.courseschedule.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.courseschedule.data.Course
import com.dicoding.courseschedule.data.DataRepository
import com.dicoding.courseschedule.util.QueryType

class HomeViewModel(repository: DataRepository): ViewModel() {

    private val _queryType = MutableLiveData<QueryType>()
    val nearestSchedule: LiveData<Course?>

    init {
        _queryType.value = QueryType.CURRENT_DAY
        nearestSchedule = repository.getNearestSchedule(_queryType.value!!)
    }

    fun setQueryType(queryType: QueryType) {
        _queryType.value = queryType
    }
}
