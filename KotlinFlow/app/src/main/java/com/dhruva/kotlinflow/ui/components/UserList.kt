package com.dhruva.kotlinflow.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.dhruva.kotlinflow.model.UserItem

// reusable user list composable
@Composable
fun UserList(
    users: List<UserItem>
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(
            items = users,
            key = { it.id },
        ) { user ->
            UserListItem(user = user)
        }
    }
}
