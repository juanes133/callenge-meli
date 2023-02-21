package co.com.challengemeli.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import co.com.challengemeli.MainActivity
import co.com.challengemeli.SearchApplication
import co.com.challengemeli.databinding.FragmentDetailResultBinding
import co.com.challengemeli.viewmodel.SearchViewModel
import co.com.challengemeli.viewmodel.SearchViewModelFactory
import com.google.gson.JsonArray
import com.google.gson.JsonObject

class DetailResultFragment : Fragment() {

    private lateinit var binding: FragmentDetailResultBinding
    //private var args: DetailResultFragmentArgs by navArgs()
    private var result: JsonArray? = null
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
       /* searchViewModel.findById(args.detailResult)
        searchViewModel.searchList.observe(viewLifecycleOwner) { it ->
            it.getContentIfNotHandled()?.let { results ->
                result = results.asJsonObject
                result?.let {
                }
            }
        }*/
        return binding.root
    }
}