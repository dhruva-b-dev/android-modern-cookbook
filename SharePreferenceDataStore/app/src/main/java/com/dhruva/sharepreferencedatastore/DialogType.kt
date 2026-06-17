package com.dhruva.sharepreferencedatastore

enum class DialogType {
    DATA_STORE,
    SHARED_PREFERENCE,
    ENCRYPTED_DATA_STORE
}

fun getDialogTitle(dialogType: DialogType): String {
    return when (dialogType) {
        DialogType.DATA_STORE -> "Update Data Store"
        DialogType.SHARED_PREFERENCE -> "Update Shared Preference"
        DialogType.ENCRYPTED_DATA_STORE -> "Update Encrypted Data Store"
    }
}