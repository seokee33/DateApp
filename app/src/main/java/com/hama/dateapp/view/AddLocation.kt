package com.hama.dateapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hama.dateapp.databinding.ActivityAddLocationBinding

class AddLocation : AppCompatActivity() {
    var _binding: ActivityAddLocationBinding? = null
    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAddLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}