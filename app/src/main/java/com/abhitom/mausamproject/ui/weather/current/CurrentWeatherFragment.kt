package com.abhitom.mausamproject.ui.weather.current

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.abhitom.mausamproject.data.OpenWeatherAPIRetrofitClient
import com.abhitom.mausamproject.data.response.OneCallResponse
import com.abhitom.mausamproject.databinding.CurrentWeatherFragmentBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CurrentWeatherFragment : Fragment() {

    lateinit var binding: CurrentWeatherFragmentBinding

    companion object {
        fun newInstance() = CurrentWeatherFragment()
    }

    private lateinit var viewModel: CurrentWeatherViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = CurrentWeatherFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CurrentWeatherViewModel::class.java)

        OpenWeatherAPIRetrofitClient.instance.openWeatherAPIService.oneCallApi(33.441792,94.037689 , "metric")
                .enqueue(object : Callback<OneCallResponse> {
                    override fun onResponse(
                            call: Call<OneCallResponse>,
                            response: Response<OneCallResponse>
                    ) {
                        if (response.isSuccessful) {
                            binding.tvCurrentWeatherFragment.text= response.body().toString()
                        } else {
                            Toast.makeText(context,response.errorBody().toString(),Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<OneCallResponse>, t: Throwable) {
                        Toast.makeText(context,"No Internet / Server Down",Toast.LENGTH_SHORT).show()
                    }
                })
    }

}