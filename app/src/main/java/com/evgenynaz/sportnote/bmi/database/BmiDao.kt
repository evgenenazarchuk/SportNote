package com.evgenynaz.sportnote.bmi.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.evgenynaz.sportnote.bmi.model.BMI

@Dao
interface BmiDao {

    @Insert
    suspend fun inserBmi(bmi: BMI)

    @Delete
    suspend fun deleteBmi(bmi: BMI)

    @Query("SELECT * FROM BMI")
    fun getAllBmis(): LiveData<List<BMI>>
}