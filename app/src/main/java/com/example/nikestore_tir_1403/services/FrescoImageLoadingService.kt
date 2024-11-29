package com.example.nikestore_tir_1403.services

import com.example.nikestore_tir_1403.view.NikeImageView
import com.facebook.drawee.view.SimpleDraweeView

class FrescoImageLoadingService : ImageLoadingService {
    override fun load(imageView: NikeImageView, url: String) {
        if (imageView is SimpleDraweeView) {
            imageView.setImageURI(url)

        } else throw IllegalAccessException("image view must be instanse of simpleDraweeView")
    }

}