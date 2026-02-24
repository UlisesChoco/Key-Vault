package com.chocolatada.keyvault.di

import android.app.Application
import androidx.room.Room
import com.chocolatada.keyvault.crypto.KeyStoreUtil
import com.chocolatada.keyvault.features.account_detail.data.AccountDetailDataSource
import com.chocolatada.keyvault.features.account_detail.domain.IAccountDetailRepository
import com.chocolatada.keyvault.features.accounts.data.AccountsDataSource
import com.chocolatada.keyvault.features.accounts.domain.IAccountsRepository
import com.chocolatada.keyvault.features.authentication.domain.IAuthenticationRepository
import com.chocolatada.keyvault.features.authentication.data.AuthenticationDataSource
import com.chocolatada.keyvault.room.KeyVaultDatabase
import com.chocolatada.keyvault.room.dao.EncryptedPasswordDao

class KeyVaultApp: Application() {
    companion object {
        lateinit var encryptedPasswordDao: EncryptedPasswordDao

        lateinit var authenticationRepository: IAuthenticationRepository

        lateinit var accountsRepository: IAccountsRepository

        lateinit var accountDetailRepository: IAccountDetailRepository
    }

    override fun onCreate() {
        super.onCreate()

        KeyStoreUtil.createKeyIfNotExists()

        val db = Room.databaseBuilder(
            context = this,
            klass = KeyVaultDatabase::class.java,
            name = "key_vault"
        ).build()

        encryptedPasswordDao = db.encryptedPasswordDao()

        authenticationRepository = AuthenticationDataSource()

        accountsRepository = AccountsDataSource(encryptedPasswordDao)

        accountDetailRepository = AccountDetailDataSource(encryptedPasswordDao)
    }
}