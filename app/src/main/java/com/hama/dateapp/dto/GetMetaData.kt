package com.hama.dateapp.dto

import com.google.gson.annotations.SerializedName

data class GetMetaData(
    @SerializedName("total_count")
    var total_count:String,
    @SerializedName("pageable_count")
    var pageable_count:String,
    @SerializedName("is_end")
    var is_end:String
)

