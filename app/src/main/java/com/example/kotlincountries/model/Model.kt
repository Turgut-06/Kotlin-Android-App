package com.example.kotlincountries.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

//veri sınıfı oluşturuyoruz verileri çekeceğimiz için

//local veritabanı sqlite ı room ile yönetiyoruz
//Entity ve column room un bileşenleri
//column ismi versem değişken ismini column ismi yapar(ör. regionName)

@Entity
data class Country(

        @ColumnInfo(name= "name")
        @SerializedName("name")
        val countryName:String?,

        @ColumnInfo(name="region")
        @SerializedName("region")
        val regionName:String?,

        @ColumnInfo(name = "capital")
        @SerializedName("capital")
        val capitalName:String?,

        @ColumnInfo(name="language")
        @SerializedName("language")
        val languageName:String?,

        @ColumnInfo(name="curreny")
        @SerializedName("currency")
        val currencyName:String?,

        @ColumnInfo(name="flag")
        @SerializedName("flag")
        val flagimageUrl:String?) {

        @PrimaryKey(autoGenerate = true)  // ben hiçbir şey yapmadan model kendisi oluşturucak
        var uuıd=0    // veri sınıfıma body açıp içine primary keyi giriyoruz constructor içine girmedik dikkat her oluşturduğunda pk istemesin diye
}

