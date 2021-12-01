package com.kizadev.myapplication.di.modules

import android.content.Context
import com.kizadev.myapplication.data.network.api.ApiConfig
import com.kizadev.myapplication.data.network.api.ItunesService
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


@Module
class NetworkModule {


    @Provides
    fun provideItunesService(context: Context): ItunesService {

        val cache = Cache(
            directory = context.cacheDir,
            maxSize = 10L * 1024 * 1024
        )

        val client = OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .callTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor())
            .cache(cache)
            .build()

        val retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl(ApiConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()

        return retrofit.create(ItunesService::class.java)

    }

}