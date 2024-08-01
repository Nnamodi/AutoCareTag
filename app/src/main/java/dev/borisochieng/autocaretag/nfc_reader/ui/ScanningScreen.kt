package dev.borisochieng.autocaretag.nfc_reader.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import dev.borisochieng.autocaretag.R
import dev.borisochieng.autocaretag.ui.components.ScreenTitle
import dev.borisochieng.autocaretag.ui.theme.AutoCareTagTheme
import dev.borisochieng.autocaretag.ui.theme.AutoCareTheme.colorScheme
import dev.borisochieng.autocaretag.ui.theme.AutoCareTheme.typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScanningScreen(
      scanNFC: () -> Unit,
//    navigate: (Screens) -> Unit
) {
    scanNFC()

    val composition by rememberLottieComposition(spec =
    LottieCompositionSpec.RawRes(R.raw.scanning))

    val progress by animateLottieCompositionAsState(composition = composition, iterations = 50)

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

        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Ready to scan",
                    style = typography.title,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(8.dp)
                )
                Text(
                    text = "Hold your device near the NFC tag",
                    style = typography.bodyLarge,
                    modifier = Modifier.padding(8.dp)
                )
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

}

@Preview(showBackground = true)
@Composable
fun ScanningScreenPreview() {
    AutoCareTagTheme {
        ScanningScreen(scanNFC = {},/* {}*/)
    }
}