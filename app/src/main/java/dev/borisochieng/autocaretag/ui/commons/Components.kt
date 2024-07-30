package dev.borisochieng.autocaretag.ui.commons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.borisochieng.autocaretag.nfc_writer.presentation.viewModel.InfoScreenEvents

@Composable
fun Avatar(
	name: String,
	modifier: Modifier = Modifier,
	avatarTextSize: TextUnit = 15.sp
) {
	Box(
		modifier
			.clip(CircleShape)
			.background(Color.Blue)
	) {
		Text(
			text = name.take(1),
			modifier = Modifier.padding(12.dp),
			color = Color.Black,
			fontSize = avatarTextSize,
			fontWeight = FontWeight.SemiBold,
			letterSpacing = 0.sp
		)
	}
}

@Composable
fun CustomTextField(
	header: String,
	value: String,
	placeholder: String,
	modifier: Modifier = Modifier,
	onValueChange: (String) -> Unit
) {
	Column(modifier) {
		Text(header, Modifier.padding(bottom = 8.dp))
		OutlinedTextField(
			value = value,
			onValueChange = onValueChange,
			modifier = Modifier.fillMaxWidth(),
			placeholder = {
				Text(
					text = placeholder,
					style = TextStyle(
						fontSize = 12.sp,
						fontWeight = FontWeight(400),
						color = Color(0xFF9D9D9D)
					)
				)
			}
		)
	}
}