package com.learning.onboarding.utils

import androidx.annotation.DrawableRes
import com.learning.onboarding.R

sealed class OnBoardPage(
    @DrawableRes
    val image: Int,
    val title: String,
    val description: String
) {
    object FirstPage : OnBoardPage(
        R.drawable.rules_icon,
        "Rules",
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod."
    )

    object SecondPage : OnBoardPage(
        R.drawable.notepad_icon,
        "Notepad",
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod."
    )

    object ThirdPage : OnBoardPage(
        R.drawable.sketch_pad_icon,
        "Sketch Pade",
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod."
    )


}
