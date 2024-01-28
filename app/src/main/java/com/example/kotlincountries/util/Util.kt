package com.example.kotlincountries.util

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.kotlincountries.R

//İstediğim nesne üzerine onun sınıfı üstünden extension oluşturabiliyorum Ör. String sınıfı,Imageview sınıfı

// Imageview üzerine extension kuruyoruz
fun ImageView.downloadFromUrl(url:String?,progressDrawable: CircularProgressDrawable){

    val options=RequestOptions()
            .placeholder(progressDrawable) //resim yüklenene inene kadar içinde ne göstereceğimiz
            .error(R.mipmap.ic_launcher_round)  //hata olduğunda burası çalışacak

    Glide.with(context)  //activity de miyiz fragment da mıyız onun için
            .setDefaultRequestOptions(options)
            .load(url)
            .into(this) //hangi imageview a referans vermek istiyor bu görünüme yükle diyoruz


}

fun placeholderProgressBar(context: Context) : CircularProgressDrawable {  //direk circularprogressdrawable ım var

    //ne kadar kalın ne kadar büyük olacağını belirtiyoruz
    return  CircularProgressDrawable(context).apply {
        strokeWidth=9f
        centerRadius=35f
        start()
    }
}

//Binding Adapter ile bu fonksiyonu xml de çalıştırmama olanak sağlıyor
@BindingAdapter("android:downloadUrl")
fun downloadImage(view: ImageView,url: String?){
    view.downloadFromUrl(url, placeholderProgressBar(view.context))
}