package dev.borisochieng.autocaretag.ui.commons

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import dev.borisochieng.autocaretag.ui.navigation.Screens
import dev.borisochieng.autocaretag.ui.theme.AutoCareTheme.colorScheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
	title: String,
	navigate: (Screens) -> Unit
) {
	TopAppBar(
		title = { Text(title) },
		navigationIcon = {
			IconButton(onClick = { navigate(Screens.Back) }) {
				Icon(
					painter = painterResource(R.drawable.back_arrow_icon),
					contentDescription = stringResource(R.string.back)
				)
			}
		}
	)
}

@Composable
fun NavBar(
	navController: NavController,
	modifier: Modifier = Modifier
) {
	val navBackStackEntry = navController.currentBackStackEntryAsState()
	val currentRoute = navBackStackEntry.value?.destination?.route
	val homeScreens = setOf(AppRoute.HomeScreen, AppRoute.ManageScreen, AppRoute.MoreScreen).map { it.route }
	val inHomeScreens = currentRoute in homeScreens
	AnimatedVisibility(
		visible = inHomeScreens,
		enter = slideInVertically(tween(durationMillis = 350, delayMillis = 1000)) { it },
		exit = ExitTransition.None
	) {
		NavBarVisuals(navController, modifier)
	}
}

@SuppressLint("RestrictedApi")
@Composable
private fun NavBarVisuals(
	navController: NavController,
	modifier: Modifier = Modifier
) {
	val navBackStackEntry = navController.currentBackStackEntryAsState()
	val currentRoute = navBackStackEntry.value?.destination?.route
	val backStack = navController.currentBackStack.collectAsState().value.map { it.destination.route }

	Row(
		modifier = modifier
			.fillMaxWidth()
			.padding(16.dp),
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
				modifier = Modifier.weight(1f),
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
	val color = if (selected) colorScheme.primary else colorScheme.onBackground

	Column(
		modifier = modifier
			.fillMaxWidth()
			.padding(horizontal = 8.dp)
			.clickable { onClick() }
			.padding(vertical = 6.dp),
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
	Clients(
		title = R.string.clients,
		icon = R.drawable.ic_user,
		route = AppRoute.ManageScreen.route
	),
	More(
		title = R.string.more,
		icon = R.drawable.more_icon,
		route = AppRoute.MoreScreen.route
	)
}
