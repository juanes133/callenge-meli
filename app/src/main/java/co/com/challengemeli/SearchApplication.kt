package co.com.challengemeli

import android.app.Application
import co.com.challengemeli.repository.SearchRepository

class SearchApplication : Application() {

    val searchRepository by lazy { SearchRepository() }

}