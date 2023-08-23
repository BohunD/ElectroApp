package com.example.electroapp.presenation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.electroapp.data.models.Advertisement

class AdvertisementViewModel: ViewModel() {
    var currentAd = MutableLiveData<Advertisement>()

    fun setAdvertisement(ad: Advertisement){
        currentAd.value = ad
    }
}