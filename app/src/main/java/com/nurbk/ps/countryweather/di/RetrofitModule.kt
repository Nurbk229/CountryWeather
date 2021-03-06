package com.nurbk.ps.countryweather.di

import com.nurbk.ps.countryweather.network.*
import com.nurbk.ps.countryweather.utils.ConstanceString
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    @Singleton
    fun InstaceRetrofit(baseUrl: String) =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .apply {
                val builder = OkHttpClient.Builder()
                builder.connectTimeout(5, TimeUnit.MINUTES) // connect timeout
                    .writeTimeout(5, TimeUnit.MINUTES) // write timeout
                    .readTimeout(5, TimeUnit.MINUTES) // read timeout
                this.client(builder.build())
            }
            .build()

    @Provides
    @Singleton
    fun countriesInterface() =
        InstaceRetrofit(ConstanceString.BASE_URL_COUNTRIES).create(CountriesInterface::class.java)

    @Provides
    @Singleton
    fun citiesInterface() =
        InstaceRetrofit(ConstanceString.BASE_URL_CITIES).create(CitiesInterface::class.java)

    @Provides
    @Singleton
    fun countriesPageInterface() =
        InstaceRetrofit(ConstanceString.BASE_URL_COUNTRIES_PAGE).create(CountriesPageInterface::class.java)

    @Provides
    @Singleton
    fun photosInterface() =
        InstaceRetrofit(ConstanceString.BASE_URL_PHOTOS).create(PhotoInterface::class.java)

    @Provides
    @Singleton
    fun weatherInterface() =
        InstaceRetrofit(ConstanceString.BASE_URL_WEATHER).create(WeatherInterface::class.java)


}