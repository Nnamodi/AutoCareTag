package dev.borisochieng.autocaretag.nfcreader.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import dev.borisochieng.autocaretag.R
import dev.borisochieng.autocaretag.nfcreader.data.State
import dev.borisochieng.autocaretag.ui.components.PrimaryOutlinedButton
import dev.borisochieng.autocaretag.ui.components.ScreenTitle
import dev.borisochieng.autocaretag.ui.navigation.Screens
import dev.borisochieng.autocaretag.ui.theme.AutoCareTagTheme
import dev.borisochieng.autocaretag.ui.theme.AutoCareTheme.colorScheme
import dev.borisochieng.autocaretag.ui.theme.AutoCareTheme.typography
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScanningScreen(
    viewModel: NFCReaderViewModel,
    fromWriteScreen: Boolean,
    scanNFC: () -> Unit,
    navigate: (Screens) -> Unit
) {
    LaunchedEffect(Unit) { scanNFC() }

    val composition by rememberLottieComposition(
        spec =
        LottieCompositionSpec.RawRes(R.raw.scanning)
    )

    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )

    Scaffold(
        containerColor = colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    ScreenTitle()
                }
            )
        }
    ) { paddingValues ->

        val nfcReadState by viewModel.nfcReadState.collectAsState()
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (nfcReadState !is State.Error) {
                    Text(
                        text = if (nfcReadState is State.Loading) "Ready to scan" else "Successful",
                        style = typography.title,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(8.dp)
                    )
                }
                val subtext = when (nfcReadState) {
                    is State.Loading -> "Hold your device near the NFC Tag"
                    is State.Error -> (nfcReadState as State.Error).errorMessage
                    else -> "Details imported successfully"
                }
                Text(
                    text = subtext,
                    style = typography.bodyLarge,
                    modifier = Modifier.padding(8.dp)
                )
                when (nfcReadState) {
                    is State.Success -> {
                        Box(
                            modifier = Modifier
                                .padding(16.dp)
                                .size(100.dp)
                                .clip(CircleShape)
                                .background(Color.Green, shape = CircleShape)
                        ) {
                            Icon(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .size(100.dp),
                                imageVector = Icons.Rounded.Check,
                                tint = Color.White,
                                contentDescription = "Successful"
                            )
                        }
                    }

                    is State.Error -> {
                        Box(
                            modifier = Modifier
                                .padding(16.dp)
                                .size(100.dp)
                                .clip(CircleShape)
                                .background(colorScheme.error, shape = CircleShape)
                        ) {
                            Icon(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .size(100.dp),
                                imageVector = Icons.Rounded.Close,
                                tint = colorScheme.background,
                                contentDescription = "Error"
                            )
                        }
                    }

                    else -> {
                        Box(
                            modifier = Modifier
                                .padding(16.dp)
                                .size(150.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            LottieAnimation(
                                composition = composition,
                                progress = { progress },
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .fillMaxSize()
                            )
                        }
                    }
                }
            }

            if (nfcReadState is State.Success && fromWriteScreen) {
                PrimaryOutlinedButton(
                    onClick = { navigate(Screens.HomeScreen) },
                    modifier = Modifier
                        .padding(
                            start = 16.dp,
                            end = 50.dp,
                            bottom = 66.dp
                        ),
                    label = stringResource(R.string.back_to_home),
                    isEnabled = true
                )
            }

            LaunchedEffect(Unit) {
                if (nfcReadState == State.Loading || fromWriteScreen) return@LaunchedEffect
                delay(2000)
                if (nfcReadState is State.Error) {
                    navigate(Screens.Back)
                    return@LaunchedEffect
                }
                if (nfcReadState !is State.Success) return@LaunchedEffect
                val clientId = (nfcReadState as State.Success).data.customerId
                navigate(Screens.ClientDetailsScreen(clientId))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ScanningScreenPreview() {
    AutoCareTagTheme {
        ScanningScreen(viewModel(), true, {}) {}
    }
}