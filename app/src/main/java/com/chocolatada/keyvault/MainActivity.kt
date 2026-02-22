package com.chocolatada.keyvault

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.chocolatada.keyvault.crypto.CryptoUtil
import com.chocolatada.keyvault.di.KeyVaultApp
import com.chocolatada.keyvault.navigation.NavigationComposable
import com.chocolatada.keyvault.ui.theme.KeyVaultTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KeyVaultTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val coroutine = rememberCoroutineScope()
                    LaunchedEffect(null) {
                        coroutine.launch(context = Dispatchers.IO) {
                            //will delete later. just for test purposes d:
                            if(KeyVaultApp.encryptedPasswordDao.getAll().isEmpty()) {
                                val password = "password!".toByteArray()
                                val account1 = CryptoUtil.encrypt(
                                    plainText = password
                                )
                                val account2 = CryptoUtil.encrypt(
                                    plainText = password
                                )
                                val account3 = CryptoUtil.encrypt(
                                    plainText = password
                                )
                                account1.title = "First Account"
                                account2.title = "Second Account"
                                account3.title = "Third Account"
                                KeyVaultApp.encryptedPasswordDao.insertAll(
                                    account1, account2, account3
                                )
                            }
                        }
                    }
                    NavigationComposable(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues = innerPadding),
                        activity = this
                    )
                }
            }
        }
    }
}

//nice extension fun
//i'll probably move it to other file later
fun Context.toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}