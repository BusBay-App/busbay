package com.example.busbay

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface DateTimeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDateTime(dt_upload:DateTimeEntity)

    @Delete
    suspend fun deleteDateTime(dt_upload:DateTimeEntity)

    @Query("SELECT * FROM date_time_table WHERE data_date_time= :search_date_time")
    fun searchDateTime(search_date_time:String) : LiveData<List<DateTimeEntity>>

    @Query("SELECT * FROM date_time_table order by data_date_time DESC")
    fun getAllDateTime(): LiveData<List<DateTimeEntity>>


}