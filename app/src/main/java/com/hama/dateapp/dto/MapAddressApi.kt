package com.hama.dateapp.dto

import com.google.gson.annotations.SerializedName

data class MapAddressApi(
    @SerializedName("documents")
    var documents:ArrayList<LocationAddress>,
    @SerializedName("meta")
    var meta:GetMetaData
)
