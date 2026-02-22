package com.chocolatada.keyvault.di.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.chocolatada.keyvault.features.accounts.domain.IAccountsRepository
import com.chocolatada.keyvault.features.accounts.presentation.AccountsViewModel

class AccountsViewModelFactory(
    private val accountsRepository: IAccountsRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AccountsViewModel(accountsRepository) as T
    }
}