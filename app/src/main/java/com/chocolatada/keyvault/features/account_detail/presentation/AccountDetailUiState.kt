package com.chocolatada.keyvault.features.account_detail.presentation

import com.chocolatada.keyvault.features.account_detail.presentation.dto.AccountDetailDto

sealed interface AccountDetailUiState {
    object Loading: AccountDetailUiState
    data class Success(val account: AccountDetailDto): AccountDetailUiState

    object Modifying: AccountDetailUiState
    data class Modified(val account: AccountDetailDto): AccountDetailUiState

    data class Error(val message: String): AccountDetailUiState
}