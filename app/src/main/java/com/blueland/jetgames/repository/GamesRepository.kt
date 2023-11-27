package com.blueland.jetgames.repository

import com.blueland.jetgames.model.GamesData
import com.blueland.jetgames.model.ListGame
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

class GamesRepository private constructor() {
    private val gameItem = mutableListOf<ListGame>()
    private val favoriteGames = mutableListOf<String>()
    private val listGame = GamesData.games.map { ListGame(it, 0) }

    fun getAllGames(): Flow<List<ListGame>> = flow {
        emit(listGame)
    }

    fun getListGameById(gameId: String): ListGame {
        return listGame.first { it.game.id == gameId }
    }

    fun searchGames(query: String): Flow<List<ListGame>>{
        return flowOf(listGame.filter{
            it.game.name.contains(query, ignoreCase = true)
        })
    }
    fun getGamesFavorite(): Flow<List<ListGame>> {
        return flow {
            val favoriteGamesList = gameItem.filter { it.game.id in favoriteGames }
            emit(favoriteGamesList)
        }
    }
    fun addToFavorites(gameId: String) {
        if (!favoriteGames.contains(gameId)) {
            favoriteGames.add(gameId)

            // Update the gameItem list
            gameItem.add(listGame.first { it.game.id == gameId })
        }
    }
    fun isFavorite(gameId: String): Boolean {
        return favoriteGames.contains(gameId)
    }

    fun removeFromFavorites(gameId: String) {
        favoriteGames.remove(gameId)
    }


    companion object {
        @Volatile
        private var instance: GamesRepository? = null

        fun getInstance(): GamesRepository =
            instance ?: synchronized(this) {
                instance ?: GamesRepository().also { instance = it }
            }
    }
}