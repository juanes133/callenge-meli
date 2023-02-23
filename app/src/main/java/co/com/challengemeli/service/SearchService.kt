package co.com.challengemeli.service

import com.google.gson.JsonObject
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {

    @GET("search")
    suspend fun listSearch(
        @Query("q") query: String,
    ): JsonObject
}