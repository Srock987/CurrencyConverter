package com.srock.currencyconverter.networking

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.srock.currencyconverter.data.Values.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.Reusable
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
object NetworkModule {

    @Provides
    @Reusable
    @JvmStatic
     internal fun provideGson(): Gson {
        return GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()
    }

//    @Provides
//    @Reusable
//    @JvmStatic
//    internal fun provideOkHttpClient(application: Application): OkHttpClient {
//        val interceptor = HttpLoggingInterceptor()
//        interceptor.level = HttpLoggingInterceptor.Level.BASIC
//
//        val cacheDir = File(application.cacheDir, UUID.randomUUID().toString())
//        // 10 MiB cache
//        val cache = Cache(cacheDir, 10 * 1024 * 1024)
//
//        return OkHttpClient.Builder()
//            .cache(cache)
//            .connectTimeout(30, TimeUnit.SECONDS)
//            .readTimeout(60, TimeUnit.SECONDS)
//            .writeTimeout(60, TimeUnit.SECONDS)
//            .addInterceptor(interceptor)
//            .build()
//    }

    @Provides
    @Reusable
    @JvmStatic
    fun provideCurrencyService(gson: Gson): CurrencyService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//            .client(okHttpClient)
            .build().create(CurrencyService::class.java)
    }

    @Provides
    @Reusable
    @JvmStatic
    fun provideCurrencyRepository(service: CurrencyService) : CurrencyRepository {
        return CurrencyRepository(service)
    }
}