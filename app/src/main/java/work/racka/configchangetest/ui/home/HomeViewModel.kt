package work.racka.configchangetest.ui.home

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import work.racka.configchangetest.network.MyApi

// We call it a View Model but it does not extend Androidx ViewModel
class HomeViewModel(
    private val api: MyApi,
    private val scope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
) {

    private val catFactState = MutableStateFlow<CatFactState>(CatFactState.Nothing)
    private val inputText = MutableStateFlow("")
    private val randomOutputText = MutableStateFlow("")

    val uiState: StateFlow<HomeState> = combine(
        catFactState,
        inputText,
        randomOutputText
    ) { catFactState, inputText, randomOutputText ->
        HomeState(
            catFactState = catFactState,
            inputText = inputText,
            randomOutputText = randomOutputText
        )
    }.stateIn(
        scope = scope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = HomeState()
    )

    fun getCatFact() {
        scope.launch {
            catFactState.update { CatFactState.LoadingFact }
            val result = api.getCatFact()
            if (result.isSuccess) {
                val catFact = result.getOrThrow()
                catFactState.update { CatFactState.Fact(fact = catFact.fact) }
            } else if (result.isFailure) {
                val message = result.exceptionOrNull()?.message ?: "Error!"
                catFactState.update { CatFactState.Error(msg = message) }
            }
        }
    }

    fun updateInputText(text: String) {
        inputText.update { text }
    }

    fun getRandomCharacter() {
        val input = inputText.value
        val random = if (input.isNotBlank()) input.random().uppercase() else ""
        randomOutputText.update { random }
    }


    fun onClear() {
        scope.cancel()
        println("Is Scope Active: ${scope.isActive}")
    }
}