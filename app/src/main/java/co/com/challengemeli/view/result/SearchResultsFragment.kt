package co.com.challengemeli.view.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import co.com.challengemeli.base.SearchBaseFragment
import co.com.challengemeli.databinding.FragmentSearchResultsBinding
import com.google.gson.JsonArray

class SearchResultsFragment : SearchBaseFragment() {

    private lateinit var binding: FragmentSearchResultsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSearchResultsBinding.inflate(inflater, container, false)
        searchActivity.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        searchActivity.supportActionBar?.hide()
        searchActivity.searchViewModel.searchList.observe(viewLifecycleOwner) {
            initSearchResultsRecyclerView(it.peekContent().get("results").asJsonArray)
        }
        return binding.root
    }

    private fun initSearchResultsRecyclerView(results: JsonArray) {
        binding.recyclerSearchResults.layoutManager = LinearLayoutManager(context)
        binding.recyclerSearchResults.adapter = SearchResultsAdapter(results) {
            findNavController().navigate(
                SearchResultsFragmentDirections.actionSearchResultsFragmentToDetailResultFragment(
                    it.get("id").asString
                )
            )
        }
    }
}