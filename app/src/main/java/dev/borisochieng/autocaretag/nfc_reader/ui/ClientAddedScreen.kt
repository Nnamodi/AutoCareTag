package dev.borisochieng.autocaretag.nfc_reader.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.borisochieng.autocaretag.R
import dev.borisochieng.autocaretag.ui.navigation.Screens
import dev.borisochieng.autocaretag.ui.theme.AutoCareTheme.colorScheme
import dev.borisochieng.autocaretag.ui.theme.AutoCareTheme.shape
import dev.borisochieng.autocaretag.ui.theme.AutoCareTheme.typography

@Composable
fun ClientAddedScreen(
    navigate: (Screens) -> Unit
) {
    Scaffold(
        containerColor = colorScheme.background
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
                    text = "Client Added",
                    style = typography.title,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(8.dp)
                )
                Text(
                    text = "Details updated successfully",
                    style = typography.bodyLarge,
                    modifier = Modifier.padding(8.dp)
                )
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


            OutlinedButton(
                onClick = {
                    navigate(Screens.HomeScreen)
                },
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                shape = shape.button,
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = colorScheme.background
                )
            ) {
                Text(
                    text = "Back to home",
                    style = typography.bodyLarge,
                    color = colorScheme.primary
                )
            }
        }


    }
}

@Preview(showBackground = true)
@Composable
fun ScanningScreenPreview() {
    ClientAddedScreen({})
}