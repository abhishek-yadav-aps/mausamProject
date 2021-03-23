package com.abhitom.mausamproject.ui.weather.future.list

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abhitom.mausamproject.data.database.entity.DailyItem
import com.abhitom.mausamproject.databinding.FutureListWeatherFragmentBinding
import com.abhitom.mausamproject.ui.base.ScopedFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class  FutureListWeatherFragment : ScopedFragment(), KodeinAware {

    private lateinit var binding: FutureListWeatherFragmentBinding
    override val kodein by closestKodein()
    private val viewModelFactory: FutureListWeatherViewModelFactory by instance()

    private lateinit var viewModel: FutureListWeatherViewModel
    private val futureWeatherListAdapter = FutureWeatherListAdapter()
    private var graphDecorator: RecyclerView.ItemDecoration? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FutureListWeatherFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(FutureListWeatherViewModel::class.java)

        bindUi()
    }

    private fun bindUi() = launch(Dispatchers.Main){

        binding.rvFutureList.apply {
            setHasFixedSize(true)
            adapter = futureWeatherListAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        }

        val futureWeather = viewModel.weather.await()
        val currentLocation = viewModel.location.await()
        try {
            futureWeather.observe(viewLifecycleOwner, Observer {
                if (it == null) return@Observer

                binding.pbFutureLoading.visibility=View.GONE
                binding.rvFutureList.visibility=View.VISIBLE
                futureWeatherListAdapter.swapData(it as MutableList<DailyItem>)
                graphDecorator?.let { gd -> binding.rvFutureList.removeItemDecoration(gd) }
                graphDecorator = FutureWeatherListCurlyItemDecorator(it, this@FutureListWeatherFragment.requireContext())
                graphDecorator?.let { gd -> binding.rvFutureList.addItemDecoration(gd) }
            })

            currentLocation.observe(viewLifecycleOwner, Observer {
                if (it == null) return@Observer
                val loc=it.name+", "+it.country
                binding.tvFutureLocation.text=loc
            })

        }catch (e:IllegalStateException){
            Log.i("EXCEPTION",e.message.toString())
        }
    }

}