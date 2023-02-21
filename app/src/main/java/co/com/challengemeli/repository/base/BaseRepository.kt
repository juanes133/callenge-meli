package co.com.challengemeli.repository.base

import co.com.challengemeli.service.SearchService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

open class BaseRepository {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.mercadolibre.com/sites/MCO/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service: SearchService = retrofit.create(SearchService::class.java)

}
