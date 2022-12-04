package com.hama.dateapp.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_COLLAPSED
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED
import com.hama.dateapp.R
import com.hama.dateapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity()
                        ,GoogleMap.OnMyLocationButtonClickListener
                        ,GoogleMap.OnMyLocationClickListener
                        ,OnMapReadyCallback {

    lateinit var binding: ActivityMainBinding

    //뒤로가기
    var backPressedState: Int = 0
    private var doubleBackToExit = false

    //지도
    private lateinit var mMap: GoogleMap
    lateinit var mapFragment: SupportMapFragment

    //현위치 가져오기
    private var mFusedLocationProviderClient: FusedLocationProviderClient? =
        null // 현재 위치를 가져오기 위한 변수
    lateinit var mLastLocation: Location // 위치 값을 가지고 있는 객체
    lateinit var mLocationRequest: com.google.android.gms.location.LocationRequest // 위치 정보 요청의 매개변수를 저장하는
    private val REQUEST_PERMISSION_LOCATION = 10


    //BottomSheet : 더보기
    private lateinit var fragmentManager: FragmentManager
    private lateinit var fragmentTransaction: FragmentTransaction
    private lateinit var moreFragment: MoreFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //권한 설정정
        if (checkPermissionForLocation(this)) {
            startLocationUpdates()
        }

        //BottomSheet
        fragmentManager = supportFragmentManager
        fragmentTransaction = fragmentManager.beginTransaction()
        moreFragment = MoreFragment()

        // 현재 접힌 상태에서의 BottomSheet 귀퉁이의 둥글기 저장
        val cornerRadius = binding.bottomSheet.radius
        val bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet)
        bottomSheetBehavior.setBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                // 상태가 변함에 따라서 할일들을 적어줍니다.
                if (newState == STATE_EXPANDED) {
                    backPressedState = 1
                    fragmentTransaction.add(R.id.fragment, moreFragment)
                    fragmentTransaction.commit()
                } else if (newState == STATE_COLLAPSED) {
                    backPressedState = 0
                }
            }

            override fun onSlide(bottomSheetView: View, slideOffset: Float) {
                // slideOffset 접힘 -> 펼쳐짐: 0.0 ~ 1.0
                if (slideOffset >= 0) {
                    // 둥글기는 펼칠수록 줄어들도록
                    binding.bottomSheet.radius = cornerRadius - (cornerRadius * slideOffset)
                    // 화살표는 완전히 펼치면 180도 돌아가게
//                    binding.guideline1.rotation =  (1 - slideOffset) * 180F
                    // 글자는 조금더 빨리 사라지도록
                    binding.tvMore.alpha = 1 - slideOffset * 2.3F
                    // 내용의 투명도도 같이 조절...
                    binding.fragment.alpha = Math.min(slideOffset * 2F, 1F)
                }
            }
        })


        //지도
        mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        mLocationRequest = LocationRequest.create().apply {

            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }





    }

    /**지도*/
    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.isMyLocationEnabled = true
        mMap.setOnMyLocationButtonClickListener(this)
        mMap.setOnMyLocationClickListener(this)
        startLocationUpdates()  //초기값으로 현위치로 카메라 이동
        mapFragment.view?.findViewById<View>(Integer.parseInt("1"))?.also { it ->
            (it.parent as View).findViewById<View>(Integer.parseInt("2")).also {
                it.updatePadding(top = 150)
            }
        }
    }

    /** 현재위치 마커 클릭시*/
    override fun onMyLocationClick(location: Location) {
        Toast.makeText(this, "Current location:\n$location", Toast.LENGTH_LONG)
            .show()
    }

    /** 현재위치 버튼 클릭*/
    override fun onMyLocationButtonClick(): Boolean {
        if (checkPermissionForLocation(this)) {
            startLocationUpdates()
        }
        return false
    }


    /**현위치 가져오기*/
    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        //FusedLocationProviderClient의 인스턴스를 생성.
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        // 기기의 위치에 관한 정기 업데이트를 요청하는 메서드 실행
        // 지정한 루퍼 스레드(Looper.myLooper())에서 콜백(mLocationCallback)으로 위치 업데이트를 요청
        mFusedLocationProviderClient!!.requestLocationUpdates(
            mLocationRequest,
            mLocationCallback,
            Looper.myLooper()
        )
    }

    // 시스템으로 부터 위치 정보를 콜백으로 받음
    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            // 시스템에서 받은 location 정보를 onLocationChanged()에 전달
            locationResult.lastLocation
            onLocationChanged(locationResult.lastLocation)
        }
    }

    // 시스템으로 부터 받은 위치정보를 화면에 갱신해주는 메소드
    fun onLocationChanged(location: Location) {
        mLastLocation = location
        val myLocation = LatLng(mLastLocation.latitude, mLastLocation.longitude)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 15F))
    }


    // 위치 권한이 있는지 확인하는 메서드
    private fun checkPermissionForLocation(context: Context): Boolean {
        // Android 6.0 Marshmallow 이상에서는 위치 권한에 추가 런타임 권한이 필요
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                true
            } else {
                // 권한이 없으므로 권한 요청 알림 보내기
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_PERMISSION_LOCATION
                )
                false
            }
        } else {
            true
        }
    }

    // 사용자에게 권한 요청 후 결과에 대한 처리 로직
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationUpdates()

            } else {
                Log.d("ttt", "onRequestPermissionsResult() _ 권한 허용 거부")
                Toast.makeText(this, "권한이 없어 해당 기능을 실행할 수 없습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }


    /** 뒤로가기*/
    override fun onBackPressed() {
        super.onBackPressed()
        val bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet)
        if (bottomSheetBehavior.state == STATE_EXPANDED) {
            bottomSheetBehavior.setState(STATE_COLLAPSED)
        } else {
            if (doubleBackToExit) {
                finishAffinity()
            } else {
                Toast.makeText(this, "종료하시려면 뒤로가기를 한번 더 눌러주세요.", Toast.LENGTH_SHORT).show()
                doubleBackToExit = true
                runDelayed(1500L) {
                    doubleBackToExit = false
                }
            }
        }
    }

    private fun runDelayed(millis: Long, function: () -> Unit) {
        Handler(Looper.getMainLooper()).postDelayed(function, millis)
    }

}
