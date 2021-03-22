package com.abhitom.mausamproject.ui.weather.future.list

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.abhitom.mausamproject.databinding.FutureListWeatherFragmentBinding
import com.abhitom.mausamproject.ui.base.ScopedFragment
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class FutureListWeatherFragment : ScopedFragment(), KodeinAware {

    private lateinit var binding: FutureListWeatherFragmentBinding
    override val kodein by closestKodein()
    private val viewModelFactory: FutureListWeatherViewModelFactory by instance()

    private lateinit var viewModel: FutureListWeatherViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FutureListWeatherFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(FutureListWeatherViewModel::class.java)

        bindUi()
    }

    private fun bindUi() = launch{
        val futureWeather = viewModel.weather.await()
        futureWeather.observe(viewLifecycleOwner, Observer {
            if (it == null) return@Observer
            var data=""
            for (i in it.indices){
                data+=it[i].toString()+"\n\n\n"
            }
            binding.tv.text=data
        })
    }

}