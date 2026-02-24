package com.chocolatada.keyvault.features.account_detail.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chocolatada.keyvault.crypto.CryptoUtil
import com.chocolatada.keyvault.features.account_detail.domain.IAccountDetailRepository
import com.chocolatada.keyvault.features.account_detail.presentation.dto.AccountDetailDto
import com.chocolatada.keyvault.features.account_detail.presentation.mapper.toAccountDetailDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AccountDetailViewModel(
    private val accountDetailRepository: IAccountDetailRepository
): ViewModel() {
    private val _uiState = MutableStateFlow<AccountDetailUiState>(
        value = AccountDetailUiState.Loading
    )
    val uiState = _uiState.asStateFlow()

    fun loadAccountDetail(uid: Int) {
        viewModelScope.launch(context = Dispatchers.IO) {
            runCatching {
                accountDetailRepository.getAccount(uid).toAccountDetailDto()
            }.onSuccess { account ->
                _uiState.value = AccountDetailUiState.Success(account)
            }.onFailure {
                _uiState.value = AccountDetailUiState.Error(it.message ?: "Unexpected error!")
            }
        }
    }

    fun updateAccountDetail(uid: Int, accountDetail: AccountDetailDto) {
        viewModelScope.launch(context = Dispatchers.IO) {
            runCatching {
                _uiState.value = AccountDetailUiState.Modifying

                val encryptedPassword = accountDetailRepository.getAccount(uid)
                val newAccount = CryptoUtil.encrypt(
                    plainText = accountDetail.password.toByteArray()
                )

                encryptedPassword.title = accountDetail.title
                encryptedPassword.password = newAccount.password
                accountDetailRepository.updateAccount(encryptedPassword)

                return@runCatching accountDetailRepository.getAccount(uid)
            }.onSuccess { account ->
                _uiState.value = AccountDetailUiState.Modified(
                    account.toAccountDetailDto()
                )
            }.onFailure {
                _uiState.value = AccountDetailUiState.Error("Unexpected error!")
            }
        }
    }
}