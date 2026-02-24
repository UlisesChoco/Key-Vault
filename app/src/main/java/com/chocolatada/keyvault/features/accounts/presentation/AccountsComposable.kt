package com.chocolatada.keyvault.features.accounts.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import com.chocolatada.keyvault.features.accounts.presentation.dto.AccountsDto
import com.chocolatada.keyvault.toast

@Composable
fun AccountsComposable(
    modifier: Modifier = Modifier,
    viewModel: AccountsViewModel,
    onAccountDetail: (Int) -> Unit
) {
    val uiState = viewModel.uiState.collectAsState()
    when(uiState.value) {
        AccountsUiState.Loading -> {
            Text(text = "Loading . . .")
        }
        is AccountsUiState.Success -> {
            val uiStateSuccess = (uiState.value as AccountsUiState.Success)
            AccountsListComposable(
                accounts = uiStateSuccess.accounts,
                onAccountDetail = onAccountDetail
            )
        }
        is AccountsUiState.Error -> {
            val uiStateError = (uiState.value as AccountsUiState.Error)
            Text(
                text = uiStateError.message
            )
        }
    }
}

@Composable
fun AccountsListComposable(
    accounts: List<AccountsDto>,
    onAccountDetail: (Int) -> Unit
) {
    val ctx = LocalContext.current
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(items = accounts) { account ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = account.title,
                    fontSize = 12.sp
                )
                Button(
                    onClick = { onAccountDetail(account.uid) }
                ) {
                    Text(
                        text = "Account details"
                    )
                }
            }
        }
    }
}