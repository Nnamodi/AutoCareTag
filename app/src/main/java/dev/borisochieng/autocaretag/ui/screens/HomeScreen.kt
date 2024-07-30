package dev.borisochieng.autocaretag.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.borisochieng.autocaretag.R
import dev.borisochieng.autocaretag.ui.components.RecentActivityCard
import dev.borisochieng.autocaretag.ui.theme.AutoCareTheme.colorScheme
import dev.borisochieng.autocaretag.ui.theme.AutoCareTheme.shape
import dev.borisochieng.autocaretag.ui.theme.AutoCareTheme.typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToScan: () -> Unit,
    onNavigateToNotifications: () -> Unit,
    onNavigateToClient: (Client) -> Unit,
    clients: List<Client>
) {
    var showBottomSheet by remember { mutableStateOf(false) }
    val screenTitle = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = colorScheme.onBackground
            )
        ) {
            append(stringResource(R.string.auto))
        }
        withStyle(
            style = SpanStyle(
                color = colorScheme.primary
            )
        ) {
            append(stringResource(R.string.care))
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = screenTitle,
                        style = typography.title
                    )
                },
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding)
        ) {
            item {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        modifier = Modifier
                            .size(200.dp)
                            .align(Alignment.Center),
                        painter = painterResource(id = R.drawable.homescreen_image),
                        contentDescription = stringResource(
                            id = R.string.app_name
                        ),
                        contentScale = ContentScale.Fit
                    )
                }
            }

            item {
                Button(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                        .height(50.dp),
                    onClick = { showBottomSheet = true },
                    shape = shape.button,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorScheme.secondary,
                        contentColor = colorScheme.onBackground
                    ),
//                    border = BorderStroke(
//                        width = 1.dp,
//                        color = colorScheme.primary
//                    )
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Absolute.SpaceBetween
                    ) {
                        Text(
                            text = stringResource(R.string.scan_nfc),
                            style = typography.bodyLarge
                        )
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_wifi),
                            contentDescription = stringResource(
                                id = R.string.scan_nfc
                            )
                        )
                    }
                }
            }

            item {
                Text(
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
                    text = stringResource(R.string.recent_activity),
                    style = typography.title
                )
            }

            items(items = clients) { client ->
                RecentActivityCard(client = client, onNavigateToClient = onNavigateToClient)


            }

        }

        ScanningBottomSheet(
            showBottomSheet = showBottomSheet,
            onDismiss = { showBottomSheet = false })


    }

}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val fakeClients = listOf(
        Client(
            name = "John Doe",
            vehicleName = "Benz E200"
        ),
        Client(
            name = "John Doe",
            vehicleName = "Benz E200"
        ),
        Client(
            name = "John Doe",
            vehicleName = "Benz E200"
        ),
        Client(
            name = "John Doe",
            vehicleName = "Benz E200"
        )
    )
    HomeScreen(
        onNavigateToScan = {},
        onNavigateToNotifications = {},
        onNavigateToClient = {},
        clients = fakeClients
    )
}