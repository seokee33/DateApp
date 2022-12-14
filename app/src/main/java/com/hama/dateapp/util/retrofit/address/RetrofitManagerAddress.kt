package com.hama.dateapp.util.retrofit.address

import android.util.Log
import com.google.gson.JsonElement
import com.hama.dateapp.util.API
import com.hama.dateapp.util.RESPONSE_STATE
import retrofit2.Call
import retrofit2.Response

class RetrofitManagerAddress {

    companion object{
        val instance = RetrofitManagerAddress()
    }

    private val iRetrofit : IRetrofitAddress? = RetrofitClientAddress.getClient(API.BASE_URL)?.create(IRetrofitAddress::class.java)

    fun searchAddress(searchTerm:String, completion:(RESPONSE_STATE, String)->Unit){
        val term = searchTerm.let {
            it
        }?:""
//        val term = searchTerm ?:""
        val call = iRetrofit?.searchAddress(searchTerm).let {
            it
        }?: return

        call.enqueue(object : retrofit2.Callback<JsonElement>{
            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                Log.d("RetrofitManager","RetrofitManager - onFailure() called / t: $t")
                completion(RESPONSE_STATE.FAIL,t.toString())

            }

            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                Log.d("RetrofitManager","RetrofitManager - OnResponse() called / response : ${response.body().toString()}")
                completion(RESPONSE_STATE.OKAY, response.body().toString())
            }

        })
    }
}