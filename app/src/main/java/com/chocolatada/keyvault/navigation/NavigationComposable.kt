package com.chocolatada.keyvault.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.chocolatada.keyvault.MainActivity
import com.chocolatada.keyvault.di.KeyVaultApp
import com.chocolatada.keyvault.di.factory.AccountDetailViewModelFactory
import com.chocolatada.keyvault.di.factory.AccountsViewModelFactory
import com.chocolatada.keyvault.di.factory.AuthenticationViewModelFactory
import com.chocolatada.keyvault.features.account_detail.presentation.AccountDetailComposable
import com.chocolatada.keyvault.features.account_detail.presentation.AccountDetailViewModel
import com.chocolatada.keyvault.features.accounts.presentation.AccountsComposable
import com.chocolatada.keyvault.features.accounts.presentation.AccountsViewModel
import com.chocolatada.keyvault.features.authentication.presentation.AuthenticationComposable
import com.chocolatada.keyvault.features.authentication.presentation.AuthenticationViewModel

@Composable
fun NavigationComposable(
    modifier: Modifier = Modifier,
    activity: MainActivity
) {
    val backStack = rememberNavBackStack(elements = arrayOf(AuthenticationScreen))

    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {
            entry<AuthenticationScreen> {
                val viewModel = viewModel<AuthenticationViewModel>(
                    factory = AuthenticationViewModelFactory(
                        authenticationRepository = KeyVaultApp.authenticationRepository
                    )
                )
                AuthenticationComposable(
                    viewModel = viewModel,
                    activity = activity,
                    onAuthenticated = {
                        backStack.clear()
                        backStack.add(AccountsScreen)
                    }
                )
            }
            entry<AccountsScreen> {
                val viewModel = viewModel<AccountsViewModel>(
                    factory = AccountsViewModelFactory(
                        accountsRepository = KeyVaultApp.accountsRepository
                    )
                )
                AccountsComposable(
                    viewModel = viewModel,
                    onAccountDetail = { uid ->
                        backStack.add(AccountDetailScreen(uid))
                    }
                )
            }
            entry<AccountDetailScreen> { data ->
                val viewModel = viewModel<AccountDetailViewModel>(
                    factory = AccountDetailViewModelFactory(
                        accountDetailRepository = KeyVaultApp.accountDetailRepository
                    )
                )
                viewModel.loadAccountDetail(data.uid)
                AccountDetailComposable(
                    viewModel = viewModel,
                    uid = data.uid,
                    onBack = { backStack.removeLastOrNull() }
                )
            }
        }
    )
}