package co.com.challengemeli.repository

import co.com.challengemeli.log.Logger
import co.com.challengemeli.repository.base.BaseRepository
import com.google.gson.JsonObject

class SearchRepository : BaseRepository() {

    suspend fun getSearch(
        query: String,
        onSuccess: (JsonObject) -> Unit,
        onFailure: (Exception) -> Unit,
    ) {
        try {
            val result = service.listSearch(query)
            onSuccess(result)
            Logger().info(result)
        } catch (e: Exception) {
            onFailure(e)
            Logger().error(e)
        }
    }
}
