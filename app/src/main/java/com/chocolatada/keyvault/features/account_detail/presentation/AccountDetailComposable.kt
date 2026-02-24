package com.chocolatada.keyvault.features.account_detail.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

@Composable
fun AccountDetailComposable(
    viewModel: AccountDetailViewModel,
    uid: Int,
    onBack: () -> Unit
) {
    val uiState = viewModel.uiState.collectAsState()
    when(uiState.value) {
        AccountDetailUiState.Loading -> Text(text = "Loading . . .")
        is AccountDetailUiState.Success -> {
            val uiState = (uiState.value as AccountDetailUiState.Success)
            var account by remember { mutableStateOf(value = uiState.account) }
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = "Account Details",
                    fontSize = 24.sp
                )
                TextField(
                    value = account.title,
                    onValueChange = { account = account.copy(title = it, password = account.password) }
                )
                TextField(
                    value = account.password,
                    onValueChange = { account = account.copy(title = account.title, password = it) }
                )
                Row {
                    Button(
                        onClick = { onBack() }
                    ) {
                        Text(text = "Cancel")
                    }
                    Button(
                        onClick = {
                            viewModel.updateAccountDetail(uid = uid, accountDetail = account)
                        }
                    ) {
                        Text(text = "Update")
                    }
                }
            }
        }
        //started crashing when implemented below ones
        AccountDetailUiState.Modifying -> {
            Text(text = "Modifying . . .")
        }
        is AccountDetailUiState.Modified -> {
            Text(text = "Modified ! ! !")
            Button(
                onClick = { onBack() }
            ) {
                Text(text = "Go back")
            }
        }
        is AccountDetailUiState.Error -> {
            val uiState = (uiState.value as AccountDetailUiState.Error)
            Text(
                text = uiState.message,
                fontSize = 12.sp
            )
        }
    }
}

