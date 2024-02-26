package net.linakis.colorandom.api

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit

object RetrofitClient {
    private const val BASE_URL = "https://www.thecolorapi.com/"

    private val json = Json { ignoreUnknownKeys = true }

    val apiService: ColorApiService by lazy {
        val contentType = MediaType.parse("application/json")!!

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()

        retrofit.create(ColorApiService::class.java)
    }
}