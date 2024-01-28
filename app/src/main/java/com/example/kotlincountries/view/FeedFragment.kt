package com.example.kotlincountries.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlincountries.view.FeedFragmentDirections
import com.example.kotlincountries.R
import com.example.kotlincountries.adapter.CountryAdapter
import com.example.kotlincountries.model.Country
import com.example.kotlincountries.viewmodel.FeedViewModel
import kotlinx.android.synthetic.main.fragment_feed.*
import kotlinx.coroutines.InternalCoroutinesApi


class FeedFragment : Fragment() {

    private lateinit var viewModel: FeedViewModel
    private val countryAdapter= CountryAdapter(arrayListOf())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    @InternalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel=ViewModelProviders.of(this).get(FeedViewModel::class.java)
        viewModel.refreshData()

        //fragment_feed deki recyclerview ın id si countryList
        countryList.layoutManager=LinearLayoutManager(context)
        countryList.adapter=countryAdapter

        observeLiveData()

        swipeRefreshLayout.setOnRefreshListener {
            countryList.visibility=View.GONE
            errorMessage.visibility=View.GONE
            countryLoading.visibility=View.GONE
            viewModel.refreshFromAPI()

            swipeRefreshLayout.isRefreshing=false
        }

        /*fragment_button.setOnClickListener {
            val action= FeedFragmentDirections.actionFeedFragmentToCountryFragment()
            Navigation.findNavController(it).navigate(action)
        }

         */
    }

    //ViewModel da oluşturduğumuz livedata ları burada gözlemledik değişiklik varsa ona göre kodunu yazdık
    //bütün viewlarda bu fonksiyon olacağından private yapıyoruz
    private fun observeLiveData() {
          viewModel.countries.observe(viewLifecycleOwner, Observer { countries->
              countries?.let {
                  countryList.visibility=View.VISIBLE
                  countryAdapter.updateCountryList(countries)
              }

          })

        viewModel.countyError.observe(viewLifecycleOwner, Observer { error ->
            error?.let {
                if(it){
                    errorMessage.visibility=View.VISIBLE
                }
                else{
                    errorMessage.visibility=View.GONE
                }

            }

        })

        viewModel.countryLoading.observe(viewLifecycleOwner, Observer { loading->
            loading?.let {
                if(it){
                    countryLoading.visibility=View.VISIBLE
                    countryList.visibility=View.GONE
                    errorMessage.visibility=View.GONE
                }
                else{
                    countryLoading.visibility=View.GONE
                }
            }

        })
    }


}