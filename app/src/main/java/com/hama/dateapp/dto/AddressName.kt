package com.hama.dateapp.dto

import com.google.gson.annotations.SerializedName

data class AddressName(
    @SerializedName("address_name")
    var address_name:String,
    @SerializedName("region_1depth_name")
    var region_1depth_name:String,
    @SerializedName("region_2depth_name")
    var region_2depth_name:String,
    @SerializedName("region_3depth_name")
    var region_3depth_name:String,
    @SerializedName("region_3depth_h_name")
    var region_3depth_h_name:String,
    @SerializedName("h_code")
    var h_code:String,
    @SerializedName("b_code")
    var b_code:String,
    @SerializedName("mountain_yn")
    var mountain_yn:String,
    @SerializedName("sub_address_no")
    var sub_address_no:String,
    @SerializedName("x")
    var x:String,
    @SerializedName("y")
    var y:String,
)
