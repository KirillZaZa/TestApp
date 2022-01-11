package com.kizadev.myapplication.di.modules

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module(includes = [NetworkModule::class, DisposableModule::class, BindModule::class])
abstract class MainModule {

    @Singleton
    @Binds
    abstract fun context(application: Application): Context
}
