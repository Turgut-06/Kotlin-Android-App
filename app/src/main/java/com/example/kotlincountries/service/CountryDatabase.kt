package com.example.kotlincountries.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.kotlincountries.model.Country
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

@Database(entities = arrayOf(Country::class),version = 1) //veritabanına birden fazla model koymak istiyorsa arrayOf içine yazarsın birden fazla tablo
abstract class CountryDatabase :RoomDatabase() { //veritabanında değişiklik yapıp rooma farklı fonksiyon eklerse versionu değiştirmen gerekir

   abstract fun countryDao() : CountryDAO

  //database i singleton mantığıyla oluşturuyoruz içerisinden tek bir obje(instance) oluşturulabilen sınıftır
  //daha önce oluşturulmuş obje yoksa oluşturuyoruz eğer varsa yeni oluşturmayıp oluşturulan objeyi çekiyoruz

  //companion object her yerden ulaşmamızı sağlıyor,diğer scopelardan ulaşalım diye companion object yapıyoruz statik bir şekilde değişken oluşturuyor
   companion object{

      //volatile olarak tanımladığında property farklı threadlere görünür hale geliyor farklı threadlerden çağrılma couroutin kullandığımız için volatile kullanmamız gerekiyor singleton yapma amacımız farklı threadlerde çalıştırmak istememiz
      @Volatile private var instance : CountryDatabase ?=null

      //instance var mı yok mu oluşturmuş hazır var mı bunu invoke ile kontrol ediyoruz
      //synchronized in kitlenip kitlenmeyeceğini kontrol için lock oluşturuyoruz içine direk any atsanda olur
      //instance varsa instance döndürülecek yoksa senkrozine şekilde instance a ulaşılacak

      private val lock=Any()
      @InternalCoroutinesApi
      operator fun invoke(context: Context) = instance ?: synchronized(lock)   //birden fazla thread instance oluşturmaya çalışırsa synchronized yaparsak farklı zamanlarda çalıştırıyor aynı anda çalışmalarını önlüyor
      {
        instance?: makeDatabase(context).also {
            instance=it
        }
      }
      private fun makeDatabase(context: Context) = Room.databaseBuilder(
              context.applicationContext ,CountryDatabase::class.java,"countryDatabase"    //ekran yan çevirince sıkıntı olmasın diye application ın kendi context ini aldım
      ).build()
   }


}