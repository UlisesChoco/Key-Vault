package com.chocolatada.keyvault.features.accounts.presentation

import com.chocolatada.keyvault.features.accounts.presentation.dto.AccountsDto

sealed interface AccountsUiState {
    object Loading: AccountsUiState
    data class Success(val accounts: List<AccountsDto>): AccountsUiState
    data class Error(val message: String): AccountsUiState
}