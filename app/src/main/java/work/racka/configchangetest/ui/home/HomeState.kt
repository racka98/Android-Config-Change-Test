package work.racka.configchangetest.ui.home

import work.racka.configchangetest.models.CatFact

// Simulating modeling state for a complex screen
data class HomeState(
    val catFactState: CatFactState = CatFactState.Nothing,
    val inputText: String = "",
    val randomOutputText: String = ""
)

data class RandomizerState(
    val inputText: String
)

sealed class CatFactState {
    object Nothing : CatFactState()
    object LoadingFact: CatFactState()
    data class Fact(val fact: String): CatFactState()
    data class Error(val msg: String): CatFactState()
}
