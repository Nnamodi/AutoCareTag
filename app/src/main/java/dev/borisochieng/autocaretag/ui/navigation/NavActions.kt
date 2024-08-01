package dev.borisochieng.autocaretag.ui.navigation

import androidx.navigation.NavHostController

class NavActions(private val navController: NavHostController) {

    fun navigate(screen: Screens) {
        when (screen) {
            Screens.HomeScreen -> navigateToHomeScreen()
            Screens.AddScreen -> navigateToAddScreen()
            Screens.ManageScreen -> navigateToManageScreen()
            Screens.MoreScreen -> navigateToMoreScreen()
            is Screens.ClientDetailsScreen -> navigateToClientDetailsScreen(screen.clientId)
            Screens.Back -> navController.navigateUp()
        }
    }

    private fun navigateToHomeScreen() {
        navController.navigate(AppRoute.HomeScreen.route)
    }

    private fun navigateToAddScreen() {
        navController.navigate(AppRoute.AddScreen.route) {
            popUpTo(AppRoute.HomeScreen.route) {
                inclusive
            }
            launchSingleTop = true
        }
    }

    private fun navigateToManageScreen() {
        navController.navigate(AppRoute.ManageScreen.route)
    }

    private fun navigateToMoreScreen() {
        navController.navigate(AppRoute.MoreScreen.route)
    }

    private fun navigateToClientDetailsScreen(clientId: Long) {
        navController.navigate(
            AppRoute.ClientDetailsScreen.routeWithId(clientId)
        ) {
            popUpTo(AppRoute.ManageScreen.route) {
                inclusive
            }
            launchSingleTop = true
        }
    }

}

sealed class AppRoute(val route: String) {
    data object HomeScreen : AppRoute("home_screen")
    data object AddScreen : AppRoute("add_screen")
    data object ManageScreen : AppRoute("manage_screen")
    data object MoreScreen : AppRoute("more_screen")
    data object ClientDetailsScreen : AppRoute("client_details_screen/{clientId}") {
        fun routeWithId(clientId: Long) = String.format("client_details_screen/%s", clientId)
    }
}

sealed class Screens {
    data object HomeScreen : Screens()
    data object AddScreen : Screens() // This is the `add client` screen
    data object ManageScreen : Screens()
    data object MoreScreen : Screens()
    data class ClientDetailsScreen(val clientId: Long) : Screens()
    data object Back : Screens()
}
