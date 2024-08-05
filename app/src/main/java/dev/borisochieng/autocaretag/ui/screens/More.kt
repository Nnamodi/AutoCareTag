package dev.borisochieng.autocaretag.ui.screens

import android.content.Context
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.borisochieng.autocaretag.R
import dev.borisochieng.autocaretag.ui.components.ScreenTitle
import dev.borisochieng.autocaretag.ui.theme.AutoCareTagTheme
import dev.borisochieng.autocaretag.ui.theme.AutoCareTheme.colorScheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoreScreen() {
	Scaffold(
		containerColor = colorScheme.background,
		topBar = { TopAppBar(title = { ScreenTitle() }) }
	) { paddingValues ->
		Column(
			Modifier
				.fillMaxSize()
				.padding(paddingValues)
		) {
			MenuOptions.entries.forEach { menu ->
				MenuItem(
					painter = painterResource(menu.iconRes),
					label = stringResource(menu.labelRes)
				)
			}
		}
	}
}

@Composable
private fun MenuItem(
	painter: Painter,
	label: String,
	modifier: Modifier = Modifier
) {
	val context = LocalContext.current

	Column(
		modifier = Modifier.padding(16.dp, 8.dp)
	) {

		Row(
			modifier = modifier
				.fillMaxWidth()
				.clickable { showToast(R.string.coming_soon, context) },
			verticalAlignment = Alignment.CenterVertically
		) {
			Icon(
				painter = painter,
				contentDescription = label,
				modifier = Modifier.padding(end = 10.dp),
				tint = Color.DarkGray
			)
			Text(text = label)

			Spacer(modifier = Modifier.weight(1f))

			Icon(
				painter = painterResource(id = R.drawable.ic_arrow_forward),
				contentDescription = label,
				modifier = Modifier.padding(8.dp),
				tint = Color.Gray
			)
		}
	}

	HorizontalDivider(
        modifier = Modifier.padding(horizontal = 16.dp)
    )
}

private fun showToast(
	@StringRes stringRes: Int,
	context: Context,
	text: String? = null
) {
	Toast.makeText(
		context,
		text ?: context.getString(stringRes),
		Toast.LENGTH_SHORT
	).show()
}

private enum class MenuOptions(
	@StringRes val labelRes: Int,
	@DrawableRes val iconRes: Int
) {
	Help(
		R.string.help,
		R.drawable.help_icon
	),
	AboutUs(
		R.string.about,
		R.drawable.ic_user
	),
	Rate(
		R.string.rate_app,
		R.drawable.rate_logo
	)
}

@Preview(showBackground = true)
@Composable
fun MoreScreenPreview() {
	AutoCareTagTheme {
		MoreScreen()
	}
}
