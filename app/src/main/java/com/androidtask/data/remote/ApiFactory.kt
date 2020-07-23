package com.androidtask.data.remote

import com.androidtask.util.AppConstants
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object ApiFactory {

    private var retrofitWithHeader: Retrofit? = null

    //todo have to change this method
    val clientWithoutHeader: Retrofit
        get() {

            val httpClientBuilder = OkHttpClient.Builder()

            httpClientBuilder.addInterceptor { chain ->

                val original = chain.request()
                val requestBuilder = original.newBuilder()
                requestBuilder.addHeader("Accept", "application/json")
                val request = requestBuilder.build()

                chain.proceed(request)
            }

            httpClientBuilder.connectTimeout(5, TimeUnit.MINUTES)
            httpClientBuilder.readTimeout(5, TimeUnit.MINUTES)
            httpClientBuilder.writeTimeout(5, TimeUnit.MINUTES)

            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            httpClientBuilder.addInterceptor(loggingInterceptor)

            if (retrofitWithHeader == null) {
                val gson = GsonBuilder().setLenient().create()
                retrofitWithHeader = Retrofit.Builder()
                    .baseUrl(AppConstants.BASE_URL)
                    .client(httpClientBuilder.build())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
            }
            return retrofitWithHeader!!
        }


}

