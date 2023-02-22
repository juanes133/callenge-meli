package co.com.challengemeli.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import co.com.challengemeli.R
import co.com.challengemeli.SearchApplication
import co.com.challengemeli.databinding.FragmentSearchBinding
import co.com.challengemeli.viewmodel.SearchViewModel
import co.com.challengemeli.viewmodel.SearchViewModelFactory
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.util.*
import kotlin.collections.ArrayList

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private var userListToFilter = JsonArray()
    private val searchViewModel: SearchViewModel by activityViewModels {
        SearchViewModelFactory(
            (activity?.application as SearchApplication).searchRepository,
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity).supportActionBar?.title = ""
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (activity as AppCompatActivity).supportActionBar?.hide()
        searchViewModel.searchList.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { result ->
                if (result.get("results").asJsonArray.size() == 0) {
                    Snackbar.make(
                        binding.root,
                        "No hay publicaciones que coincidan con tu búsqueda",
                        BaseTransientBottomBar.LENGTH_SHORT
                    ).show()
                } else {
                    findNavController().navigate(R.id.action_searchFragment_to_searchResultsFragment)
                }
            }
        }
        searchViewModel.searchError.observe(viewLifecycleOwner) {
            Snackbar.make(
                binding.root,
                "Al parecer algo salio mal",
                BaseTransientBottomBar.LENGTH_SHORT
            ).show()
        }
        binding.btnSearch.setOnClickListener {
            searchViewModel.getSearch()
        }
        binding.loading.isVisible = false
        binding.btnSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int,
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int,
            ) {
                if (s.isNotEmpty()) {
                    val userFilter = userListToFilter.filter { result ->
                        result.asJsonObject.get("results").asString.startsWith(s.toString().lowercase(Locale.ROOT))
                    } as JsonArray
                    if (userFilter.asJsonArray.size() > 0) {
                            userFilter.asJsonArray
                    } else {
                        showUserList(JsonArray())
                        binding.textListEmpty.isVisible = true
                    }
                } else {
                    showUserList(userListToFilter)
                }
            }
        })
        return binding.root
    }

    private fun showUserList(userList: JsonArray) {
        userList.asJsonArray
        binding.loading.isVisible = false
        binding.containerSearch.isVisible = true
        binding.textListEmpty.isVisible = false
    }
}