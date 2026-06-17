package com.dhruva.sharepreferencedatastore

import android.util.Log
import androidx.datastore.core.Serializer
import com.google.crypto.tink.Aead
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.OutputStream

class EncryptedSerializer(private val aead: Aead) : Serializer<String> {
    override val defaultValue: String = ""

    override suspend fun readFrom(input: InputStream): String {
        val encryptedBytes = input.readBytes()
        if (encryptedBytes.isEmpty()) return defaultValue

        Log.d("tag", encryptedBytes.contentToString())

        val decryptedBytes = aead.decrypt(encryptedBytes, null)
        return String(decryptedBytes, Charsets.UTF_8)
    }

    override suspend fun writeTo(t: String, output: OutputStream) {
        val encryptedBytes = aead.encrypt(t.toByteArray(Charsets.UTF_8), null)
        withContext(Dispatchers.IO) {
            output.write(encryptedBytes)
        }
    }
}
