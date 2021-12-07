package com.kizadev.myapplication.di.component

import android.app.Application
import com.kizadev.myapplication.di.modules.MainModule
import com.kizadev.myapplication.presentation.fragments.AlbumDetailsFragment
import com.kizadev.myapplication.presentation.fragments.MainFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [MainModule::class])
interface ForaComponent {

    fun inject(mainFragment: MainFragment)

    fun inject(detailFragment: AlbumDetailsFragment)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ForaComponent
    }
}
