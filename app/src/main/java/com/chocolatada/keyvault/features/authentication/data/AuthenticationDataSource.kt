package com.chocolatada.keyvault.features.authentication.data

import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.chocolatada.keyvault.MainActivity
import com.chocolatada.keyvault.features.authentication.domain.IAuthenticationRepository
import com.chocolatada.keyvault.toast

//i'll clean this code and refactor it later
//but for now it works
class AuthenticationDataSource(): IAuthenticationRepository {
    override fun authenticate(
        activity: MainActivity,
        authenticator: Int,
        authenticatorInCaseOfError: Int,
        onSuccessfulAuthentication: () -> Unit
    ) {
        val biometricManager = BiometricManager.from(activity)
        when (biometricManager.canAuthenticate(authenticator)) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                launchAuthenticationPrompt(
                    activity = activity,
                    authenticator = authenticator,
                    onSuccessfulAuthentication = onSuccessfulAuthentication
                )
            }

            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                launchAuthenticationPrompt(
                    activity = activity,
                    authenticator = authenticatorInCaseOfError,
                    onSuccessfulAuthentication = onSuccessfulAuthentication
                )
            }

            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                launchAuthenticationPrompt(
                    activity = activity,
                    authenticator = authenticatorInCaseOfError,
                    onSuccessfulAuthentication = onSuccessfulAuthentication
                )
            }

            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                requestBiometricActivation(activity = activity, authenticator = authenticator)
            }
        }
    }

    private fun launchAuthenticationPrompt(
        activity: MainActivity,
        authenticator: Int,
        onSuccessfulAuthentication: () -> Unit
    ) {
        val executor = ContextCompat.getMainExecutor(activity)
        val callback = object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(
                errorCode: Int,
                errString: CharSequence
            ) {
                super.onAuthenticationError(errorCode, errString)
                activity.toast(message = "Error $errString")
            }

            override fun onAuthenticationSucceeded(
                result: BiometricPrompt.AuthenticationResult
            ) {
                super.onAuthenticationSucceeded(result)
                onSuccessfulAuthentication()
                activity.toast(message = "Success")
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                activity.toast(message = "Failed")
            }
        }
        val biometricPrompt = BiometricPrompt(activity, executor, callback)
        var promptInfo: BiometricPrompt.PromptInfo

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            promptInfo = BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric Authentication")
                .setDescription("Log in using your biometric credential")
                .setAllowedAuthenticators(authenticator)
                .setNegativeButtonText("Cancel")
                .build()
        } else {
            if (authenticator != DEVICE_CREDENTIAL) {
                promptInfo = BiometricPrompt.PromptInfo.Builder()
                    .setTitle("Biometric Authentication")
                    .setDescription("Log in using your biometric credential")
                    .setAllowedAuthenticators(authenticator)
                    .setNegativeButtonText("Cancel")
                    .build()
            } else {
                promptInfo = BiometricPrompt.PromptInfo.Builder()
                    .setTitle("Biometric Authentication")
                    .setDeviceCredentialAllowed(true)
                    .build()
            }
        }

        biometricPrompt.authenticate(promptInfo)
    }

    private fun requestBiometricActivation(
        activity: MainActivity,
        authenticator: Int
    ) {
        var enrollIntent: Intent

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            enrollIntent = Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
                putExtra(Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED, authenticator)
            }
        } else {
            enrollIntent = Intent(Settings.ACTION_SECURITY_SETTINGS)
        }

        activity.startActivity(enrollIntent)
    }
}