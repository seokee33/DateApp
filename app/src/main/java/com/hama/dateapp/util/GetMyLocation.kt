package com.hama.dateapp.util

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*

class GetMyLocation private constructor() {
    companion object {
        private var instance: GetMyLocation? = null

        private lateinit var context: Context

        fun getInstance(_context: Context): GetMyLocation {
            return instance ?: synchronized(this) {
                instance ?: GetMyLocation().also {
                    context = _context
                    instance = it
                }
            }
        }
    }
    //현위치 가져오기
    private var mFusedLocationProviderClient: FusedLocationProviderClient? =
        null // 현재 위치를 가져오기 위한 변수
    var mLastLocation: Location? = null // 위치 값을 가지고 있는 객체
    private lateinit var mLocationRequest: LocationRequest  // 위치 정보 요청의 매개변수를 저장하는
    private val REQUEST_PERMISSION_LOCATION = 10
    lateinit var myLocation:Location
    /**현위치 가져오기*/
    @SuppressLint("MissingPermission")
    fun getLocation():Location? {
        if(context == null)
            Log.w("getLocation!!!!!!!!!!!!!!","context : null")
        //FusedLocationProviderClient의 인스턴스를 생성.
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return null
        }
        mLocationRequest = LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        // 기기의 위치에 관한 정기 업데이트를 요청하는 메서드 실행
        // 지정한 루퍼 스레드(Looper.myLooper())에서 콜백(mLocationCallback)으로 위치 업데이트를 요청
        mFusedLocationProviderClient!!.requestLocationUpdates(
            mLocationRequest,
            mLocationCallback,
            Looper.getMainLooper()
        ).addOnSuccessListener {
            Log.w("mFusedLocationProviderClient , addOnSuccessListener!!!!!!!!!!!","Success")
        }.addOnFailureListener {
            Log.w("mFusedLocationProviderClient , addOnFailureListener!!!!!!!!!!!",it.toString())
        }
        Handler().postDelayed(Runnable {
            if(mLastLocation == null)
                Log.w("getLocation!!!!!!!!!!!!!!","mLastLocation : null")
        },2500)
        return mLastLocation;
    }

    // 시스템으로 부터 위치 정보를 콜백으로 받음
    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            mLastLocation =locationResult.lastLocation
        }
    }

}