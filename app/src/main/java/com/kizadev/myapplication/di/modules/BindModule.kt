package com.kizadev.myapplication.di.modules

import com.kizadev.myapplication.data.repository.ForaRepositoryImpl
import com.kizadev.myapplication.domain.repository.ForaRepository
import dagger.Binds
import dagger.Module

@Module
interface BindModule {

    @Binds
    fun bindForaRepository(foraRepositoryImpl: ForaRepositoryImpl): ForaRepository
}
