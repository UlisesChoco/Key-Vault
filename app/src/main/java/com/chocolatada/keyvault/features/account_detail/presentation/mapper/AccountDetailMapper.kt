package com.chocolatada.keyvault.features.account_detail.presentation.mapper

import com.chocolatada.keyvault.crypto.CryptoUtil
import com.chocolatada.keyvault.features.account_detail.presentation.dto.AccountDetailDto
import com.chocolatada.keyvault.room.model.EncryptedPassword

fun EncryptedPassword.toAccountDetailDto(): AccountDetailDto {
    return AccountDetailDto(
        title = this.title,
        password = CryptoUtil.decrypt(
            encryptedPassword = this
        )
    )
}