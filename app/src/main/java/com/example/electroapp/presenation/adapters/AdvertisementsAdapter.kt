package com.example.electroapp.presenation.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.electroapp.data.models.Advertisement
import com.example.electroapp.data.util.NO_ADVERTISEMENT_PHOTO
import com.example.electroapp.data.util.getDateTimeFromLong
import com.example.electroapp.data.util.loadUrl
import com.example.electroapp.databinding.ItemYourAdDemoBinding
import com.example.electroapp.presenation.listeners.AdListener

class AdvertisementsAdapter(
    private val adsList: ArrayList<Advertisement>
) : RecyclerView.Adapter<AdvertisementsAdapter.AdsViewHolder>() {

    private var listener: AdListener?=null
    class AdsViewHolder(binding: ItemYourAdDemoBinding) : RecyclerView.ViewHolder(binding.root) {
        val tvName = binding.tvAdsName
        val tvPrice = binding.tvAdsPrice
        val ivPhoto = binding.ivAdsPhoto
        val tvDate = binding.tvAdsDate
        val tvCity = binding.tvAdsCityName
        val layout = binding.layout
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdsViewHolder {
        val binding = ItemYourAdDemoBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return AdsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return adsList.size
    }

    override fun onBindViewHolder(holder: AdsViewHolder, position: Int) {
        bind(holder, position, listener)
    }

    private fun bind(holder: AdsViewHolder, position: Int, listener: AdListener?) {
        holder.tvName.text = adsList[position].name
        holder.tvPrice.text = adsList[position].price
        holder.tvCity.text = adsList[position].city
        holder.tvDate.text = getDateTimeFromLong(adsList[position].dateTime)
        if (adsList[position].photos?.size!! > 0) {
            Log.d("Looog", adsList[position].photos?.get(0).toString())
            Glide.with(holder.itemView).load(adsList[position].photos?.get(0)).into(holder.ivPhoto)

        } else {
            holder.ivPhoto.loadUrl(holder.ivPhoto.context, NO_ADVERTISEMENT_PHOTO)
        }

            holder.layout.setOnClickListener {
                listener?.onLayoutClick(adsList[position])
            }
    }

    fun setListener(l: AdListener){
        listener = l
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateAds(newAds: List<Advertisement>){
        adsList.clear()
        adsList.addAll(newAds)
        Log.d("Looog", adsList.toString())
        notifyDataSetChanged()
    }
}