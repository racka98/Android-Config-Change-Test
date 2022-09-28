package work.racka.configchangetest.ui.other

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class OtherViewModel(
    private val scope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
) {
    private val counter = MutableStateFlow(0)
    val counterState: StateFlow<Int> = counter

    init {
        scope.launch {
            countingFlow().collectLatest { num ->
                counter.update { num }
            }
        }
    }

    private suspend fun countingFlow(): Flow<Int> = flow {
        var number = 0
        while (true) {
            delay(1000)
            emit(++number)
            println("Current Number: $number")
        }
    }.flowOn(Dispatchers.IO)

    fun onClear() {
        scope.cancel()
        println("Is Scope Active: ${scope.isActive}")
    }
}