package com.catalin.library

import android.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object EncryptionUtils {
    
    private val idVector: ByteArray = byteArrayOf(0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 0, 0, 0, 0, 1)
    private val ivSpec = IvParameterSpec(idVector)
    private val secretKeySpec = SecretKeySpec(BuildConfig.ENCODE_KEY.toByteArray(), ALGORITHM_AES)
    private val cipher = Cipher.getInstance(CIPHER_TRANSFORMATION)

    fun encrypt(message: String): String {
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivSpec)
        return Base64.encodeToString(cipher.doFinal(), Base64.NO_WRAP)
    }

    fun decrypt(encryptedMessage: String): String {
        val decodedBase64Message = Base64.decode(encryptedMessage, Base64.NO_WRAP)
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivSpec)
        return String(cipher.doFinal(decodedBase64Message))
    }
}