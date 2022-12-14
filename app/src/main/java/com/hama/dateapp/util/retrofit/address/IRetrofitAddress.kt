package com.hama.dateapp.util.retrofit.address

import com.google.gson.JsonElement
import com.hama.dateapp.util.API
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface IRetrofitAddress {
    @Headers("Authorization:"+API.CLIENT_ID)
    @GET(API.SEARCH_ADDRESS)
    fun searchAddress(@Query("query") query:String) : Call<JsonElement>

    @Headers("Authorization:"+API.CLIENT_ID)
    @GET(API.SEARCH_COORD2ADDRESS)
    fun searchCoord2Address(@Query("query") query:String) : Call<JsonElement>

    @Headers("Authorization:"+API.CLIENT_ID)
    @GET(API.SEARCH_TRANSCOORD)
    fun searchTranscoord(@Query("query") query:String) : Call<JsonElement>

    @Headers("Authorization:"+API.CLIENT_ID)
    @GET(API.SEARCH_COORD2REGIONCODE)
    fun searchCoord2Regioncode(@Query("query") query:String) : Call<JsonElement>
}