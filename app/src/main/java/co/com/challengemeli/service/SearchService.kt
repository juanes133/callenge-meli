package co.com.challengemeli.service

import com.google.gson.JsonObject
import retrofit2.http.GET

interface SearchService {

    @GET("search?q=Motorola%20G6")
    suspend fun listSearch(
    ): JsonObject
}