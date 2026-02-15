package com.chocolatada.keyvault.crypto

import android.security.keystore.KeyProperties
import com.chocolatada.keyvault.room.model.EncryptedPassword
import javax.crypto.Cipher
import javax.crypto.spec.GCMParameterSpec

class CryptoUtil {
    companion object {
        private val ALGORITHM = KeyProperties.KEY_ALGORITHM_AES

        private val BLOCK_MODE = KeyProperties.BLOCK_MODE_GCM

        private val PADDING = KeyProperties.ENCRYPTION_PADDING_NONE

        private val TRANSFORMATION = "$ALGORITHM/$BLOCK_MODE/$PADDING"

        fun encrypt(plainText: ByteArray): EncryptedPassword {
            //initialize the cipher in encrypt mode with the key generated via KeyStore
            val cipher = Cipher.getInstance(TRANSFORMATION)
            cipher.init(Cipher.ENCRYPT_MODE, KeyStoreUtil.getSecretKey())

            //cipher.doFinal() encrypts the text
            //during this, the cipher generates a random iv (initialization vector)
            //that vector is necessary to decrypt the text; so we save it too
            val encryptedText = cipher.doFinal(plainText)
            val iv = cipher.iv

            return EncryptedPassword(
                iv = iv,
                password = encryptedText
            )
        }

        fun decrypt(encryptedPassword: EncryptedPassword): String {
            //initialize the cipher in decrypt mode, using the iv used for encryption
            //without the iv used in encryption the decryption would be impossible
            val cipher = Cipher.getInstance(TRANSFORMATION)
            val spec = GCMParameterSpec(128, encryptedPassword.iv)

            cipher.init(Cipher.DECRYPT_MODE, KeyStoreUtil.getSecretKey(), spec)

            return String(bytes = cipher.doFinal(encryptedPassword.password))
        }
    }
}