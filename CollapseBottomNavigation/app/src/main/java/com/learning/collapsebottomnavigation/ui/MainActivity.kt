package com.learning.collapsebottomnavigation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.learning.collapsebottomnavigation.models.navigation.NavItem
import com.learning.collapsebottomnavigation.models.navigation.navList
import com.learning.collapsebottomnavigation.ui.theme.CollapseBottomNavigationTheme
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CollapseBottomNavigationTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {

    val bottomNavHeight = 60.dp
    val bottomNavHeightPx = with(LocalDensity.current) {
        bottomNavHeight.roundToPx().toFloat()
    }

    val bottomOffsetHeight = remember { mutableStateOf(0f) }
    val nestedScroll = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                val offset = bottomOffsetHeight.value + delta
                bottomOffsetHeight.value = offset.coerceIn(-bottomNavHeightPx, 0f)
                return Offset.Zero
            }
        }
    }

    val scaffoldState = rememberScaffoldState()
    val navController = rememberNavController()
    Scaffold(
        modifier = Modifier.nestedScroll(nestedScroll),
        scaffoldState = scaffoldState,
        bottomBar = {
            BottomAppBar(
                modifier = Modifier
                    .height(bottomNavHeight)
                    .offset {
                        IntOffset(x = 0, y = -bottomOffsetHeight.value.roundToInt())
                    }
            ) {
                SetUpBottomNavigation(navController, navList)
            }
        }
    ) {
        SetUpNavHost(navController = navController)
    }
}

@Composable
fun SetUpBottomNavigation(navController: NavHostController, navList: List<NavItem>) {
    BottomNavigation {
        navList.forEach { navItem ->
            BottomNavigationItem(
                selected = false,
                onClick = {
                    // handle click
                    navController.navigate(navItem.route) {
                        navController.graph.startDestinationRoute?.let {
                            popUpTo(it) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                label = { Text(text = navItem.name) },
                icon = {
                    Icon(imageVector = navItem.icon, contentDescription = "")
                },
                alwaysShowLabel = true
            )
        }
    }
}

@Composable
fun SetUpNavHost(navController: NavHostController) {

    NavHost(navController = navController, startDestination = NavItem.Home.route) {
        composable(NavItem.Home.route) {
            MainHomeCompose(navController)
        }
        composable(NavItem.Favorite.route) {
            MainFavoriteCompose(navController)
        }
        composable(NavItem.Search.route) {
            MainSearchCompose()
        }
        composable(NavItem.Person.route) {
            MainProfileCompose(navController)
        }

    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CollapseBottomNavigationTheme {
        MainScreen()
    }
}