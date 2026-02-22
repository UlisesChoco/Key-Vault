package com.chocolatada.keyvault.features.accounts.domain

import com.chocolatada.keyvault.room.model.EncryptedPassword

interface IAccountsRepository {
    fun getAll(): List<EncryptedPassword>
}