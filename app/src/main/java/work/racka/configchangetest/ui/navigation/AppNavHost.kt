package work.racka.configchangetest.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.Dispatchers
import work.racka.configchangetest.ConfigChangeTestApplication
import work.racka.configchangetest.ui.home.HomeScreen
import work.racka.configchangetest.ui.home.HomeViewModel
import work.racka.configchangetest.ui.other.OtherScreen
import work.racka.configchangetest.ui.other.OtherViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavHost(navController: NavHostController = rememberNavController()) {

    Scaffold { innerPadding ->
        NavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            startDestination = Routes.HOME
        ) {
            composable(route = Routes.HOME) {
                // You can use DI or whatever to create this class
                val app = LocalContext.current.applicationContext as ConfigChangeTestApplication
                val viewModel = remember {
                    HomeViewModel(api = app.myApi)
                }
                val uiState by viewModel.uiState.collectAsState(Dispatchers.Main.immediate)

                HomeScreen(
                    uiState = uiState,
                    updateInputText = viewModel::updateInputText,
                    randomize = viewModel::getRandomCharacter,
                    getCatFact = viewModel::getCatFact,
                    navigateToOther = {
                        navController.navigate(Routes.OTHER)
                    }
                )

                /*// Clear VM scope
                DisposableEffect(Unit) {
                    onDispose { viewModel.onClear() }
                }*/

            }

            composable(route = Routes.OTHER) {
                val viewModel = remember { OtherViewModel() }
                val counterValue by viewModel.counterState.collectAsState(Dispatchers.Main.immediate)

                OtherScreen(counterValue = counterValue) { navController.popBackStack() }

                // Clear VM scope
                DisposableEffect(Unit) {
                    onDispose { viewModel.onClear() }
                }
            }
        }
    }
}