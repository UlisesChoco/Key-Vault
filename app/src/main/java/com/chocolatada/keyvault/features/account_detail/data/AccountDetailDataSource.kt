package com.chocolatada.keyvault.features.account_detail.data

import com.chocolatada.keyvault.features.account_detail.domain.IAccountDetailRepository
import com.chocolatada.keyvault.room.dao.EncryptedPasswordDao
import com.chocolatada.keyvault.room.model.EncryptedPassword

class AccountDetailDataSource(
    private val encryptedPasswordDao: EncryptedPasswordDao
): IAccountDetailRepository {
    override fun getAccount(uid: Int): EncryptedPassword = encryptedPasswordDao.getById(uid)
    override fun updateAccount(encryptedPassword: EncryptedPassword) {
        encryptedPasswordDao.update(encryptedPassword)
    }
}