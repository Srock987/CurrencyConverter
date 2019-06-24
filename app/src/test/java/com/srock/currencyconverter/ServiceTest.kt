package com.srock.currencyconverter

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.srock.currencyconverter.data.Values
import com.srock.currencyconverter.networking.CurrencyService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import org.junit.ClassRule
import java.util.concurrent.TimeUnit


class ServiceTest {

    companion object {
        @ClassRule
        @JvmField
        val schedulers = RxImmediateSchedulerRule()
    }

    lateinit var currencyService: CurrencyService

    @Before
    fun inject_service(){
        val gson =GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
            .create()

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BASIC

//        val cacheDir = File(application.cacheDir, UUID.randomUUID().toString())
//        // 10 MiB cache
//        val cache = Cache(cacheDir, 10 * 1024 * 1024)

        val client = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .build()

        currencyService = Retrofit.Builder()
            .baseUrl(Values.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
            .create(CurrencyService::class.java)
    }

    @Test
    fun verify_api_call(){
        val currencyName = "GBP"

        currencyService.getCurrency(currencyName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { assert(it.base == currencyName) },
                { assert(false) }
            )
    }

}