package com.learning.onboarding.ui.screens.welcomescreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learning.onboarding.data.repositories.WelcomeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WelcomeViewModel(
    private val welcomeRepository: WelcomeRepository
) : ViewModel() {

    fun saveOnBoardingState(completed: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            welcomeRepository.saveOnBoardingState(completed = completed)
        }
    }
}