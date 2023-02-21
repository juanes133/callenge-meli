package co.com.challengemeli

import androidx.lifecycle.Observer
import androidx.test.annotation.UiThreadTest
import co.com.challengemeli.repository.SearchRepository
import co.com.challengemeli.viewmodel.SearchViewModel
import co.com.challengemeli.viewmodel.SearchViewModelFactory
import com.google.gson.JsonObject
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class SearchViewModelTest {

    var finished = false
    var time = 2

    //Given
    private var searchViewModel: SearchViewModel =
        SearchViewModelFactory(SearchRepository()).create(
            SearchViewModel::class.java
        )
    private var observerSearch: Observer<JsonObject>? = null

    @Before
    fun setUp() {
        finished = false
        time = 2
    }

    @After
    fun tearDown() {
        Thread.sleep(time * 1000L)
        Assert.assertTrue(finished)
    }

    @Test
    @UiThreadTest
    fun getSearchTest() {
        //When
        searchViewModel.getSearch()

        //Then
        observerSearch = Observer<JsonObject> { searchResult ->
            Assert.assertNotNull(searchResult)
            Assert.assertEquals("MCO", searchResult.get("site_id").asString)
            Assert.assertEquals("GMT-05:00", searchResult.get("country_default_time_zone").asString)
            Assert.assertEquals("Motorola G6", searchResult.get("query").asString)
            Assert.assertEquals(true, searchResult.getAsJsonArray("results").size() > 0)
            Assert.assertEquals(true, searchResult.getAsJsonArray("available_sorts").size() > 0)
            Assert.assertEquals(true, searchResult.getAsJsonArray("filters").size() > 0)
            Assert.assertEquals(true, searchResult.getAsJsonArray("available_filters").size() > 0)
            finished = true
        }
        searchViewModel.searchList.observeForever(observerSearch!!)
    }
}
