package com.example.nikestore_tir_1403.feature.main.product_detail

import android.content.Intent
import android.graphics.Paint
import android.media.Image
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nikestore_tir_1403.R
import com.example.nikestore_tir_1403.common.EXTRA_KEY_Id
import com.example.nikestore_tir_1403.common.NikeActivity
import com.example.nikestore_tir_1403.common.observer.NikeCompletableObserver
import com.example.nikestore_tir_1403.common.formatPrice
import com.example.nikestore_tir_1403.data.Comment
import com.example.nikestore_tir_1403.data.TokenContainer
import com.example.nikestore_tir_1403.feature.auth.AuthActivity
import com.example.nikestore_tir_1403.feature.favorites.FavoriteProductsAdapter
import com.example.nikestore_tir_1403.feature.main.product_detail.comment.CommentAdapter
import com.example.nikestore_tir_1403.feature.main.product_detail.comment.CommentListActivity
import com.example.nikestore_tir_1403.services.ImageLoadingService
import com.example.nikestore_tir_1403.view.NikeImageView
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks
import com.github.ksoichiro.android.observablescrollview.ScrollState
import com.google.android.material.snackbar.Snackbar
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.greenrobot.eventbus.EventBus
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import timber.log.Timber
import kotlin.collections.ArrayList

class ProductDetailActivity : NikeActivity(){
    val productDetailViewModel : ProductDetailViewModel by viewModel { parametersOf(intent.extras) }
    val imageLoadingService : ImageLoadingService by inject()
    lateinit var productDetailIv : NikeImageView
    lateinit var addToCArtBtn : Button
    lateinit var favoriteBtnIv :ImageView
    val compositeDisposable = CompositeDisposable()
    var commentAdapter = CommentAdapter()
    lateinit var backBtn :ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)
        addToCArtBtn = findViewById(R.id.addToCartBtn)
EventBus.getDefault().register(this)
        productDetailViewModel.progressBarLiveData.observe(this){
    setProgressIndicator(it)
}
        backBtn  = findViewById(R.id.back_detail_btn)
        backBtn.setOnClickListener { finish() }
        favoriteBtnIv = findViewById(R.id.favorite_detail_btn)
         productDetailIv = this.findViewById<NikeImageView>(R.id.product_detail_iv)
        productDetailViewModel.productLiveData.observe(this){
            favoriteBtnIv.setImageResource(if (it.isFavorite) R.drawable.ic_favorite_fill else R.drawable.ic_favorites)

            this.findViewById<TextView>(R.id.Product_detail_title_tv).setText(it.title)

            findViewById<TextView   >(R.id.toolbar_datail_tv).text = "جزئیات محصول"
            imageLoadingService.load( productDetailIv,it.image)
            this.findViewById<TextView>(R.id.previous_detail_PriceTv).apply {    paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                text = formatPrice(it.previous_price)
            }
            this.findViewById<TextView>(R.id.current_detail_PriceTv).text = formatPrice(it.price)
        }
        productDetailViewModel.commentLiveData.observe(this ){
          commentAdapter.comments = it as ArrayList<Comment>
            if (it.size>3){
                findViewById<Button>(R.id.viewAllComentBtn).visibility = View.VISIBLE
                findViewById<Button>(R.id.viewAllComentBtn).setOnClickListener{
                    startActivity(Intent(this, CommentListActivity::class.java).apply { putExtra(EXTRA_KEY_Id,
                    productDetailViewModel.productLiveData.value!!.id) })
                }
            }
        }
        initViews()


        //Easy coding inflate and create views------------------------------------------------------------------------



    }
    fun initViews(){

        val commentRv = findViewById<RecyclerView>(R.id.commentRv)
        commentRv.layoutManager = LinearLayoutManager(this
            ,RecyclerView.VERTICAL,false)
        commentRv.adapter = commentAdapter

        productDetailIv.post { val productHeightIv = productDetailIv.measuredHeight
            val productDetailIvTest = productDetailIv

            val toolbarView = findViewById<CardView>(R.id.toolbar_view  )
            toolbarView.alpha = 0f
            val observableScrollView = findViewById<ObservableScrollView>(R.id.observableScrollView  )
            observableScrollView.setScrollViewCallbacks(object: ObservableScrollViewCallbacks{

                override fun onScrollChanged(scrollY: Int, firstScroll: Boolean, dragging: Boolean) {
                    toolbarView.alpha = scrollY.toFloat() / productHeightIv.toFloat()
                    productDetailIvTest.translationY=scrollY.toFloat()/2.45.toFloat()

                }

                override fun onDownMotionEvent() {

                }

                override fun onUpOrCancelMotionEvent(scrollState: ScrollState?) {

                }

            }) }
        addToCArtBtn.setOnClickListener {
            Timber.i("the token is ${TokenContainer.token?.substring(0,10)} ")
            productDetailViewModel.addToCartBtn().subscribeOn(Schedulers.io()).
        observeOn(AndroidSchedulers.mainThread()).subscribe(object : NikeCompletableObserver(compositeDisposable){
            override fun onComplete() {

                showsnackBar(getString(R.string.success_addToCart),Snackbar.LENGTH_SHORT)
            }

        })}

    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }


}