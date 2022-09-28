package work.racka.configchangetest.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notes
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    uiState: HomeState,
    updateInputText: (String) -> Unit,
    randomize: () -> Unit,
    getCatFact: () -> Unit,
    navigateToOther: () -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        stickyHeader {
            Text(
                text = "Home",
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
            )
        }

        item {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.inputText,
                onValueChange = updateInputText,
                label = { Text(text = "Enter Text Here") },
                leadingIcon = { Icon(imageVector = Icons.Default.Notes, contentDescription = null) }
            )
        }

        item {
            Text(
                text = "Random Character: ${uiState.randomOutputText}",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            Button(onClick = randomize) {
                Text(text = "Get Random Character")
            }
        }

        stickyHeader {
            Text(
                text = "Cat Fact",
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
            )
        }

        item {
            when (uiState.catFactState) {
                is CatFactState.LoadingFact -> LinearProgressIndicator()
                is CatFactState.Nothing -> {
                    Text(
                        text = "Click the Button below to get a fact!",
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                is CatFactState.Fact -> {
                    Text(
                        text = "Fact: ${uiState.catFactState.fact}",
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                is CatFactState.Error -> {
                    Text(
                        text = uiState.catFactState.msg,
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }

        item {
            Button(onClick = getCatFact) {
                Text(text = "Get Cat Fact!")
            }
        }

        item {
            Button(onClick = navigateToOther) {
                Text(text = "Go To Other Screen")
            }
        }
    }
}