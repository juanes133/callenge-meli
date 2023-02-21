package co.com.challengemeli.repository

import co.com.challengemeli.repository.base.BaseRepository
import com.google.gson.JsonObject

class SearchRepository : BaseRepository() {

    suspend fun getSearch(
        onSuccess: (JsonObject) -> Unit,
        onFailure: (Exception) -> Unit,
    ) {
        try {
            onSuccess(service.listSearch())
        } catch (e: Exception) {
            onFailure(e)
        }
    }

    suspend fun findById(
        onSuccess: (JsonObject) -> Unit,
        onFailure: (Exception) -> Unit,
    ) {
        try {
            onSuccess(service.listSearch())
        } catch (e: Exception) {
            onFailure(e)
        }
    }
}
