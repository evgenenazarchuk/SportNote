package com.evgenynaz.sportnote.bmi.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "UserBmi")
data class UserBmi(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var id: Int?,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "height") var height: String,
    @ColumnInfo(name = "weight") var weight: String,
    @ColumnInfo(name = "bmi") var bmi: String,
    @ColumnInfo(name = "gender") var gender: String,
    @ColumnInfo(name = "age") var age: Int
) {
    constructor() : this(null, "", "", "", "", "", 0)
}
