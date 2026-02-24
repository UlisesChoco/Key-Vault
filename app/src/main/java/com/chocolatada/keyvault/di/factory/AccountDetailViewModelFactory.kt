package com.chocolatada.keyvault.di.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.chocolatada.keyvault.features.account_detail.domain.IAccountDetailRepository
import com.chocolatada.keyvault.features.account_detail.presentation.AccountDetailViewModel

class AccountDetailViewModelFactory(
    private val accountDetailRepository: IAccountDetailRepository,
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AccountDetailViewModel(
            accountDetailRepository = accountDetailRepository
        ) as T
    }
}