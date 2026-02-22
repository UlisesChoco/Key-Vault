package com.chocolatada.keyvault.features.authentication.presentation

import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.lifecycle.ViewModel
import com.chocolatada.keyvault.MainActivity
import com.chocolatada.keyvault.features.authentication.domain.IAuthenticationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AuthenticationViewModel(
    private val authenticationRepository: IAuthenticationRepository
): ViewModel() {
    private val _authenticated = MutableStateFlow(value = false)
    val authenticated = _authenticated.asStateFlow()

    fun authenticate(activity: MainActivity) {
        authenticationRepository.authenticate(
            activity = activity,
            authenticator = BIOMETRIC_STRONG,
            authenticatorInCaseOfError = DEVICE_CREDENTIAL,
            onSuccessfulAuthentication = {
                _authenticated.value = true
            }
        )
    }
}