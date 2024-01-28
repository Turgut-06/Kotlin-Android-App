package com.example.kotlincountries.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.example.kotlincountries.R
import com.example.kotlincountries.databinding.ItemCountryBinding
import com.example.kotlincountries.model.Country
import com.example.kotlincountries.util.downloadFromUrl
import com.example.kotlincountries.util.placeholderProgressBar
import com.example.kotlincountries.view.FeedFragmentDirections
import kotlinx.android.synthetic.main.item_country.view.*

//Country modelimi parametre olarak verdim
class CountryAdapter(val countryList: ArrayList<Country>):RecyclerView.Adapter<CountryAdapter.CountryViewHolder>(),CountryClickListener {

    //view yerine binding kullacağımızı belirtiyoruz aşağı tarafı da view yerine binding olacak şekilde ayarlıyoruz
    //view.root binding ile bağlanan view ı bize veriyor
    class CountryViewHolder(var view: ItemCountryBinding) : RecyclerView.ViewHolder(view.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val inflater=LayoutInflater.from(parent.context)
        //val view=inflater.inflate(R.layout.item_country,parent,false)
        val view=DataBindingUtil.inflate<ItemCountryBinding>(inflater,R.layout.item_country,parent,false)
        return CountryViewHolder(view) //buradaki view yukardaki sınıfının constructorına girer oda yana paslar
    }

    //item_country deki itemlara burda direk ulaşıp işlem yapabiliyorum
    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {

        //country variable ı layout kısmında oluşturduk , diğer türlü eski usül binding de arka planda R.id şekline dönüşüyor ve belleği yoruyor böyle daha efektif
        holder.view.country=countryList[position]
        holder.view.listener=this //listenerı bu sınıfa implemente ettiğimiz için direk this veriyoruz




        /* eski hali databinding öncesi
        holder.view.name.text=countryList[position].countryName //pozisyonu da belirterek model üzerinden o andaki ülkenin ismini alıyorum
        holder.view.region.text=countryList[position].regionName

        holder.view.imageView.downloadFromUrl(countryList[position].flagimageUrl, placeholderProgressBar(holder.view.context))

        //bir view a tıkladığında ne olacağını kodluyoruz
        holder.view.setOnClickListener{
            val action=FeedFragmentDirections.actionFeedFragmentToCountryFragment(countryList[position].uuıd)
            Navigation.findNavController(it).navigate(action)
        }

         */


    }

    //kaç tane row oluşturacağını söylüyoruz
    override fun getItemCount(): Int {
       return countryList.size
    }

    //kullanıcı swipe refresh ile aşağı çekip yenilediğinde güncelleme olursa bundan adapter ünde bilgisi olması gerekiyor
    fun updateCountryList(newCountryList: List<Country>) {
        countryList.clear()
        countryList.addAll(newCountryList)
        notifyDataSetChanged() //adapterü yenilemek için kullandığımız metot
    }

    override fun onCountryClicked(v: View) {
        val uuid=v.countryuuidText.text.toString().toInt()
        val action=FeedFragmentDirections.actionFeedFragmentToCountryFragment(uuid)  //uuid yi alıp 2.fragment a geçiyor
        Navigation.findNavController(v).navigate(action)

    }

}