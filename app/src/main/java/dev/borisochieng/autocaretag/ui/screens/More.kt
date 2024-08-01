package dev.borisochieng.autocaretag.ui.screens

import android.content.Context
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.borisochieng.autocaretag.R
import dev.borisochieng.autocaretag.ui.components.ScreenTitle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoreScreen() {
	Scaffold(
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

	Row(
		modifier = modifier
			.fillMaxWidth()
			.clickable { showToast(R.string.coming_soon, context) }
			.padding(20.dp, 24.dp),
		verticalAlignment = Alignment.CenterVertically
	) {
		Icon(
			painter = painter,
			contentDescription = label,
			modifier = Modifier.padding(end = 10.dp)
		)
		Text(text = label)
	}
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
		R.drawable.about_us_icon
	),
	Rate(
		R.string.rate_app,
		R.drawable.rate_logo
	)
}
