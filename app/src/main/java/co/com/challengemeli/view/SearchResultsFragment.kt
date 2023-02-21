package co.com.challengemeli.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import co.com.challengemeli.R
import co.com.challengemeli.SearchApplication
import co.com.challengemeli.databinding.FragmentSearchResultsBinding
import co.com.challengemeli.viewmodel.SearchViewModel
import co.com.challengemeli.viewmodel.SearchViewModelFactory
import com.google.gson.JsonArray

class SearchResultsFragment : Fragment() {

    private lateinit var binding: FragmentSearchResultsBinding
    private val args: SearchResultsFragmentArgs by navArgs()
    private val searchViewModel: SearchViewModel by activityViewModels {
        SearchViewModelFactory(
            (activity?.application as SearchApplication).searchRepository,
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSearchResultsBinding.inflate(inflater, container, false)
        searchViewModel.searchList.observe(viewLifecycleOwner) {
            initSearchResultsRecyclerView(it.peekContent().get("results").asJsonArray)
        }
        return binding.root
    }

    private fun initSearchResultsRecyclerView(results: JsonArray) {
        binding.recyclerSearchResults.layoutManager = LinearLayoutManager(context)
        binding.recyclerSearchResults.adapter = SearchResultsAdapter(results) {
            findNavController().navigate(R.id.detailResultFragment)
        }
    }
}