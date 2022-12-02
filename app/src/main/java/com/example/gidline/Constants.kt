package com.example.gidline

object Constants {
    const val PREFS = "fourPicsOneWordPrefs"
    const val LEVEL = "currentLevel"
    const val CYCLE = "levelCycle"

    fun setQuestions():List<Question>{
    return listOf(

        Question(
            id=1,
            images = listOf(
                R.drawable.img,
                R.drawable.img_1,
                R.drawable.img_2,
                R.drawable.img_3
            ),
            answer = "ХОЛОД"

        ),
        Question(
            id=1,
            images = listOf(
                R.drawable.img,
                R.drawable.img_1,
                R.drawable.img_2,
                R.drawable.img_3
            ),
            answer = "ГРОМКО"

        ),
        Question(
            id=1,
            images = listOf(
                R.drawable.img,
                R.drawable.img_1,
                R.drawable.img_2,
                R.drawable.img_3
            ),
            answer = "МУЗЫКА"

        ),
        Question(
            id=1,
            images = listOf(
                R.drawable.img,
                R.drawable.img_1,
                R.drawable.img_2,
                R.drawable.img_3
            ),
            answer = "ХОЛОД"

        ),
        Question(
            id=1,
            images = listOf(
                R.drawable.img,
                R.drawable.img_1,
                R.drawable.img_2,
                R.drawable.img_3
            ),
            answer = "ХОЛОД"

        )


    )
    }


}