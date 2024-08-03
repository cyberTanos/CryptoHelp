package com.tanya.finhelp.di

import com.tanya.finhelp.domain.AuthRepository
import com.tanya.finhelp.domain.AuthRepositoryImpl
import com.tanya.finhelp.domain.CoinRepository
import com.tanya.finhelp.domain.CoinRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoriesModule {

    @Singleton
    @Binds
    fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Singleton
    @Binds
    fun bindCoinRepository(impl: CoinRepositoryImpl): CoinRepository
}
