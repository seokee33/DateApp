package com.hama.dateapp.dto

import com.google.gson.annotations.SerializedName

data class LocationAddress(
    @SerializedName("address_name")
    var address_name:String,
    @SerializedName("y")
    var y:String,
    @SerializedName("x")
    var x:String,
    @SerializedName("address_type")
    var address_type:String,
    @SerializedName("address")
    var address:AddressName,
    @SerializedName("road_address")
    var road_address:RoadAddress
){
    override fun toString(): String {
        return "address_name : $address_name"
    }
}
