package work.racka.configchangetest

import android.app.Application
import android.content.res.Configuration
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import work.racka.configchangetest.network.MyApi

class ConfigChangeTestApplication : Application() {

    private lateinit var httpClient: HttpClient
    lateinit var myApi: MyApi

    override fun onCreate() {
        super.onCreate()
        httpClient = createHttpClient()
        myApi = MyApi(client = httpClient)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        // Checking if the wallpaper changes trigger this
        println("Application - Config Change: $newConfig")
    }

    private fun createHttpClient() = HttpClient(OkHttp) {
        followRedirects = true
        if (BuildConfig.DEBUG) {
            install(ContentNegotiation) { json() }
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.INFO
            }
        }
    }
}