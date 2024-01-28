package com.example.kotlincountries.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.kotlincountries.R
import com.example.kotlincountries.databinding.FragmentCountryBinding
import com.example.kotlincountries.util.downloadFromUrl
import com.example.kotlincountries.util.placeholderProgressBar
import com.example.kotlincountries.viewmodel.CountryViewModel
import kotlinx.android.synthetic.main.fragment_country.*
import kotlinx.coroutines.InternalCoroutinesApi


class CountryFragment : Fragment() {

    private lateinit var viewModel:CountryViewModel
    private lateinit var dataBinding : FragmentCountryBinding //bindingin fragment daki farklarından biri adaptere göre

    private var CountryUuid = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding=DataBindingUtil.inflate(inflater,R.layout.fragment_country,container,false)
        return dataBinding.root
    }


    @InternalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            CountryUuid=CountryFragmentArgs.fromBundle(it).CountryUuid

        viewModel=ViewModelProviders.of(this).get(CountryViewModel::class.java)
        viewModel.getDataFromRoom(CountryUuid)


            //created içinde bu fonksiyonu çağırmayı unutma
            observeLiveData()
        }
    }

    private fun observeLiveData(){
        viewModel.countryLiveData.observe(viewLifecycleOwner, Observer {  country ->
            country?.let {

                dataBinding.selectedCountrie=it
                /*
                country_name.text=country.countryName
                country_capital.text=country.capitalName
                country_currency.text=country.currencyName
                country_region.text=country.regionName
                country_language.text=country.languageName
                context?.let{
                    country_image.downloadFromUrl(country.flagimageUrl, placeholderProgressBar(it))


                 */
                }
            })

        }


    }




