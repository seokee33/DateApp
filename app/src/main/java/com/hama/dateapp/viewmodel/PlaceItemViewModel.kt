package com.hama.dateapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hama.dateapp.model.PlaceInfo

class PlaceItemViewModel : ViewModel() {
    private val mutableSelectedItem = MutableLiveData<PlaceInfo>()
    val selectedItem: LiveData<PlaceInfo> get() = mutableSelectedItem

    fun selectItem(item: PlaceInfo) {
        mutableSelectedItem.value = item
    }

}