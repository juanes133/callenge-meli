package co.com.challengemeli.view.result

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import co.com.challengemeli.databinding.ItemSearchResultsBinding
import com.bumptech.glide.Glide
import com.google.gson.JsonObject
import java.text.NumberFormat
import java.util.*

class SearchResultsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemSearchResultsBinding.bind(view)

    fun render(
        result: JsonObject,
        onClickListener: (JsonObject) -> Unit,
    ) {
        Glide.with(binding.thumbnail.context)
            .load(result.get("thumbnail").asString.replaceFirst("http", "https"))
            .into(binding.thumbnail)
        binding.title.text = result.get("title").asString
        val locale = Locale("es", "CO")
        val numberFormat: NumberFormat = NumberFormat.getCurrencyInstance(locale)
        binding.price.text = numberFormat.format(result.get("price").asNumber).replace(",00", "")
        result.get("address").asJsonObject.get("state_name")?.let {
            binding.state.text = it.asString
        }
        result.get("address").asJsonObject.get("city_name")?.let {
            binding.city.text = it.asString
        }
        itemView.setOnClickListener {
            onClickListener(result)
        }
    }
}