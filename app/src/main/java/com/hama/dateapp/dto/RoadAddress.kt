package com.hama.dateapp.dto

import com.google.gson.annotations.SerializedName

data class RoadAddress(
    @SerializedName("address_name")
    var address_name:String,
    @SerializedName("region_1depth_name")
    var region_1depth_name:String,
    @SerializedName("region_2depth_name")
    var region_2depth_name:String,
    @SerializedName("region_3depth_name")
    var region_3depth_name:String,
    @SerializedName("road_name")
    var road_name:String,
    @SerializedName("underground_yn")
    var underground_yn:String,
    @SerializedName("main_building_no")
    var main_building_no:String,
    @SerializedName("sub_building_no")
    var sub_building_no:String,
    @SerializedName("building_name")
    var building_name:String,
    @SerializedName("zone_no")
    var zone_no:String,
    @SerializedName("x")
    var x:String,
    @SerializedName("y")
    var y:String
)
