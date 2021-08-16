package com.evgenynaz.sportnote.bmi

import androidx.room.*

import kotlinx.coroutines.flow.Flow


@Dao
interface BmiDao {
    @Query("SELECT * FROM bmi")
    fun getBmiList(): Flow<List<BmiEntity>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addBmi(bmi: BmiEntity)

    @Delete
    suspend fun deleteBmi(bmi: BmiEntity)
}
