package com.kizadev.myapplication.di.component

import android.app.Application
import com.kizadev.myapplication.di.modules.MainModule
import com.kizadev.myapplication.presentation.activity.MainActivity
import com.kizadev.myapplication.presentation.fragments.AlbumDetailsFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [MainModule::class])
interface ForaComponent {

    fun inject(activity: MainActivity)

    fun inject(fragment: AlbumDetailsFragment)


    @Component.Builder
    interface Builder{

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ForaComponent

    }

}