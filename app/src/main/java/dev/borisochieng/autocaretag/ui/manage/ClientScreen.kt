package dev.borisochieng.autocaretag.ui.manage

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.borisochieng.autocaretag.ui.components.ClientCard
import dev.borisochieng.autocaretag.ui.components.ScreenTitle
import dev.borisochieng.autocaretag.ui.manage.components.ClientSearchBar
import dev.borisochieng.autocaretag.ui.navigation.Screens
import dev.borisochieng.autocaretag.ui.theme.AutoCareTheme.colorScheme
import dev.borisochieng.autocaretag.ui.theme.AutoCareTheme.typography

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ClientScreen(
    viewModel: ClientScreenViewModel,
    navigate: (Screens) -> Unit
) {

    var searchQuery by remember {
        mutableStateOf("")
    }
    val clientsUIState by viewModel.clientUiState.collectAsState()

    val lazyListState = rememberLazyListState()


    Scaffold(
        containerColor = colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    ScreenTitle()
                },
            )
        }

    ) {
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .padding(vertical = 8.dp),
            state = lazyListState
        ) {
            stickyHeader {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    ClientSearchBar(
                        query = searchQuery,
                        onQueryChange = { newQuery ->
                            searchQuery = newQuery
                            viewModel.searchClient(newQuery)
                        }
                    )
                }
            }

            if (clientsUIState.clients.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            modifier = Modifier.align(Alignment.Center),
                            text = "No clients found",
                            style = typography.bodyLarge
                        )

                    }
                }
            } else {
                items(clientsUIState.clients, key = { client -> client.clientId }) { client ->
                    ClientCard(
                        client = client,
                        navigate = {
                            navigate(Screens.ClientDetailsScreen(client.clientId))
                        }
                    )
                }
            }
        }
    }
}