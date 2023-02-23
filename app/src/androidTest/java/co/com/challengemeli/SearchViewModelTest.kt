package co.com.challengemeli

import androidx.lifecycle.Observer
import androidx.test.annotation.UiThreadTest
import co.com.challengemeli.repository.SearchRepository
import co.com.challengemeli.viewmodel.Event
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
    private var observerSearch: Observer<Event<JsonObject>>? = null

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
        searchViewModel.getSearch("Motorola G6")

        //Then
        observerSearch = Observer<Event<JsonObject>> { searchResult ->
            Assert.assertNotNull(searchResult)
            Assert.assertEquals("MCO", searchResult.peekContent().get("site_id").asString)
            Assert.assertEquals("GMT-05:00", searchResult.peekContent().get("country_default_time_zone").asString)
            Assert.assertEquals("Motorola G6", searchResult.peekContent().get("query").asString)
            Assert.assertEquals(true, searchResult.peekContent().get("results").asJsonArray.size() > 0)
            Assert.assertEquals(true, searchResult.peekContent().get("available_sorts").asJsonArray.size() > 0)
            Assert.assertEquals(true, searchResult.peekContent().get("filters").asJsonArray.size() > 0)
            Assert.assertEquals(true, searchResult.peekContent().get("available_filters").asJsonArray.size() > 0)
            finished = true
        }
        searchViewModel.searchList.observeForever(observerSearch!!)
    }
}
