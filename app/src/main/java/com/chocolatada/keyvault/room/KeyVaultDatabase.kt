package com.chocolatada.keyvault.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.chocolatada.keyvault.room.dao.EncryptedPasswordDao
import com.chocolatada.keyvault.room.model.EncryptedPassword

@Database(
    entities = [EncryptedPassword::class],
    version = 1
)
abstract class KeyVaultDatabase: RoomDatabase() {
    abstract fun encryptedPasswordDao(): EncryptedPasswordDao
}