package com.example.nikestore_tir_1403

import android.app.Application
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import androidx.room.Room
import com.example.nikestore_tir_1403.data.api_with_java.repo.BannerJavaRepo
import com.example.nikestore_tir_1403.data.api_with_java.repo.BannerJavaRepoImpl
import com.example.nikestore_tir_1403.data.api_with_java.repo.ProductJavaRepo
import com.example.nikestore_tir_1403.data.api_with_java.repo.ProductJavaRepoImpl
import com.example.nikestore_tir_1403.data.api_with_java.repo.source.BannerJavaRemoteDataSource
import com.example.nikestore_tir_1403.data.api_with_java.repo.source.ProductJavaLocalDataSource
import com.example.nikestore_tir_1403.data.api_with_java.repo.source.ProductJavaRemoteDataSource
import com.example.nikestore_tir_1403.data.db.AppDataBase
import com.example.nikestore_tir_1403.data.repo.*
import com.example.nikestore_tir_1403.data.repo.order.OrderRemoteDataSource
import com.example.nikestore_tir_1403.data.repo.order.OrderRepo
import com.example.nikestore_tir_1403.data.repo.order.OrderRepositoryImpl
import com.example.nikestore_tir_1403.data.repo.source.*
import com.example.nikestore_tir_1403.feature.auth.AuthViewModel
import com.example.nikestore_tir_1403.feature.cart.CartViewModel
import com.example.nikestore_tir_1403.feature.cheackout.CheckoutViewModel
import com.example.nikestore_tir_1403.feature.favorites.FavoriteProductsViewModel
import com.example.nikestore_tir_1403.feature.home.HomeViewModel
import com.example.nikestore_tir_1403.feature.main.MainViewModel
import com.example.nikestore_tir_1403.feature.main.list.ProductListAdapter
import com.example.nikestore_tir_1403.feature.main.list.ProductListViewModel
import com.example.nikestore_tir_1403.feature.main.product_detail.ProductDetailViewModel
import com.example.nikestore_tir_1403.feature.main.product_detail.comment.CommentListViewModel
import com.example.nikestore_tir_1403.feature.profile.ProfileViewModel
import com.example.nikestore_tir_1403.feature.shipping.ShippingViewModel
import com.example.nikestore_tir_1403.services.FrescoImageLoadingService
import com.example.nikestore_tir_1403.services.ImageLoadingService
import com.example.nikestore_tir_1403.services.http.ApiJavaImpl
import com.example.nikestore_tir_1403.services.http.ApiServiceJava
import com.example.nikestore_tir_1403.services.http.createApiServiceInstance
import com.facebook.drawee.backends.pipeline.Fresco
import org.greenrobot.eventbus.EventBus
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import timber.log.Timber
import java.util.*


class App: Application() {
    override fun onCreate() {
        //fresco
        Fresco.initialize(this)

Timber.plant(Timber.DebugTree())
        super.onCreate();
//        setLocale("fa"); // تغییر زبان به فارسی
//
//        Locale.setDefault(Locale("fa"))
        val myModules = module {
            single { createApiServiceInstance() }
            single { Room.databaseBuilder(this@App,AppDataBase::class.java,"db_app").allowMainThreadQueries().build()}
            factory<ProductRepo> {
                ProductRepositoryImpl(
                    ProductRemoteDataSource(get()),
                    get<AppDataBase>().getProductDao()
                )
            }


            factory<BannerRepo> {BannerRepositoryImpl(BannerRemoteDataSource(get()))  }
factory <CartRepo>{CartRepositoryImpl(CartRemoteDataSource(get()))  }
            single <SharedPreferences>{ this@App.getSharedPreferences("app_settings", MODE_PRIVATE) }
            factory <UserRepo>{UserRepositoryImpl(
                UserRemoteDataSource(get()),
                UserLocalDataSource(get())
            )}
            //Java Api With Call Adapter
single <ApiServiceJava>{ApiJavaImpl.getInsApiJava()}
            factory<ProductJavaRepo> { ProductJavaRepoImpl(ProductJavaRemoteDataSource(get()),
            ProductJavaLocalDataSource()) }
            factory<BannerJavaRepo>  {BannerJavaRepoImpl(BannerJavaRemoteDataSource(get()))}

single<EventBus> { EventBus() }
            single <ImageLoadingService>{ FrescoImageLoadingService() }
            factory{(viewType:Int)-> ProductListAdapter(viewType,get()) }
            viewModel{(productId:Int)-> CommentListViewModel(get(),productId) }
viewModel { (sort :Int)-> ProductListViewModel(get(),sort) }
factory <CommentRepo>{ CommentRepositoryImpl(CommentRemoteDataSource(get())) }
            single<OrderRepo>{OrderRepositoryImpl(OrderRemoteDataSource(get()))}
viewModel{(bundle:Bundle)-> ProductDetailViewModel(bundle,get(),get()) }
            viewModel { AuthViewModel(get()) }
            viewModel { CartViewModel(get()) }
            viewModel{MainViewModel(get())}
        viewModel { HomeViewModel(get(),get(),get(),get()) }
viewModel { ShippingViewModel(get()) }
            viewModel{(orderId:Int)->CheckoutViewModel(get(),orderId)}
            viewModel { ProfileViewModel(get()) }
            viewModel{FavoriteProductsViewModel(get())}
            factory { UserLocalDataSource(get())}}
        startKoin{androidLogger()
            androidContext(this@App)
           modules(myModules)
        }
        val userRepo : UserRepo = get()
        userRepo.loadToken();
    }
    private fun setLocale(lang: String) {
        val locale = Locale(lang)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        resources.updateConfiguration(config, resources.displayMetrics)
    }
}