package dev.borisochieng.autocaretag.ui.commons

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import dev.borisochieng.autocaretag.R
import dev.borisochieng.autocaretag.ui.navigation.AppRoute
import dev.borisochieng.autocaretag.ui.theme.AutoCareTheme.colorScheme

@SuppressLint("RestrictedApi")
@Composable
fun NavBar(
	navController: NavController,
	modifier: Modifier = Modifier
) {
	val navBackStackEntry = navController.currentBackStackEntryAsState()
	val currentRoute = navBackStackEntry.value?.destination?.route
	val backStack = navController.currentBackStack.collectAsState().value.map { it.destination.route }

	Row(
		modifier = modifier
			.fillMaxWidth()
			.padding(16.dp, 6.dp),
		horizontalArrangement = Arrangement.Center,
		verticalAlignment = Alignment.CenterVertically
	) {
		NavBarItems.entries.forEach { item ->
			val inBackStack = item.route == currentRoute || item.route in backStack
			val selected = when (item) {
				NavBarItems.Home -> {
					val noHomeScreenInStack = NavBarItems.entries.filter { it != NavBarItems.Home }
						.all { it.route !in backStack }
					inBackStack && noHomeScreenInStack
				}
				else -> inBackStack
			}

			NavigationBarItem(
				selected = selected,
				icon = painterResource(item.icon),
				label = stringResource(item.title),
				onClick = {
					navController.navigate(item.route) {
						popUpTo(navController.graph.findStartDestination().id) {
							saveState = item.route != currentRoute
						}
						launchSingleTop = true
						restoreState = true
					}
				}
			)
		}
	}
}

@Composable
private fun NavigationBarItem(
	selected: Boolean,
	icon: Painter,
	label: String,
	modifier: Modifier = Modifier,
	onClick: () -> Unit
) {
	val color = if (selected) colorScheme.primary else colorScheme.onBackgroundVariant

	Column(
		modifier = modifier
			.padding(horizontal = 8.dp)
			.clickable { onClick() },
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Icon(
			painter = icon,
			contentDescription = label,
			modifier = Modifier.padding(start = 12.5.dp, end = 12.5.dp, bottom = 4.dp),
			tint = color
		)
		Text(text = label, color = color)
	}
}

private enum class NavBarItems(
	@StringRes val title: Int,
	@DrawableRes val icon: Int,
	val route: String
) {
	Home(
		title = R.string.home,
		icon = R.drawable.home_icon,
		route = AppRoute.HomeScreen.route
	),
	Add(
		title = R.string.add,
		icon = R.drawable.add_icon,
		route = AppRoute.AddScreen.route
	),
	Manage(
		title = R.string.manage,
		icon = R.drawable.manage_icon,
		route = AppRoute.ManageScreen.route
	)
}
