package com.example.namozvaqti.ui.prayerTime
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.namozvaqti.databinding.ItemCityBinding
import com.example.namozvaqti.utils.AutoUpdatableAdapter
import kotlin.properties.Delegates

class CityAdapter(private val itemClickListener: (Int) -> Unit) : RecyclerView.Adapter<CityAdapter.VH>(),
    AutoUpdatableAdapter {

    var items: List<String> by Delegates.observable(emptyList()) { _, old, new ->
        autoNotify(old, new) { o, n -> o == n }
    }

    class VH(private val binding: ItemCityBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(city: String) {
            binding.tvCity.text = city
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH =
        VH(ItemCityBinding.inflate(LayoutInflater.from(parent.context),
            parent, false))

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(items[position])
        holder.itemView.setOnClickListener {
            itemClickListener.invoke(holder.adapterPosition)
        }
    }

    override fun getItemCount(): Int = items.size
}