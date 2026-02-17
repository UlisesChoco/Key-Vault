package com.chocolatada.keyvault.room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "encrypted_password")
data class EncryptedPassword(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "title") val title: String = "Key Vault Password",
    @ColumnInfo(name = "password") val password: ByteArray,
    @ColumnInfo(name = "iv") val iv: ByteArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as EncryptedPassword

        if (uid != other.uid) return false
        if (title != other.title) return false
        if (!password.contentEquals(other.password)) return false
        if (!iv.contentEquals(other.iv)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = uid
        result = 31 * result + title.hashCode()
        result = 31 * result + password.contentHashCode()
        result = 31 * result + iv.contentHashCode()
        return result
    }
}
