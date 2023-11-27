package com.blueland.jetgames.ui.screen.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.blueland.jetgames.R
import com.blueland.jetgames.di.Injection
import com.blueland.jetgames.ui.screen.UiState
import com.blueland.jetgames.ui.screen.ViewModelFactory


@Composable
fun DetailScreen(
    gameId: String,
    viewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    navigateBack: () -> Unit
) {
    val isFavorite = remember { mutableStateOf(false) }
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getGameById(gameId)
            }
            is UiState.Success -> {
                val data = uiState.data
                viewModel.checkFavorite(gameId) { isGameFavorite ->
                    isFavorite.value = isGameFavorite
                }
                DetailContent(
                    data.game.name,
                    data.game.photoUrl,
                    data.game.genre,
                    data.game.release,
                    data.game.desc,
                    onBackClick = navigateBack,
                    isFavorite = isFavorite.value,
                    onToggleFavorite = {
                        if (isFavorite.value) {
                            viewModel.removeFromFavorite(gameId)
                            isFavorite.value = false
                        } else {
                            viewModel.addToFavorites(gameId)
                            isFavorite.value = true
                        }
                    },
                )
            }
            is UiState.Error -> {}
        }
    }
}

@Composable
fun DetailContent(
    name: String,
    photoUrl: String,
    genre: String,
    release: String,
    desc: String,
    onBackClick: () -> Unit,
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.verticalScroll(rememberScrollState())) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back button",
                modifier = Modifier
                    .clickable { onBackClick() }
                    .padding(end = 16.dp)
            )
            Text(
                text = name,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            IconButton(
                onClick = { onToggleFavorite() },
                modifier = modifier.padding(top = 4.dp)
            ) {
                val icon =
                    if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder
                Icon(
                    imageVector = icon,
                    tint = if (isFavorite) MaterialTheme.colorScheme.primary else Color.White,
                    contentDescription = if (isFavorite) "Hapus Dari Favorite" else "Tambah ke Favorite",
                )
            }
        }

        AsyncImage(
            model = photoUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(210.dp)
                .padding(16.dp)
        )

        DetailInfoItem("Genre", genre)
        DetailInfoItem("Release Date", release)

        Text(
            text = "About this game",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier
                .padding(16.dp, 16.dp, 16.dp, 8.dp)
        )
        Divider(
            color = Color.Black,
            thickness = 2.dp,
            modifier = Modifier.padding(16.dp, 0.dp, 16.dp, 16.dp)
        )
        Text(
            text = desc,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            modifier = Modifier
                .padding(16.dp, 0.dp, 16.dp, 16.dp)
        )
    }
}


@Composable
fun DetailInfoItem(label: String, value: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, bottom = 8.dp)
    ) {
        Text(
            text = label,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Text(
            text = value,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp
        )
    }
}

