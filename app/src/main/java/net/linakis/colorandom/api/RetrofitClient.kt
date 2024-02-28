package net.linakis.colorandom.api

import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import net.linakis.colorandom.FlipperHelper
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

object RetrofitClient {
    private const val BASE_URL = "https://www.thecolorapi.com/"

    private val json = Json { ignoreUnknownKeys = true }

    val apiService: ColorApiService by lazy {
        val contentType = "application/json".toMediaType()

        val okHttpClient = OkHttpClient.Builder()
            .addNetworkInterceptor(FlipperOkhttpInterceptor(FlipperHelper.getNetworkPlugin()))
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()

        retrofit.create(ColorApiService::class.java)
    }
}