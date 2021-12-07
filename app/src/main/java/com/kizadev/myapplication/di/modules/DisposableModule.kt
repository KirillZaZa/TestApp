package com.kizadev.myapplication.di.modules

import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class DisposableModule {

    @Volatile
    private var compositeDisposable: CompositeDisposable? = null

    @Provides
    fun provideCompositeDisposable(): CompositeDisposable {
        return compositeDisposable ?: synchronized(this) {
            val comp = CompositeDisposable()
            compositeDisposable = comp
            comp
        }
    }
}
