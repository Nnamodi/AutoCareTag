package dev.borisochieng.autocaretag.ui.navigation

import androidx.navigation.NavHostController

class NavActions(private val navController: NavHostController) {

    fun navigate(screen: Screens) {
        when (screen) {
            Screens.HomeScreen -> navigateToHomeScreen()
//            Screens.ReadStatusScreen -> navigateToReadStatusScreen()
            Screens.AddScreen -> navigateToAddScreen()
//            Screens.WriteStatusScreen -> navigateToWriteStatusScreen()
            Screens.ManageScreen -> navigateToManageScreen()
            Screens.MoreScreen -> navigateToMoreScreen()
            Screens.ClientAddedScreen -> navigateToClientAddedScreen()
            is Screens.ScanningScreen -> navigateToScanningScreen(screen.fromWriteScreen)
            is Screens.ClientDetailsScreen -> navigateToClientDetailsScreen(screen.clientId)
            Screens.Back -> navController.navigateUp()
        }
    }

    private fun navigateToHomeScreen() {
        navController.navigate(AppRoute.HomeScreen.route)
    }

//    private fun navigateToReadStatusScreen() {
//        navController.navigate(AppRoute.ReadStatusScreen.route)
//    }

    private fun navigateToAddScreen() {
        navController.navigate(AppRoute.AddScreen.route) {
            popUpTo(AppRoute.HomeScreen.route) {
                inclusive
            }
            launchSingleTop = true
        }
    }

//    private fun navigateToWriteStatusScreen() {
//        navController.navigate(AppRoute.WriteStatusScreen.route) {
//            popUpTo(AppRoute.HomeScreen.route) {
//                inclusive
//            }
//            launchSingleTop = true
//        }
//    }

    private fun navigateToManageScreen() {
        navController.navigate(AppRoute.ManageScreen.route)
    }

    private fun navigateToMoreScreen() {
        navController.navigate(AppRoute.MoreScreen.route)
    }
    private fun navigateToClientAddedScreen() {
        navController.navigate(AppRoute.ClientAddedScreen.route) {
            popUpTo(AppRoute.HomeScreen.route) {
                inclusive
            }
            launchSingleTop = true
        }
    }

    private fun navigateToScanningScreen(fromWriteScreen: Boolean) {
        navController.navigate(
            AppRoute.ScanningScreen.routeWithValue(fromWriteScreen)
        ) {
            popUpTo(AppRoute.HomeScreen.route) {
                inclusive
            }
            launchSingleTop = true
        }
    }

    private fun navigateToClientDetailsScreen(clientId: String) {
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
//    data object ReadStatusScreen : AppRoute("read_status_screen")
    data object AddScreen : AppRoute("add_screen")
//    data object WriteStatusScreen : AppRoute("write_status_screen")
    data object ManageScreen : AppRoute("manage_screen")
    data object MoreScreen : AppRoute("more_screen")
    data object ClientAddedScreen: AppRoute("client_added_screen")
    data object ScanningScreen: AppRoute("scanning_screen/{fromWriteScreen}") {
        fun routeWithValue(fromWriteScreen: Boolean) = String.format("scanning_screen/%b", fromWriteScreen)
    }
    data object ClientDetailsScreen : AppRoute("client_details_screen/{clientId}") {
        fun routeWithId(clientId: String) = String.format("client_details_screen/%s", clientId)
    }
}

sealed class Screens {
    data object HomeScreen : Screens()
//    data object ReadStatusScreen : Screens()
    data object AddScreen : Screens() // This is the `add client` screen
//    data object WriteStatusScreen : Screens()
    data object ManageScreen : Screens()
    data object MoreScreen : Screens()
    data class ClientDetailsScreen(val clientId: String) : Screens()
    data object ClientAddedScreen: Screens()
    data class ScanningScreen(val fromWriteScreen: Boolean): Screens()
    data object Back : Screens()
}
