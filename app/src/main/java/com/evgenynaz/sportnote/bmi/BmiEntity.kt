package com.evgenynaz.sportnote.bmi

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bmi")
class BmiEntity(
    @PrimaryKey val calories: String,
    val date: String
)


