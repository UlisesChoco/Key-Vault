package com.chocolatada.keyvault.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.chocolatada.keyvault.room.model.EncryptedPassword

@Dao
interface EncryptedPasswordDao {
    @Insert
    fun insertAll(vararg encryptedPasswords: EncryptedPassword)

    @Update
    fun update(encryptedPassword: EncryptedPassword)

    @Query(value = "SELECT * FROM encrypted_password")
    fun getAll(): List<EncryptedPassword>

    @Query(
        value = "SELECT * FROM encrypted_password WHERE uid = :uid"
    )
    fun getById(uid: Int): EncryptedPassword
}