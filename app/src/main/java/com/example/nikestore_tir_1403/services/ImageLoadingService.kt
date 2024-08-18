package com.example.nikestore_tir_1403.services

import com.example.nikestore_tir_1403.view.NikeImageView

interface ImageLoadingService  {
   fun load(imageView: NikeImageView,url:String)
}