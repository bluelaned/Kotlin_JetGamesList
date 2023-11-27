package com.blueland.jetgames.ui.screen.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blueland.jetgames.model.ListGame
import com.blueland.jetgames.repository.GamesRepository
import com.blueland.jetgames.ui.screen.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: GamesRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<ListGame>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<ListGame>>> = _uiState

    fun getAllGames() {
        viewModelScope.launch {
            repository.getAllGames()
                .catch { exception ->
                    _uiState.value = UiState.Error(exception.message.toString())
                }
                .collect { listGame ->
                    _uiState.value = UiState.Success(listGame)
                }
        }
    }

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    fun search(newQuery: String) {
        _query.value = newQuery
        viewModelScope.launch {
            repository.searchGames(_query.value)
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { listGame ->
                    _uiState.value = UiState.Success(listGame)
                }

        }
    }
}
