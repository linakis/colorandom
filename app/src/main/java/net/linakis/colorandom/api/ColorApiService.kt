package net.linakis.colorandom.api

import retrofit2.http.GET
import retrofit2.http.Query

interface ColorApiService {
    @GET("id")
    suspend fun getColorName(@Query(value = "rgb", encoded = true) rgb: String): ColorApiResponse
}