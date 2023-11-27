package com.blueland.jetgames.ui.screen.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blueland.jetgames.model.ListGame
import com.blueland.jetgames.repository.GamesRepository
import com.blueland.jetgames.ui.screen.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class FavoriteViewModel(private val repository: GamesRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<ListGame>>> =
        MutableStateFlow(UiState.Loading)

    val favoriteGames: Flow<List<ListGame>> = repository.getGamesFavorite()
    val uiState: StateFlow<UiState<List<ListGame>>> get() = _uiState
    fun getAllGamesFavorite() {
        viewModelScope.launch {
            repository.getGamesFavorite()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { favoriteGamesList ->
                    _uiState.value = UiState.Success(favoriteGamesList)
                }
        }
    }
}