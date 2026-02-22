package com.chocolatada.keyvault.di.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.chocolatada.keyvault.features.authentication.domain.IAuthenticationRepository
import com.chocolatada.keyvault.features.authentication.presentation.AuthenticationViewModel

class AuthenticationViewModelFactory(
    private val authenticationRepository: IAuthenticationRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AuthenticationViewModel(
            authenticationRepository = authenticationRepository
        ) as T
    }
}