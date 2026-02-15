package com.chocolatada.keyvault.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.chocolatada.keyvault.room.model.EncryptedPassword

@Dao
interface EncryptedPasswordDao {
    @Insert
    fun insertAll(vararg encryptedPasswords: EncryptedPassword)

    @Query("SELECT * FROM encrypted_password")
    fun getAll(): List<EncryptedPassword>
}