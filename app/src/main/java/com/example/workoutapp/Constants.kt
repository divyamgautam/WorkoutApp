package com.example.workoutapp

object Constants {
    fun defaultExerciseList(): ArrayList<ExerciseModel>{
        val exerciseList = ArrayList<ExerciseModel>()
        val jumpingJacks = ExerciseModel(1, "Jumping Jacks", R.drawable.ic_jj,
            false, false)
        val pushUps = ExerciseModel(2, "Push Ups", R.drawable.ic_pp,
            false, false)
        val squats = ExerciseModel(3, "Squats", R.drawable.ic_s,
            false, false)
        val lunges = ExerciseModel(4, "Lunges", R.drawable.ic_l,
            false, false)
        val inclinePushUps = ExerciseModel(5, "Incline Push-ups", R.drawable.ic_ip,
            false, false)
        val sitUps = ExerciseModel(6, "Sit Ups", R.drawable.ic_su,
            false, false)
        val  superman= ExerciseModel(7, "Superman", R.drawable.ic_sm,
            false, false)
        val pikePushUps = ExerciseModel(8, "Pike Push Ups", R.drawable.ic_ppu,
            false, false)
        val plankTap = ExerciseModel(9, "Plank Tap", R.drawable.ic_pt,
            false, false)
        val tricepsDips = ExerciseModel(10, "Triceps Dips", R.drawable.ic_td,
            false, false)

        exerciseList.add(jumpingJacks)
        exerciseList.add(pushUps)
        exerciseList.add(squats)
        exerciseList.add(lunges)
        exerciseList.add(inclinePushUps)
        exerciseList.add(sitUps)
        exerciseList.add(superman)
        exerciseList.add(pikePushUps)
        exerciseList.add(plankTap)
        exerciseList.add(tricepsDips)
        return exerciseList
    }
}