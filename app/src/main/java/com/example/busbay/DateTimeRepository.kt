package com.example.busbay

import androidx.lifecycle.LiveData

class DateTimeRepository(private val dateTimeDao: DateTimeDao) {

    val alldatetime: LiveData<List<DateTimeEntity>> =dateTimeDao.getAllDateTime()

    fun search_date_time(searchQuery: String) : LiveData<List<DateTimeEntity>> {
        return  dateTimeDao.searchDateTime(searchQuery)
    }

    suspend fun insert(date_time:DateTimeEntity){
        dateTimeDao.insertDateTime(date_time)
    }
//    suspend fun delete(note:DateTimeEntity){
//        DateTimeDao.deleteDateTime(note)
//    }
}