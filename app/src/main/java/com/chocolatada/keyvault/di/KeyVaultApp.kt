package com.chocolatada.keyvault.di

import android.app.Application
import androidx.room.Room
import com.chocolatada.keyvault.crypto.KeyStoreUtil
import com.chocolatada.keyvault.features.authentication.domain.IAuthenticationRepository
import com.chocolatada.keyvault.features.authentication.data.AuthenticationDataSource
import com.chocolatada.keyvault.room.KeyVaultDatabase
import com.chocolatada.keyvault.room.dao.EncryptedPasswordDao

class KeyVaultApp: Application() {
    companion object {
        lateinit var encryptedPasswordDao: EncryptedPasswordDao

        lateinit var authenticationRepository: IAuthenticationRepository
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
    }
}