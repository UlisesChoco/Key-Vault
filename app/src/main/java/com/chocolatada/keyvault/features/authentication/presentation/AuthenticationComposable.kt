package com.chocolatada.keyvault.features.authentication.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.chocolatada.keyvault.MainActivity

@Composable
fun AuthenticationComposable(
    modifier: Modifier = Modifier,
    viewModel: AuthenticationViewModel,
    activity: MainActivity,
    onAuthenticated: () -> Unit
) {
    val authenticated by viewModel.authenticated.collectAsState()

    if(authenticated) onAuthenticated()

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Key Vault",
                fontSize = 24.sp
            )
            Text(
                text = "An app made to locally store your accounts.",
                fontSize = 16.sp
            )
            Button(
                onClick = { viewModel.authenticate(activity = activity) }
            ) {
                Text(text = "Authenticate")
            }
        }
    }
}