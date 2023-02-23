package co.com.challengemeli.view.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import co.com.challengemeli.R
import co.com.challengemeli.base.SearchBaseFragment
import co.com.challengemeli.databinding.FragmentSearchBinding
import co.com.challengemeli.log.Logger
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

class SearchFragment : SearchBaseFragment() {

    private lateinit var binding: FragmentSearchBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        searchActivity.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        searchActivity.supportActionBar?.hide()
        searchActivity.searchViewModel.searchList.observe(viewLifecycleOwner) {
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
                binding.loading.isVisible = false
                binding.btnSearch.isEnabled = true
            }
        }
        searchActivity.searchViewModel.searchError.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { e ->
                Snackbar.make(
                    binding.root,
                    "Al parecer algo salió mal",
                    BaseTransientBottomBar.LENGTH_SHORT
                ).show()
                Logger().error(e)
                binding.loading.isVisible = false
                binding.btnSearch.isEnabled = true
            }
        }
        binding.btnSearch.setOnClickListener {
            if (searchActivity.checkNetworkConnection?.isConnected(searchActivity) == true) {
                if (binding.searchProducts.text.toString().length >= 4) {
                    binding.loading.isVisible = true
                    binding.btnSearch.isEnabled = false
                    searchActivity.searchViewModel.getSearch(binding.searchProducts.text.toString())
                } else {
                    Snackbar.make(
                        binding.root,
                        "Señor usuario debe poner al menos cuatro letras para buscar su producto",
                        BaseTransientBottomBar.LENGTH_SHORT
                    ).show()
                }
            } else {
                Snackbar.make(
                    binding.root,
                    "No estas conectado a internet!",
                    BaseTransientBottomBar.LENGTH_SHORT
                ).show()
            }
        }
        binding.loading.isVisible = false
        return binding.root
    }
}