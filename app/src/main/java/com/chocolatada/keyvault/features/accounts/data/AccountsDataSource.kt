package com.chocolatada.keyvault.features.accounts.data

import com.chocolatada.keyvault.features.accounts.domain.IAccountsRepository
import com.chocolatada.keyvault.room.dao.EncryptedPasswordDao
import com.chocolatada.keyvault.room.model.EncryptedPassword

class AccountsDataSource(
    private val encryptedPasswordDao: EncryptedPasswordDao
): IAccountsRepository {
    override fun getAll(): List<EncryptedPassword> = encryptedPasswordDao.getAll()
}