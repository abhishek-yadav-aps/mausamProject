package com.abhitom.mausamproject.ui.weather.future.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abhitom.mausamproject.R
import com.abhitom.mausamproject.data.database.entity.DailyItem
import com.abhitom.mausamproject.databinding.ItemFutureListBinding
import com.abhitom.mausamproject.internal.TimeConverter

class FutureWeatherListAdapter :
    RecyclerView.Adapter<FutureWeatherListAdapter.FutureWeatherListAdapterViewHolder>() {
    //Data
    var data: MutableList<DailyItem> = mutableListOf()

    //ClickListener need to be implemented in activity
    var onItemClick: ((data: String) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FutureWeatherListAdapter.FutureWeatherListAdapterViewHolder {
        return FutureWeatherListAdapterViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_future_list, parent, false)
        )
    }

    //Data Change or Add
    fun swapData(newData: MutableList<DailyItem>) {
        data = newData
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(
        holder: FutureWeatherListAdapter.FutureWeatherListAdapterViewHolder,
        position: Int
    ) = holder.bind(data[position])

    override fun getItemCount() = data.size

    inner class FutureWeatherListAdapterViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val binding = ItemFutureListBinding.bind(itemView)
        fun bind(item: DailyItem) = with(itemView) {
            //Bind the data with View
            binding.tvDate.text=TimeConverter.instance.convertToDate(item.dt!! *1000)
            binding.tvDay.text=TimeConverter.instance.convertToDay(item.dt *1000)
            val windSpeed=item.windSpeed.toString()+" m/s"
            binding.tvWindSpeed.text = windSpeed
            val minTemp= item.temp?.min.toString() + " °C"
            val maxTemp= item.temp?.max.toString()+ " °C"
            binding.tvMin.text =minTemp
            binding.tvMax.text =maxTemp
            setOnClickListener {
                onItemClick?.invoke("DATA")
            }
        }
    }
}