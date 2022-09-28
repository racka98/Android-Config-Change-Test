package work.racka.configchangetest.network

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import work.racka.configchangetest.models.CatFact

class MyApi(
    private val client: HttpClient,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun getCatFact(): Result<CatFact> = withContext(dispatcher) {
        val response = client.get(CAT_FACT_URL)
        when (response.status) {
            HttpStatusCode.OK -> {
                val body = response.body<CatFact>()
                Result.success(body)
            }
            else -> Result.failure(Exception("An Error Occurred!"))
        }
    }

    companion object {
        const val CAT_FACT_URL = "https://catfact.ninja/fact"
    }
}