package com.learning.onboarding.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learning.onboarding.data.NavItem
import com.learning.onboarding.data.repositories.WelcomeRepository
import kotlinx.coroutines.launch

class MainActivityViewModel(
    private val repository: WelcomeRepository
) : ViewModel() {

    private val _isLoading: MutableState<Boolean> = mutableStateOf(true)
    val isLoading: State<Boolean> = _isLoading

    private val _startDestination: MutableState<String> = mutableStateOf(NavItem.Welcome.route)
    val startDestination: State<String> = _startDestination

    init {
        viewModelScope.launch {
            repository.readOnBoardingState().collect { completed ->
                if (completed) {
                    _startDestination.value = NavItem.Home.route
                } else {
                    _startDestination.value = NavItem.Welcome.route
                }
                _isLoading.value = false
            }

        }
    }
}