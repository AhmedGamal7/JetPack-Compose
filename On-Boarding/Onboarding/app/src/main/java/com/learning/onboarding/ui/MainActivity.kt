package com.learning.onboarding.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.learning.onboarding.data.NavItem
import com.learning.onboarding.ui.screens.homescreen.HomeScreen
import com.learning.onboarding.ui.screens.welcomescreen.WelcomeScreen
import com.learning.onboarding.ui.theme.OnboardingTheme
import org.koin.androidx.compose.viewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            OnboardingTheme {
                val viewModel: MainActivityViewModel by viewModel()
                val navController = rememberNavController()

                if (viewModel.isLoading.value) {
                    CircularProgressIndicator(color = Color.Green)
                } else {
                    SetUpNavGraph(navController, viewModel.startDestination.value)
                }

            }
        }
    }
}

@Composable
fun SetUpNavGraph(navController: NavHostController, startDestination: String) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable(NavItem.Welcome.route) {
            //Welcome Composable function
            WelcomeScreen(navController)
        }
        composable(NavItem.Home.route) {
            // Home Screen Composable function
            HomeScreen()
        }

    }
}
