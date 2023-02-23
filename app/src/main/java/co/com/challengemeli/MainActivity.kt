package co.com.challengemeli

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import co.com.challengemeli.databinding.ActivityMainBinding
import co.com.challengemeli.utilities.CheckNetworkConnection
import co.com.challengemeli.viewmodel.SearchViewModel
import co.com.challengemeli.viewmodel.SearchViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var navController: NavController? = null
    private var appBarConfiguration: AppBarConfiguration? = null
    private var checkNetworkConnection: CheckNetworkConnection? = null
    val searchViewModel: SearchViewModel by viewModels {
        SearchViewModelFactory(
            (application as SearchApplication).searchRepository,
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        callNetworkConnection()
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
        appBarConfiguration = navController?.let {
            AppBarConfiguration(it.graph)
        }
        appBarConfiguration?.let { appBarConfiguration ->
            navController?.let {
                setupActionBarWithNavController(it, appBarConfiguration)
            }
        }
        if (checkNetworkConnection?.isConnected(this) == false) {
            binding.cardInternetError.isVisible = false

        }
    }

    override fun onSupportNavigateUp(): Boolean {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        return appBarConfiguration?.let {
            navController?.apply { navigateUp(it) }.run { return true }
        } == true
    }

    private fun callNetworkConnection() {
        checkNetworkConnection = CheckNetworkConnection(application)
        checkNetworkConnection?.observe(this) { isConnected ->
            binding.cardInternetError.isVisible = !isConnected
        }
    }
}
