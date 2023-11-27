package com.blueland.jetgames.ui.screen

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.blueland.jetgames.di.Injection
import com.blueland.jetgames.model.GamesData
import com.blueland.jetgames.model.ListGame
import com.blueland.jetgames.ui.screen.home.HomeViewModel



@Composable
fun GameListItem(
    name: String,
    photoUrl: String,
    modifier: Modifier = Modifier
) {
    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
        ),
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                AsyncImage(
                    model = photoUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
            Text(
                text = name,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GameListItemPreview() {
    com.blueland.jetgames.ui.theme.JetGamesTheme {
        GameListItem(
            name = "Dota 2",
            photoUrl = "https://cdn.cloudflare.steamstatic.com/apps/dota2/images/dota2_social.jpg",
        )
    }
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (String) -> Unit,
) {
    val query by viewModel.query
    Column {
        SearchBar(
            query = query,
            onQueryChange = viewModel::search
        )
        viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
            when (uiState) {
                is UiState.Loading -> {
                    viewModel.getAllGames()
                }
                is UiState.Success -> {
                    HomeContent(
                        listGame = uiState.data,
                        modifier = modifier,
                        navigateToDetail = navigateToDetail,
                    )
                }
                is UiState.Error -> {}
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeContent(
    listGame: List<ListGame>,
    modifier: Modifier = Modifier,
    navigateToDetail: (String) -> Unit,
) {
    Box(modifier = modifier) {
        LazyColumn {
            items(listGame, key = { it.game.id }) { list ->
                GameListItem(
                    name = list.game.name,
                    photoUrl = list.game.photoUrl,
                    modifier = Modifier
                        .clickable {
                            navigateToDetail(list.game.id)
                        }
                        .animateItemPlacement(tween(durationMillis = 100))
                )
            }
        }
    }
}