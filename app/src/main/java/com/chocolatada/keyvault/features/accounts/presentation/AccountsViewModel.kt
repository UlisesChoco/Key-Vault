package com.chocolatada.keyvault.features.accounts.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chocolatada.keyvault.features.accounts.domain.IAccountsRepository
import com.chocolatada.keyvault.features.accounts.presentation.mapper.toAccountsDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AccountsViewModel(
    private val accountsRepository: IAccountsRepository
): ViewModel() {
    private val _uiState = MutableStateFlow<AccountsUiState>(
        value = AccountsUiState.Loading
    )
    val uiState = _uiState.asStateFlow()

    init { loadAccounts() }

    private fun loadAccounts() {
        viewModelScope.launch(context = Dispatchers.IO) {
            runCatching {
                accountsRepository.getAll().map { encryptedPassword ->
                    encryptedPassword.toAccountsDto()
                }
            }.onSuccess { accountsDto ->
                _uiState.value = AccountsUiState.Success(accountsDto)
            }.onFailure {
                _uiState.value = AccountsUiState.Error("Unexpected error!")
            }
        }
    }
}