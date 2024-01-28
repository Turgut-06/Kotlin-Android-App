package com.example.kotlincountries.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel(application: Application) :AndroidViewModel(application) , CoroutineScope {

    private val job= Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main // işini yap ve main threade dön anlamına geliyor

    override fun onCleared() {  //bir şey destroy olursa(Ör. uygulama kapatılırsa)
        super.onCleared()

        job.cancel()  //app contexti de giderse iş iptal ediliyor
    }
}