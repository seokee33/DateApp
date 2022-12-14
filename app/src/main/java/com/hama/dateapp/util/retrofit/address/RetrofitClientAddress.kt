package com.hama.dateapp.util.retrofit.address

import android.util.Log
import com.hama.dateapp.util.isJsonArray
import com.hama.dateapp.util.isJsonObject
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClientAddress {
    // 레트로핏 클라이언트 선언

    private var retrofitClient: Retrofit? = null

    //레트로핏 클라이언트 가져오기
    fun getClient(baseUrl: String): Retrofit? {
        Log.d("RetrofitClient", "RetrofitClient - getClient() called")


        //로킹 인터셉터 추가
//        OkHttp 인스턴스 생성
        val client = OkHttpClient.Builder()

        //로그를 찍기 위해 로깅 인터셉터 추가
        val loggingInterceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Log.d("RetrofitClient", "RetrofitClient - log() called /message : $message")
                when {
                    message.isJsonObject() -> {
                        Log.d("RetrofitClient", JSONObject(message).toString(4))
                    }
                    message.isJsonArray() -> {
                        Log.d("RetrofitClient", JSONObject(message).toString(4))
                    }
                    else ->{
                        try{
                            Log.d("RetrofitClient", JSONObject(message).toString(4))
                        }catch (e:Exception){
                            Log.d("RetrofitClient", message)
                        }
                    }
                }

            }
        })
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        // 위에서 설정한 로깅 인터셈터를 okhttp 컬라이언트에 추가한다.
        client.addInterceptor(loggingInterceptor)

        if (retrofitClient == null) {
            retrofitClient = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                //위에서 설정한 크라이언트로 레트로핏 클라인언트를 설정한다.
                .client(client.build())
                .build()
        }
        return retrofitClient
    }
}