package com.blueland.jetgames.di

import com.blueland.jetgames.repository.GamesRepository

object Injection {
    fun provideRepository(): GamesRepository {
        return GamesRepository.getInstance()
    }
}