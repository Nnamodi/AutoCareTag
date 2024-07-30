package dev.borisochieng.autocaretag.ui.manage.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.borisochieng.autocaretag.R
import dev.borisochieng.autocaretag.ui.manage.Vehicle
import dev.borisochieng.autocaretag.ui.screens.Client
import dev.borisochieng.autocaretag.ui.theme.AutoCareTheme.shape
import dev.borisochieng.autocaretag.ui.theme.AutoCareTheme.typography

@Composable
fun ManageVehicleCard(
    modifier: Modifier = Modifier,
    vehicle: Vehicle,
    onNavigateToClient: (Vehicle) -> Unit
) {
    Card(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .clip(shape.card),
        shape = shape.card,
        colors = CardDefaults.cardColors(Color.Transparent)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = CircleShape
                    )
                    .size(40.dp)
                    .clip(CircleShape)
                    .border(
                        width = 3.dp,
                        color = Color.Transparent,
                        shape = CircleShape
                    )

            ) {
                Text(
                    text = if(vehicle.vehicleName!="") "${vehicle.vehicleName[0].uppercaseChar()}" else ""
                )
            }

            Column(
                modifier = Modifier
                    .align(Alignment.CenterVertically)

            ) {
                Text(
                    modifier = Modifier.padding(vertical = 4.dp),
                    text = vehicle.vehicleName,
                    style = typography.bodyLarge
                )
                Text(
                    modifier = Modifier.padding(vertical = 2.dp),
                    text = vehicle.licencePlate,
                    style = typography.bodyLight
                )
            }
            Spacer(modifier = Modifier.weight(1f))

            Text(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterVertically)
                    .clickable { onNavigateToClient(vehicle) },
                text = stringResource(R.string.view_details),
                style = typography.bodyLight
            )
        }

    }
}