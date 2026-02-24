package com.chocolatada.keyvault.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
object AuthenticationScreen: NavKey

@Serializable
object AccountsScreen: NavKey

@Serializable
data class AccountDetailScreen(val uid: Int): NavKey