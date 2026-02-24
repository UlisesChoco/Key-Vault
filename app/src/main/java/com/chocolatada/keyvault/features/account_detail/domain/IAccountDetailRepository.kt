package com.chocolatada.keyvault.features.account_detail.domain

import com.chocolatada.keyvault.room.model.EncryptedPassword

interface IAccountDetailRepository {
    fun getAccount(uid: Int): EncryptedPassword
    fun updateAccount(encryptedPassword: EncryptedPassword)
}