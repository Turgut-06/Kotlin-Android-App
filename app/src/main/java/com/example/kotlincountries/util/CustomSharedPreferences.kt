package com.example.kotlincountries.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager

class CustomSharedPreferences {

    //shared preferences singletonımı burada oluşturuyorum
    companion object{


        private val PREFERENCES_TIME="Preferences_time"
        private var sharedPreferences:SharedPreferences?=null  //intialize etmedik içine null atadık

        @Volatile private var instance : CustomSharedPreferences?=null

        private val lock=Any()

        //instance varsa instance döndürülecek yoksa senkronize şekilde içerdeki işlem yapılacak(shared preferences oluşturma işlemi)
        operator fun invoke(context: Context) : CustomSharedPreferences = instance ?: synchronized(lock){
            instance ?: makeCustomSharedPreferences(context).also {
                instance=it
            }
        }
        private fun makeCustomSharedPreferences(context: Context) : CustomSharedPreferences {
            sharedPreferences =PreferenceManager.getDefaultSharedPreferences(context)
            return CustomSharedPreferences()
        }
    }

    fun saveTime(time:Long){

        //commit=true yapınca içerdeki işlemler otomatik commit oluyor
        sharedPreferences?.edit(commit = true) {
           putLong(PREFERENCES_TIME,time)

        }

    }

    fun getTime()= sharedPreferences?.getLong(PREFERENCES_TIME, 0)
}