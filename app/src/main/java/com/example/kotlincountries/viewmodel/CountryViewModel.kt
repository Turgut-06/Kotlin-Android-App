package com.example.kotlincountries.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlincountries.model.Country
import com.example.kotlincountries.service.CountryDatabase
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch
import java.util.*

class CountryViewModel(application: Application) :BaseViewModel(application) {

    var countryLiveData=MutableLiveData<Country>()

    @InternalCoroutinesApi
    fun getDataFromRoom(uuıd: Int){
        launch {
            val dao=CountryDatabase(getApplication()).countryDao()
            val country=dao.getCountry(uuıd)
            countryLiveData.value=country

        }

    }


}