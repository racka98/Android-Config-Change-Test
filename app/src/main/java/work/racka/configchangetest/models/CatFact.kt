package work.racka.configchangetest.models

import kotlinx.serialization.Serializable

@Serializable
data class CatFact(
    val fact: String,
    val length: Int
)