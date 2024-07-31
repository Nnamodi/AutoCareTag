package dev.borisochieng.autocaretag.ui.manage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.borisochieng.autocaretag.R
import dev.borisochieng.autocaretag.ui.components.ClientCard
import dev.borisochieng.autocaretag.ui.components.ScreenTitle
import dev.borisochieng.autocaretag.ui.manage.components.ClientSearchBar
import dev.borisochieng.autocaretag.ui.screens.Client
import dev.borisochieng.autocaretag.ui.theme.AutoCareTheme.colorScheme
import dev.borisochieng.autocaretag.ui.theme.AutoCareTheme.typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientScreen(
    onNavigateUp: () -> Unit,
    onNavigateToClient: (Client) -> Unit,
    viewModel: ClientScreenViewModel
) {

    var searchQuery by remember {
        mutableStateOf("")
    }
    val clientsUIState by viewModel.clientUiState.collectAsState()


    Scaffold(
        modifier = Modifier.background(colorScheme.background),
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(
                            painter = painterResource(id = R.drawable.arrow_back_ios),
                            contentDescription = "Back Arrow"
                        )
                    }
                },
                title = {
                    ScreenTitle()
                },
            )
        }

    ) {

        Column(
            modifier = Modifier.padding(it), horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

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

            LazyColumn(contentPadding = PaddingValues(top = 16.dp, start = 8.dp, end = 8.dp)) {

                item {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        text = " Clients",
                        style = typography.bodyLarge
                    )
                }

                if (clientsUIState.clients.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier.fillMaxSize(),
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
                    items(clientsUIState.clients) { client ->
                        ClientCard(client = client, onNavigateToClient = onNavigateToClient)
                    }
                }
            }
        }
    }
}