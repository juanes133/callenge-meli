package co.com.challengemeli.viewmodel

import androidx.lifecycle.*
import co.com.challengemeli.repository.SearchRepository
import com.google.gson.JsonObject
import kotlinx.coroutines.launch
import kotlin.math.E

class SearchViewModel(
    private val searchRepository: SearchRepository,
) : ViewModel() {

    private val mutableSearchList = MutableLiveData<Event<JsonObject>>()
    val searchList: LiveData<Event<JsonObject>> get() = mutableSearchList

    private val mutableSearchError = MutableLiveData<Event<Exception>>()
    val searchError: LiveData<Event<Exception>> get() = mutableSearchError

    fun getSearch() {
        viewModelScope.launch {
            searchRepository.getSearch({ list ->
                mutableSearchList.value = Event(list)
            }, {
                mutableSearchError.value = Event(it)
            })
        }
    }

    suspend fun findById() {
        viewModelScope.launch {
            searchRepository.findById({ list ->
                mutableSearchList.value = Event(list)
            }, {
                mutableSearchError.value = Event(it)
            })
        }
    }
}

class SearchViewModelFactory(
    private val searchRepository: SearchRepository,
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SearchViewModel(searchRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
