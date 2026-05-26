package com.dhruva.kotlincoroutines.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.dhruva.kotlincoroutines.data.dto.UserDto

// reusable user list composable
@Composable
fun UserList(
    users: List<UserDto>
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
