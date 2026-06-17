package com.dhruva.sharepreferencedatastore

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.edit
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import com.dhruva.sharepreferencedatastore.ui.theme.SharePreferenceDataStoreTheme
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "my_ds")

val Context.secureDataStore: DataStore<String>
    get() = SecureDataStoreSingleton.getInstance(this)

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        putInSharedPreference(this)
        lifecycleScope.launch {
            putInDataStore(this@MainActivity)
        }

        setContent {
            SharePreferenceDataStoreTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    HomeScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var sharedPrefText by remember { mutableStateOf("") }
    var dataStoreText by remember { mutableStateOf("") }
    var encryptedDataStoreText by remember { mutableStateOf("") }

    var showDialog by remember { mutableStateOf(false) }
    var dialogType by remember { mutableStateOf(DialogType.SHARED_PREFERENCE) }

    if (showDialog) {
        ShowDialog(dialogType = dialogType, onDismiss = { showDialog = false })
    }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = sharedPrefText,
            modifier = Modifier.padding(8.dp),
        )

        Text(
            text = dataStoreText,
            modifier = Modifier.padding(8.dp)
        )

        Text(
            text = encryptedDataStoreText,
            modifier = Modifier.padding(8.dp)
        )

        Button(
            onClick = {
                val (prefText, prefInt) = getFromSharedPreference(context)
                sharedPrefText = "Shared Preference\nText: $prefText\nInt: $prefInt"
            },
            modifier = Modifier.padding(8.dp)
        ) {
            Text("Get Shared Preference")
        }

        Button(
            onClick = {
                dialogType = DialogType.SHARED_PREFERENCE
                showDialog = true
            },
            modifier = Modifier.padding(8.dp)
        ) {
            Text("Update Shared Preference")
        }

        Button(
            onClick = {
                scope.launch {
                    val (dsText, dsInt) = getFromDataStore(context)
                    dataStoreText = "Data Store\nText: $dsText\nInt: $dsInt"
                }
            },
            modifier = Modifier.padding(8.dp)
        ) {
            Text("Get Data Store")
        }

        Button(
            onClick = {
                dialogType = DialogType.DATA_STORE
                showDialog = true
            },
            modifier = Modifier.padding(8.dp)
        ) {
            Text("Update Data Store")
        }

        Button(
            onClick = {
                scope.launch {
                    val (dsText, dsInt) = getFromEncryptedDataStore(context)
                    encryptedDataStoreText = "Encrypted Data Store\nText: $dsText\nInt: $dsInt"
                }
            },
            modifier = Modifier.padding(8.dp)
        ) {
            Text("Get Encrypted Data Store")
        }

        Button(
            onClick = {
                dialogType = DialogType.ENCRYPTED_DATA_STORE
                showDialog = true
            },
            modifier = Modifier.padding(8.dp)
        ) {
            Text("Update Encrypted Data Store")
        }
    }
}


private fun getFromSharedPreference(context: Context): Pair<String, Int> {
    val sharedPreferences = context.getSharedPreferences("shared_pref", MODE_PRIVATE)
    val textVal = sharedPreferences.getString("sp_key", "text")
    val intVal = sharedPreferences.getInt("sp_int_key", -1)
    return Pair(textVal ?: "text", intVal)
}

private suspend fun getFromDataStore(context: Context): Pair<String, Int> {
    val data = context.dataStore.data.first()
    val textVal = data[stringPreferencesKey("ds_key")] ?: "text"
    val intVal = data[intPreferencesKey("ds_int_key")] ?: -1
    return Pair(textVal, intVal)
}

fun putInSharedPreference(context: Context, value: String = "shared preference") {
    val sharedPref = context.getSharedPreferences("shared_pref", MODE_PRIVATE)
    sharedPref.edit {
        this.putString("sp_key", value)
        this.putInt("sp_int_key", 1)
    }
}

private suspend fun putInDataStore(context: Context, value: String = "data store") {
    context.dataStore.edit {
        it[stringPreferencesKey("ds_key")] = value
        it[intPreferencesKey("ds_int_key")] = 1
    }
}

suspend fun putInEncryptedDataStore(context: Context, value: String = "encrypted data store") {
    // Writing data (Suspended function)
    context.secureDataStore.updateData { value }
}

private suspend fun getFromEncryptedDataStore(context: Context): Pair<String, Int> {
    // Reading data (Asynchronous Flow)
    val data = context.secureDataStore.data.first()
    val textVal = data
    val intVal = 1
    return Pair(textVal, intVal)
}

@Composable
fun ShowDialog(dialogType: DialogType, onDismiss: () -> Unit) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var text by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = getDialogTitle(dialogType))
        },
        text = {
            TextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Enter value") }
            )
        },
        confirmButton = {
            Button(
                onClick = {
                    when (dialogType) {
                        DialogType.DATA_STORE -> {
                            scope.launch {
                                putInDataStore(context, text)
                                onDismiss()
                            }
                        }

                        DialogType.SHARED_PREFERENCE -> {
                            putInSharedPreference(context, text)
                            onDismiss()
                        }

                        DialogType.ENCRYPTED_DATA_STORE -> {
                            scope.launch {
                                putInEncryptedDataStore(context, text)
                                onDismiss()
                            }
                        }
                    }
                }
            ) {
                Text("Update")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    SharePreferenceDataStoreTheme {
        HomeScreen()
    }
}