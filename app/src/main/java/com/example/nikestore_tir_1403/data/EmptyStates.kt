package com.example.nikestore_tir_1403.data

import androidx.annotation.StringRes

data class EmptyStates(val mustShow:Boolean,
@StringRes val messageResId:Int = 0 ,val mustShowCallToActionBtn : Boolean = false)
