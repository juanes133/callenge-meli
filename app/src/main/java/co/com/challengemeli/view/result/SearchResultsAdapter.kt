package co.com.challengemeli.view.result

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.com.challengemeli.R
import com.google.gson.JsonArray
import com.google.gson.JsonObject

class SearchResultsAdapter(
    private val searchResultsList: JsonArray,
    private val onClickListener: (JsonObject) -> Unit,
) : RecyclerView.Adapter<SearchResultsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return SearchResultsViewHolder(
            layoutInflater.inflate(R.layout.item_search_results, parent, false)
        )
    }

    override fun getItemCount(): Int = searchResultsList.size()

    override fun onBindViewHolder(holder: SearchResultsViewHolder, position: Int) {
        holder.render(searchResultsList.get(position).asJsonObject, onClickListener)
    }
}