package com.dhruva.machinecodingproblems

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import kotlin.time.Duration.Companion.milliseconds

@SuppressLint("FlowOperatorInvokedInComposition")
@OptIn(FlowPreview::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SearchScreen() {
    var searchQuery by rememberSaveable { mutableStateOf("") }

    // 1. Convert the searchQuery state into a flow so we can use operators like debounce
    val queryFlow = remember {
        snapshotFlow() { searchQuery }
            .debounce(300.milliseconds)
            .distinctUntilChanged()
            .flowOn(Dispatchers.Default)
    }

    // 2. Data source as a flow - fetch on IO thread
    val listFlow = remember {
        flow {
            emit(populateList(100))
        }.flowOn(Dispatchers.IO)
    }

    // 3. Combine both flows to produce the filtered list - filter on Default thread
    val filteredListState = remember {
        combine(queryFlow, listFlow) { query, list ->
            if (query.isEmpty()) {
                list
            } else {
                list.filter { it.contains(query, ignoreCase = true) }
            }
        }
    }.collectAsState(emptyList())

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Search") },
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        ListComponent(filteredListState)
    }
}

@Composable
fun ListComponent(listState: State<List<String>>) {
    LazyColumn {
        items(listState.value) { item ->
            ListItem(itemName = item)
        }
    }
}

@Preview
@Composable
fun ListItem(itemName: String = "item1") {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .background(White),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = itemName,
            modifier = Modifier.padding(8.dp),
            style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold
        )
    }
}

suspend fun populateList(count: Int): List<String> = withContext(Dispatchers.IO) {
    List(count) { "Item ${it + 1}" }
}
