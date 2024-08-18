package com.example.nikestore_tir_1403.feature.main

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.nikestore_tir_1403.common.NikeActivity
import com.example.nikestore_tir_1403.R
import com.example.nikestore_tir_1403.data.CartItemCount
import com.example.nikestore_tir_1403.feature.home.HomeViewModel
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.color.MaterialColors
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : NikeActivity() {
    private val homeViewModel : HomeViewModel by viewModel<HomeViewModel>()

    private lateinit var navController: NavController
    lateinit var bottomNavigationMain: BottomNavigationView
    private val mainViewModel : MainViewModel by viewModel()
//    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {

     super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNavigationMain = findViewById(R.id.main_bottom_navigation)
        EventBus.getDefault().register(this)


        var navHostFragment = supportFragmentManager.findFragmentById(
            R.id.fragment_main_container
        ) as NavHostFragment
        navController = navHostFragment.navController

        // Setup the bottom navigation view with navController
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.main_bottom_navigation)
        bottomNavigationView.setupWithNavController(navController)

//        // Setup the ActionBar with navController and 3 top level destinations
//        appBarConfiguration = AppBarConfiguration(
//            setOf(R.id.home, R.id.cart,  R.id.profile)
//        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
    }


//    override fun onSupportNavigateUp(): Boolean {
//        return navController.navigateUp(appBarConfiguration)
//
//    }
@Subscribe( threadMode = ThreadMode.MAIN)
fun onCartItemCountChangeEvent(cartItemCount: CartItemCount){
    var badge = bottomNavigationMain.getOrCreateBadge(R.id.cart)
    badge.badgeGravity = BadgeDrawable.TOP_START
    badge.backgroundColor = MaterialColors.getColor(bottomNavigationMain, org.koin.android.R.attr.colorPrimary)
    badge.number = cartItemCount.count
    badge.isVisible = cartItemCount.count>0
}

    override fun onResume() {
        super.onResume()
        mainViewModel.getCartItemCounts()

    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)

        super.onDestroy()
    }
}