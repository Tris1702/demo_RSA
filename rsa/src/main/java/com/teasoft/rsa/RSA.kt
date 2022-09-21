package com.teasoft.rsa

import android.os.Build
import androidx.annotation.RequiresApi
import java.security.*
import java.security.spec.X509EncodedKeySpec
import java.util.*
import javax.crypto.Cipher

@RequiresApi(Build.VERSION_CODES.O)

class RSA(private val publicKey: String){
    fun encrypt(message: String): String{
        val messageToBytes = message.toByteArray()
        val cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
        cipher.init(Cipher.ENCRYPT_MODE, loadPublicKey(publicKey))
        val encryptedBytes = cipher.doFinal(messageToBytes)
        return encode(encryptedBytes)
    }
    fun encode(data: ByteArray): String = Base64.getEncoder().encodeToString(data)
    fun loadPublicKey(stored: String): Key {
        val data = Base64.getDecoder().decode(stored.toByteArray())
        val spec = X509EncodedKeySpec(data)
        val fact: KeyFactory = KeyFactory.getInstance("RSA")
        return fact.generatePublic(spec)
    }
}
