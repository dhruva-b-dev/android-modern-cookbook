package com.dhruva.sharepreferencedatastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile

object SecureDataStoreSingleton {
    private var instance: DataStore<String>? = null

    fun getInstance(context: Context): DataStore<String> {
        return instance ?: synchronized(this) {
            instance ?: DataStoreFactory.create(
                serializer = EncryptedSerializer(CryptoManager.getAead(context.applicationContext)),
                produceFile = { context.dataStoreFile("secure_user_data.bin") }
            ).also { instance = it }
        }
    }
}
