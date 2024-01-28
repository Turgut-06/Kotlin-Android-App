package com.example.kotlincountries.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.kotlincountries.model.Country

@Dao //bir data access object olduğunu belirtiyoruz
interface CountryDAO {

    //data access object
    //veritabanına ulaşmak istediğimiz metotları burada yazıyoruz

    @Insert
    suspend fun insertAll(vararg countries:Country) : List<Long>

    //insert --> INSERT INTO

    //suspend  --> couroutineler içerisinde çağırılıyor, courotine ana thread i yormadan kasmadan başka bir thread de çalışmamızı sağlar
               // fonksiyonları durdurup yeterli zaman ve güç olunca devam ettirmeye yarayan keywordümüz suspenddir pause&resume

    // varargs  --> sayısı belli olmayan,sayısını tam bilemediğimiz zamanlarda bir tekil objeyi farklı sayılarla belirtmemizi sağlayan keyworddür var yazsaydık 1 tane olurdu
                // 10-15 tane country objesi veriyoruz ama tek tek

    // List<Long> --> model sınıfında verdiğimiz primary keyi döndürüyor

    @Query("SELECT *FROM Country")  //içine istediğim sorguyu yazabilirim
    suspend fun getAllCountries() : List<Country>

    @Query("SELECT *FROM Country WHERE uuıd=:countryID")
    suspend fun getCountry(countryID : Int) : Country  // detay vereeğimiz country fragment da tek country döndürmek istediğimizde bu

    @Query("DELETE FROM Country")  // veritabanını siler
    suspend fun deleteAllCountries()

    @Query("DELETE FROM Country WHERE uuıd=:countryID")
    suspend fun deleteCountry(countryID: Int)
}