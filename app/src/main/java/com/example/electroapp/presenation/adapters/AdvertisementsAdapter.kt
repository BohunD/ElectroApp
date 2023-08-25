package com.example.electroapp.presenation.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.electroapp.R
import com.example.electroapp.data.models.Advertisement
import com.example.electroapp.data.util.NO_ADVERTISEMENT_PHOTO
import com.example.electroapp.data.util.getDateTimeFromLong
import com.example.electroapp.data.util.loadUrl
import com.example.electroapp.databinding.ItemAdvertisementDemoBinding
import com.example.electroapp.presenation.listeners.AdListener

class AdvertisementsAdapter(
    private val adsList: ArrayList<Advertisement>,
    private val userId: String
) : RecyclerView.Adapter<AdvertisementsAdapter.AdsViewHolder>() {

    private var listener: AdListener?=null
    class AdsViewHolder(binding: ItemAdvertisementDemoBinding) : RecyclerView.ViewHolder(binding.root) {
        val tvName = binding.tvAdsName
        val tvPrice = binding.tvAdsPrice
        val ivPhoto = binding.ivAdsPhoto
        val tvDate = binding.tvAdsDate
        val tvCity = binding.tvAdsCityName
        val layout = binding.layout
        val ivLike = binding.ivLike
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdsViewHolder {
        val binding = ItemAdvertisementDemoBinding.inflate(
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
        holder.ivLike.setOnClickListener { listener?.onLikeClick(adsList[position]) }
        if(adsList[position].likedUsers?.contains(userId)==true) {
            holder.ivLike.setImageDrawable(
                ContextCompat.getDrawable(
                    holder.ivLike.context,
                    R.drawable.like_active
                )
            )

        }

        else {
            holder.ivLike.setImageDrawable(
                ContextCompat.getDrawable(
                    holder.ivLike.context,
                    R.drawable.like
                )
            )
        }

    }
    fun updateLikeStatus(ad: Advertisement, isLiked: Boolean) {
        val position = adsList.indexOf(ad)
        if (position != -1) {
            adsList[position].likedUsers?.let { likedUsers ->
                if (isLiked) {
                    likedUsers.add(userId)
                } else {
                    likedUsers.remove(userId)
                }
            }
            notifyItemChanged(position)
        }
    }

    fun setListener(l: AdListener){
        listener = l
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateAds(newAds: List<Advertisement>) {
        adsList.clear()
        adsList.addAll(newAds)
        notifyDataSetChanged()
    }
}