package com.learning.collapsebottomnavigation.models.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavItem(
    val route: String,
    val name: String,
    val icon: ImageVector
) {
    object Home : NavItem("home", "Home", Icons.Default.Home)
    object Favorite : NavItem("favorite", "Favorite", Icons.Default.Favorite)
    object Search : NavItem("search", "Search", Icons.Default.Search)
    object Person : NavItem("user", "Me", Icons.Default.Person)
}

val navList = listOf(
    NavItem.Home,
    NavItem.Favorite,
    NavItem.Search,
    NavItem.Person
)
