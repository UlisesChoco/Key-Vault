package com.chocolatada.keyvault.features.authentication.domain

import com.chocolatada.keyvault.MainActivity

interface IAuthenticationRepository {
    fun authenticate(
        activity: MainActivity,
        authenticator: Int,
        authenticatorInCaseOfError: Int,
        onSuccessfulAuthentication: () -> Unit
    )
}