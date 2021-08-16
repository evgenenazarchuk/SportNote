package com.evgenynaz.sportnote.bmi

import com.evgenynaz.myhomework.homework16.BMI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class BmiRepository(
    private val bmiDao: BmiDao
) {

    fun getMessagesList(): Flow<List<BMI>> =
        bmiDao.getBmiList().map { bmiEntities ->
            bmiEntities.map { bmiEntities ->
                BMI(bmiEntities.calories, bmiEntities.date)
            }
        }


    suspend fun addMessages(bmi: BmiEntity) {
        bmiDao.addBmi(bmi)

    }

    suspend fun deleteMessage(bmi: BMI) {

        bmiDao.deleteBmi(bmi.entity())
    }

    private fun BMI.entity() = BmiEntity(this.calories, this.date)
}
