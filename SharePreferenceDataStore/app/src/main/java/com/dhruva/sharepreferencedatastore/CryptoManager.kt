package com.dhruva.sharepreferencedatastore

import android.content.Context
import com.google.crypto.tink.Aead
import com.google.crypto.tink.aead.AeadConfig
import com.google.crypto.tink.aead.AeadKeyTemplates
import com.google.crypto.tink.integration.android.AndroidKeysetManager

object CryptoManager {

    private const val KEY_SET = "master_keyset"
    private const val PREFERENCE_FILE = "master_key_preference"
    private const val KEY_URI = "android-keystore://master_key"

    init {
        AeadConfig.register()
    }

    private var aeadInstance: Aead? = null

    fun getAead(context: Context): Aead {
        return aeadInstance ?: synchronized(this) {
            aeadInstance ?: AndroidKeysetManager.Builder()
                .withSharedPref(context.applicationContext, KEY_SET, PREFERENCE_FILE)
                .withKeyTemplate(AeadKeyTemplates.AES256_GCM)
                .withMasterKeyUri(KEY_URI)
                .build()
                .keysetHandle
                .getPrimitive(Aead::class.java).also {
                    aeadInstance = it
                }
        }
    }
}
