package work.racka.configchangetest

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import work.racka.configchangetest.ui.navigation.AppNavHost
import work.racka.configchangetest.ui.theme.AppComposeTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppComposeTheme {
                AppNavHost()
            }
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        println("Config changed: $newConfig")
    }

}
