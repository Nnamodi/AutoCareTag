package dev.borisochieng.autocaretag.ui.manage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.borisochieng.autocaretag.R
import dev.borisochieng.autocaretag.ui.manage.components.ManageSearchBar
import dev.borisochieng.autocaretag.ui.manage.components.ManageVehicleCard
import dev.borisochieng.autocaretag.ui.screens.Client
import dev.borisochieng.autocaretag.ui.theme.AutoCareTheme.shape
import dev.borisochieng.autocaretag.ui.theme.AutoCareTheme.typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageScreen() {

    Scaffold(
        topBar = {

            TopAppBar(
                navigationIcon = {
                    Icon(painter = painterResource(id = R.drawable.arrow_back_ios), contentDescription ="Back Arrow" )
                },
                title = {
                    Text(
                        text = "Manage",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight(500),
                            color = Color(0xFF393938),
                        )
                    )
                },
            )
        }

    ) { it ->

       Column(modifier = Modifier.padding(it), horizontalAlignment = Alignment.CenterHorizontally,
           verticalArrangement = Arrangement.Center) {

               Box(
                   modifier = Modifier.fillMaxWidth(),
                   contentAlignment = Alignment.Center
               ) {
                   ManageSearchBar()
               }

            LazyColumn(contentPadding = PaddingValues(top = 16.dp, start = 8.dp, end = 8.dp)) {

                item {
                    Text(
                        text = " Client Vehicles",

                        // Body/SemiBold/Normal
                        style = TextStyle(
                            fontSize = 16.sp,
                            lineHeight = 24.sp,
                            fontWeight = FontWeight(600),
                            color = Color(0xFF393938),
                        )
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                }
                items(1) {
                    ManageVehicleCard(vehicle = Vehicle("Mazda", "DS-3432")) {

                    }
                }
            }
        }
    }
}