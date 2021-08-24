package com.evgenynaz.sportnote.bmi.exercise

import com.evgenynaz.sportnote.R

class Exercise {

    companion object{
        fun defaultExerciseList():ArrayList<ExerciseModel>{
            var exercisesList = ArrayList<ExerciseModel>()

            val jumpingJacks = ExerciseModel(1,
                "Прожок с поднятием рук вверх",
                R.drawable.jumping_jacks,
                false,
                false)
            exercisesList.add(jumpingJacks)

            val wallSit = ExerciseModel(2,
                "Приседание",
                R.drawable.wall_sit,
                false,
                false)
            exercisesList.add(wallSit)

            val pushUp = ExerciseModel(3,
                "Отжимание",
                R.drawable.pushups,
                false,
                false)
            exercisesList.add(pushUp)

            val squat = ExerciseModel(4,
                "Приседание",
                R.drawable.squat,
                false,
                false)
            exercisesList.add(squat)


            val plank = ExerciseModel(5,
                "Планка",
                R.drawable.plank,
                false,
                false)
            exercisesList.add(plank)

            return exercisesList
        }
    }
}