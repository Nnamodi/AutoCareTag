package dev.borisochieng.autocaretag.ui.navigation

import android.nfc.Tag
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.borisochieng.autocaretag.nfc_reader.ui.ClientDetailsScreen
import dev.borisochieng.autocaretag.nfc_reader.ui.NFCReaderViewModel
import dev.borisochieng.autocaretag.nfc_writer.domain.TagInfo
import dev.borisochieng.autocaretag.nfc_writer.presentation.viewModel.AddInfoViewModel
import dev.borisochieng.autocaretag.ui.manage.ManageScreen
import dev.borisochieng.autocaretag.ui.screens.AddScreen
import dev.borisochieng.autocaretag.ui.screens.HomeScreen
import dev.borisochieng.autocaretag.utils.Dummies.fakeClients
import dev.borisochieng.autocaretag.utils.animatedComposable
import org.koin.androidx.compose.koinViewModel

typealias ShouldScan = Boolean

@Composable
fun AppRoute(
    navActions: NavActions,
    navController: NavHostController,
    paddingValues: PaddingValues,
    scanNfc: (ShouldScan) -> Unit,
    viewModel: AddInfoViewModel = koinViewModel(),
    tag: Tag? = null,
    setupNfc: () -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = AppRoute.HomeScreen.route,
        modifier = Modifier
            .padding(paddingValues)
    ) {
        composable(AppRoute.HomeScreen.route) {
            HomeScreen(
                onNavigateToScan = { scanNfc(true) },
                onNavigateToNotifications = { /*TODO*/ },
                onNavigateToClient = {
                    navActions.navigate(Screens.ClientDetailsScreen("client_id"))
                },
                clients = fakeClients
            )
        }
        composable(AppRoute.AddScreen.route) {
            AddScreen(
                onNavigateToScanTag = { /*TODO(Navigate to Scanning Screen)*/ },
                onNavigateUp = {
                    navController.navigateUp()
                },
                viewModel = viewModel,
                tag = tag,
                setupNfc = setupNfc
            )


        }
        animatedComposable(AppRoute.AddRepairDetailsScreen.route) { backStackEntry ->
            val vehicleId = backStackEntry.arguments?.getString("vehicleId") ?: ""
        }
        composable(AppRoute.ManageScreen.route) {
            ManageScreen( onNavigateUp = {
                navController.navigateUp()
            })
        }
        animatedComposable(AppRoute.ClientDetailsScreen.route) {
			val viewModel: NFCReaderViewModel = koinViewModel()
			ClientDetailsScreen(
                uiState = viewModel.clientUiState,
                navigate = navActions::navigate
            )
        }
        animatedComposable(AppRoute.VehicleDetailsScreen.route) { backStackEntry ->
            val clientId = backStackEntry.arguments?.getString("clientId") ?: ""
        }
        animatedComposable(AppRoute.RepairHistoryScreen.route) { backStackEntry ->
            val vehicleId = backStackEntry.arguments?.getString("vehicleId") ?: ""
        }
    }
}
