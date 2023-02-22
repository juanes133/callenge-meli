package co.com.challengemeli.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import co.com.challengemeli.SearchApplication
import co.com.challengemeli.databinding.FragmentDetailResultBinding
import co.com.challengemeli.viewmodel.SearchViewModel
import co.com.challengemeli.viewmodel.SearchViewModelFactory
import com.bumptech.glide.Glide


class DetailResultFragment : Fragment() {

    private lateinit var binding: FragmentDetailResultBinding
    private val args: DetailResultFragmentArgs by navArgs()
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
        binding = FragmentDetailResultBinding.inflate(inflater, container, false)
        searchViewModel.searchList.observe(viewLifecycleOwner) { it ->
            val result = it.peekContent().get("results").asJsonArray.filter { result ->
                result.asJsonObject.get("id").asString == args.idResult
            }.firstOrNull()?.asJsonObject
            binding.condition.text = result?.get("condition")?.asString
            binding.title.text = result?.get("title")?.asString
            Glide.with(binding.thumbnail.context)
                .load(result?.get("thumbnail")?.asString?.replaceFirst("http", "https"))
                .into(binding.thumbnail)
            binding.price.text = result?.get("price")?.asString
            binding.availableQuantity.text = result?.get("available_quantity")?.asString
            result?.get("address")?.asJsonObject?.get("state_name")?.let {
                binding.state.text = it.asString
            }
            result?.get("address")?.asJsonObject?.get("city_name")?.let {
                binding.city.text = it.asString
            }
        }
        return binding.root
    }
}
