package com.chocolatada.keyvault.features.accounts.presentation.mapper

import com.chocolatada.keyvault.features.accounts.presentation.dto.AccountsDto
import com.chocolatada.keyvault.room.model.EncryptedPassword

fun EncryptedPassword.toAccountsDto(): AccountsDto {
    return AccountsDto(
        uid = this.uid,
        title = this.title
    )
}