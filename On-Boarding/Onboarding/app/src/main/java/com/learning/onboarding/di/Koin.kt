package com.learning.onboarding.di

import com.learning.onboarding.data.repositories.WelcomeRepository
import com.learning.onboarding.ui.MainActivityViewModel
import com.learning.onboarding.ui.screens.welcomescreen.WelcomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val module = module {

    single { WelcomeRepository(get()) }

    viewModel { WelcomeViewModel(get()) }
    viewModel { MainActivityViewModel(get()) }
}