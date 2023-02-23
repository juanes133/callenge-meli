package co.com.challengemeli.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import co.com.challengemeli.MainActivity

open class SearchBaseFragment : Fragment() {

    internal lateinit var searchActivity: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchActivity = (activity as MainActivity)
    }
}