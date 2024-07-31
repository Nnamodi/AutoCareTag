package dev.borisochieng.autocaretag.ui.navigation

import android.nfc.Tag
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.borisochieng.autocaretag.nfc_reader.ui.ClientDetailsScreen
import dev.borisochieng.autocaretag.nfc_reader.ui.NFCReaderViewModel
import dev.borisochieng.autocaretag.nfc_writer.presentation.viewModel.AddInfoViewModel
import dev.borisochieng.autocaretag.ui.manage.ClientScreenViewModel
import dev.borisochieng.autocaretag.ui.manage.ClientScreen
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
    nfcReaderViewModel: NFCReaderViewModel = koinViewModel(),
    clientScreenViewModel: ClientScreenViewModel = koinViewModel(),
    tag: Tag? = null,
    setupNfc: () -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = AppRoute.HomeScreen.route,
        modifier = Modifier
            .padding(paddingValues)
    ) {
        animatedComposable(AppRoute.HomeScreen.route) {
            HomeScreen(
                onNavigateToAddClient = {
                    //scanNfc(true)
                    navActions.navigate(Screens.AddScreen)
                },
                onNavigateToManage = {
                    navActions.navigate(Screens.ManageScreen)
                },
                clients = fakeClients,
                viewModel = nfcReaderViewModel,
                scanNFC = {scanNfc(true)}
            )
        }
        animatedComposable(AppRoute.AddScreen.route) {
            AddScreen(
                onNavigateUp = {
                    navController.navigateUp()
                },
                viewModel = viewModel,
                tag = tag,
                setupNfc = setupNfc,
                onNavigateToClientDetails = { client ->
                    navActions.navigate(Screens.ClientDetailsScreen(client.clientId))
                }
            )


        }
        animatedComposable(AppRoute.AddRepairDetailsScreen.route) { backStackEntry ->
            val vehicleId = backStackEntry.arguments?.getString("vehicleId") ?: ""
        }
        animatedComposable(AppRoute.ManageScreen.route) { backStackEntry ->
            val clientId = backStackEntry.arguments?.getString("clientId") ?: ""
            ClientScreen(
                onNavigateUp = {
                    navController.navigateUp()
                },
                onNavigateToClient = { client ->
                    navActions.navigate(Screens.ClientDetailsScreen(client.clientId))
                },
                viewModel = clientScreenViewModel
            )
        }
        animatedComposable(AppRoute.ClientDetailsScreen.route) {
            ClientDetailsScreen(
                uiState = nfcReaderViewModel.clientUiState,
                updateClientInfo = nfcReaderViewModel::updateClientDetails,
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
