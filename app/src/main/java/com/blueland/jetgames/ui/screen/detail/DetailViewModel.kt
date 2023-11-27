package com.blueland.jetgames.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blueland.jetgames.model.ListGame
import com.blueland.jetgames.repository.GamesRepository
import com.blueland.jetgames.ui.screen.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: GamesRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<ListGame>>(UiState.Loading)
    val uiState: StateFlow<UiState<ListGame>> = _uiState

    fun getGameById(gameId: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getListGameById(gameId))
        }
    }

    fun addToFavorites(gameId: String) {
        viewModelScope.launch {
            repository.addToFavorites(gameId)
        }
    }

    fun removeFromFavorite(gameId: String) {
        viewModelScope.launch {
            repository.removeFromFavorites(gameId)
        }
    }

    fun checkFavorite(gameId: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val isFavorite = repository.isFavorite(gameId)
            onResult(isFavorite)
        }
    }
}