package com.example.nikestore_tir_1403.feature.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.nikestore_tir_1403.R
import com.example.nikestore_tir_1403.common.EXTRA_KEY_DATA
import com.example.nikestore_tir_1403.data.Banner
import com.example.nikestore_tir_1403.services.ImageLoadingService
import com.example.nikestore_tir_1403.view.NikeImageView
import org.koin.android.ext.android.inject
import java.util.zip.Inflater

class BannerFragment : Fragment() {
    val imageLoadingService:ImageLoadingService by inject()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val imageView = inflater.inflate(R.layout.fragment_banner,container,false) as NikeImageView
        val banner = requireArguments().getParcelable<Banner>(EXTRA_KEY_DATA)?:throw IllegalAccessException("banner cannot be null")
        imageLoadingService.load(imageView,banner.image)
        return imageView
    }
    companion object{
        fun newInstance(banner: Banner): BannerFragment {
            val bannerFragment = BannerFragment()
            val bundle = Bundle()
            bundle.putParcelable(EXTRA_KEY_DATA,banner)
            bannerFragment.arguments = bundle
return bannerFragment
        }

    }
}