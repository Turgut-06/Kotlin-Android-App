package com.example.kotlincountries.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlincountries.model.Country
import com.example.kotlincountries.service.CountryAPIService
import com.example.kotlincountries.service.CountryDatabase
import com.example.kotlincountries.util.CustomSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch

class FeedViewModel(application: Application):BaseViewModel(application) {  //baseviewmodel sayesinde couroutine i kullanabiliyorum

    private val countryAPIService = CountryAPIService()
    //internetten veri indirdikçe disposable içine atıyoruz işimiz bitince disposable dan kurtulup hafızayı verimli kullanmış oluyoruz
    private val disposable=CompositeDisposable()
    private var customPreferences=CustomSharedPreferences(getApplication())
    private var refreshTime= 10 * 60 * 1000 * 1000 * 1000L //nanosaniye cinsinden 10 dakikayı verecek bize

    //değiştirebilir olduğundan dolayı mutable
    val countries= MutableLiveData<List<Country>>()
    val countyError =MutableLiveData<Boolean>()
    val countryLoading=MutableLiveData<Boolean>()

    @InternalCoroutinesApi
    fun refreshData(){
        val updateTime=customPreferences.getTime()
        if(updateTime !=null && updateTime != 0L && System.nanoTime() - updateTime > refreshTime){
            getDataFromAPI()

        }
        else{
            getDataFromSQLite()
        }

    }

    @InternalCoroutinesApi
    fun refreshFromAPI(){
        getDataFromAPI()
    }

    //bunu coroutine içerisinde yapıyoruz
    @InternalCoroutinesApi
    private fun getDataFromSQLite(){
        countryLoading.value=true
        launch {
            val countries=CountryDatabase(getApplication()).countryDao().getAllCountries()
            showCountries(countries)
            Toast.makeText(getApplication(),"Countries from SQLite",Toast.LENGTH_LONG).show()
        }

    }

    @InternalCoroutinesApi
    private fun getDataFromAPI(){
        countryLoading.value=true

        disposable.add(
                countryAPIService.getData()
                        .subscribeOn(Schedulers.newThread()) //yeni bir thread de bu işlemi yapıyoruz
                        .observeOn(AndroidSchedulers.mainThread()) //ana thread de gözlemleme işlemini yapıyoruz
                        .subscribeWith(object : DisposableSingleObserver<List<Country>>(){
                            override fun onSuccess(t: List<Country>) {

                                storeInSQLite(t)
                                Toast.makeText(getApplication(),"Countries from API",Toast.LENGTH_LONG).show()


                            }

                            override fun onError(e: Throwable) {
                                countryLoading.value=false
                                countyError.value=true
                                e.printStackTrace()
                            }

                        })
        )
    }

    private fun showCountries(countryList : List<Country>) {

        countries.value=countryList
        countyError.value=false
        countryLoading.value=false
    }

    //çektiğimiz verileri sqLite a kaydetmek istiyoruz
    //main thread de bunu yapmayacağız kullanıcı arayüzünü bloklamayacağız
    @InternalCoroutinesApi
    private fun storeInSQLite(List : List<Country>){

        //launch güncel olan threadi bloklamadığını ve bir job a ihtiyaç duyduğunu söylüyor
        //job iptal olursa burdaki couroutine de iptal olur
        launch {
            //coroutine içerisindeyim
            val dao=CountryDatabase(getApplication()).countryDao()
            dao.deleteAllCountries() //daha önce kalan tüm verileri silip yenilerini ekliyoruz
            val listLong=dao.insertAll(*List.toTypedArray()) //toTypedArray listedekileri tek tek tekil hale getirir, kotlinin özelliği, diziden individual hale getiriyor
            var i=0
            while (i<List.size)
            {
                List[i].uuıd=listLong[i].toInt()   //modelde constructor içinde olmayan uuıd yi veriyorum
                i++
            }
            showCountries(List)
        }
        customPreferences.saveTime(System.nanoTime()) //nanoTime alacağımız en detaylı zaman dilimi hangi zamanda kaydedildiğini burada veriyorum
    }

    override fun onCleared() {
        super.onCleared()

        disposable.clear()
    }

}