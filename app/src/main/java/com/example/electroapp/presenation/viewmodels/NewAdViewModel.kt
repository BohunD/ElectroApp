package com.example.electroapp.presenation.viewmodels

import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.electroapp.R
import com.example.electroapp.data.models.Category
import com.example.electroapp.data.util.CATEGORIES
import com.example.electroapp.data.util.loadFromDrawable

class NewAdViewModel : ViewModel() {
    val photoListLiveData = MutableLiveData<MutableList<String>>()

    val selectedCategory = MutableLiveData<Category?>()
    val selectedFilters = MutableLiveData<Map<String, ArrayList<String>>?>()
    val filters = MutableLiveData<Map<String, String>>()
    private val categories = CATEGORIES

    val isCategorySelected = MutableLiveData<Boolean>()
    val isNameValid = MutableLiveData<Boolean>()
    val isDescriptionValid = MutableLiveData<Boolean>()
    val isPriceValid = MutableLiveData<Boolean>()
    val isCityValid = MutableLiveData<Boolean>()
    val isNameCorrectLength = MutableLiveData<Boolean>()

    fun addFilters(name: String, filter: String) {
        val pair = Pair(name, filter)
        val currentFilters = filters.value ?: emptyMap()
        val updatedFilters = currentFilters + pair
        filters.value = updatedFilters
    }


    fun updateSelectedCategoryAndFilters(position: Int) {
        if (position != 0) {
            val selectedCategory = categories[position - 1]
            this.selectedCategory.value = selectedCategory
            this.selectedFilters.value = selectedCategory?.filters
        } else {
            selectedCategory.value = null
            selectedFilters.value = null
        }
    }


    fun init() {
        val currentList = mutableListOf<String>()
        currentList.add("")
        photoListLiveData.value = currentList
        selectedCategory.value = null
        selectedFilters.value = null

        isCategorySelected.value = false
        isNameValid.value = true
        isDescriptionValid.value = true
        isPriceValid.value = true
    }

    fun canPostAdvertisement(): Boolean {
        return isCategorySelected.value == true &&
                isNameValid.value == true &&
                isDescriptionValid.value == true &&
                isPriceValid.value == true &&
                isCityValid.value == true
    }

    fun addPhoto(uri: Uri?) {
        uri?.let {

            photoListLiveData.value?.add(uri.toString())
        }
    }

    fun removePhoto(position: Int) {
        val currentList = photoListLiveData.value ?: mutableListOf()
        if (position in 0 until currentList.size) {
            currentList.removeAt(position)
            photoListLiveData.value = currentList
        }
    }
}
