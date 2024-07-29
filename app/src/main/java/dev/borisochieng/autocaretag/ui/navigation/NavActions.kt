package dev.borisochieng.autocaretag.ui.navigation

import androidx.navigation.NavHostController

class NavActions(private val navController: NavHostController) {

	fun navigate(screen: Screens) {
		when (screen) {
			Screens.HomeScreen -> navigateToHomeScreen()
			Screens.AddScreen -> navigateToAddScreen()
			is Screens.AddRepairDetailsScreen -> navigateToAddRepairDetailsScreen(screen.vehicleId)
			Screens.ManageScreen -> navigateToManageScreen()
			Screens.ClientDetailsScreen -> navigateToClientDetailsScreen()
			is Screens.VehicleDetailsScreen -> navigateToVehicleDetailsScreen(screen.clientId)
			is Screens.RepairHistoryScreen -> navigateToRepairHistoryScreen(screen.vehicleId)
			Screens.Back -> navController.navigateUp()
		}
	}

	private fun navigateToHomeScreen() {
		navController.navigate(AppRoute.HomeScreen.route)
	}

	private fun navigateToAddScreen() {
		navController.navigate(AppRoute.AddScreen.route)
	}

	private fun navigateToAddRepairDetailsScreen(vehicleId: String) {
		navController.navigate(
			AppRoute.AddRepairDetailsScreen.routeWithId(vehicleId)
		)
	}

	private fun navigateToManageScreen() {
		navController.navigate(AppRoute.ManageScreen.route)
	}

	private fun navigateToClientDetailsScreen() {
		navController.navigate(AppRoute.ClientDetailsScreen.route)
	}

	private fun navigateToVehicleDetailsScreen(clientId: String) {
		navController.navigate(
			AppRoute.VehicleDetailsScreen.routeWithId(clientId)
		)
	}

	private fun navigateToRepairHistoryScreen(vehicleId: String) {
		navController.navigate(
			AppRoute.RepairHistoryScreen.routeWithId(vehicleId)
		)
	}

}

sealed class AppRoute(val route: String) {
	data object HomeScreen: AppRoute("home_screen")
	data object AddScreen: AppRoute("add_screen")
	data object AddRepairDetailsScreen: AppRoute("add_repair_details_screen/{vehicleId}") {
		fun routeWithId(vehicleId: String) = String.format("add_repair_details_screen/%s", vehicleId)
	}
	data object ManageScreen: AppRoute("manage_screen")
	data object ClientDetailsScreen: AppRoute("client_details_screen")
	data object VehicleDetailsScreen: AppRoute("vehicle_details_screen/{clientId}") {
		fun routeWithId(clientId: String) = String.format("vehicle_details_screen/%s", clientId)
	}
	data object RepairHistoryScreen: AppRoute("repair_history_screen/{vehicleId}") {
		fun routeWithId(vehicleId: String) = String.format("repair_details_screen/%s", vehicleId)
	}
}

sealed class Screens {
	data object HomeScreen : Screens()
	data object AddScreen : Screens() // This is the `add client` screen
	data class AddRepairDetailsScreen(val vehicleId: String) : Screens()
	data object ManageScreen : Screens()
	data object ClientDetailsScreen : Screens()
	data class VehicleDetailsScreen(val clientId: String) : Screens()
	data class RepairHistoryScreen(val vehicleId: String) : Screens()
	data object Back : Screens()
}
