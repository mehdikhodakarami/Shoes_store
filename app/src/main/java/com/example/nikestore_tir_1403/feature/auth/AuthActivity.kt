package com.example.nikestore_tir_1403.feature.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.transaction
import com.example.nikestore_tir_1403.R
import com.example.nikestore_tir_1403.common.InAuthCounter

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        InAuthCounter.counerStartActivity = 1
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        supportFragmentManager.beginTransaction().apply { replace(R.id.fragment_container,LoginFragment()) }.commit()
    }

    override fun onStop() {
        InAuthCounter.counerStartActivity = 0
        super.onStop()
    }
}