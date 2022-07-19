package com.example.busbay

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "date_time_table")
data class DateTimeEntity(

    @ColumnInfo(name = "data_date_time")
    val date_time: String,

    @PrimaryKey val id:Int?=null
)
