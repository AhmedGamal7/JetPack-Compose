package com.learning.onboarding.data

sealed class NavItem(
    val route: String,
) {
    object Welcome : NavItem("welcome_screen")
    object Home : NavItem("home_screen")
}
