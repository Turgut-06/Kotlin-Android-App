package com.example.kotlincountries.service

import com.example.kotlincountries.model.Country
import io.reactivex.Single
import retrofit2.http.GET

interface CountryAPI {
    //genelde verileri çekerken GET verileri değiştirirken POST kullanırız

    //Retrofit url leri 2 ye ayırıyor
    //BASE_URL --> https://raw.githubusercontent.com/  yani normal adresimiz
    //EXT_URL --> atilsamancioglu/IA19-DataSetCountries/master/countrydataset.json yani url nin devamı


    //nasıl call yapılacağını yani nasıl verilerin alınacağını söylüyoruz RXjava(observable,...) kullanıyoruz
    //bir veriyi devamlı şekilde güncelleyip hızlıca almamız gerekiyorsa --> observable
    //eğer bir veriyi bir defa garanti bir şekilde almamız gerekiyorsa --> single ı kullanırız

    @GET("atilsamancioglu/IA19-DataSetCountries/master/countrydataset.json")
    fun getCountries():Single<List<Country>>

    //istediğim kadar GET ve POST yani call yapabilirim
}