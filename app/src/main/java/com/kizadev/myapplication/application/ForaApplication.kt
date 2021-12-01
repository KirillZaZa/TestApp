package com.kizadev.myapplication.application

import android.app.Application
import android.content.Context
import com.kizadev.myapplication.di.component.DaggerForaComponent
import com.kizadev.myapplication.di.component.ForaComponent

class ForaApplication : Application() {

    lateinit var foraComponent: ForaComponent


    override fun onCreate() {
        super.onCreate()

        foraComponent = DaggerForaComponent
            .builder()
            .application(this)
            .build()

    }


}

val Context.foraComponent: ForaComponent
    get() = when(this){
        is ForaApplication -> foraComponent
        else -> this.applicationContext.foraComponent
    }

