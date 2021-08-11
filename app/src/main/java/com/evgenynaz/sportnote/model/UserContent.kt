package com.evgenynaz.sportnote.model

import androidx.room.Embedded
import androidx.room.Relation

class UserContent(

    @Embedded
    val user: User,

    @Relation(
        parentColumn = "name",
        entityColumn = "userName"
    )
    val notes: List<Note>
)