package com.example.nikestore_tir_1403.feature.shipping

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.example.nikestore_tir_1403.R
import com.example.nikestore_tir_1403.common.EXTRA_KEY_DATA
import com.example.nikestore_tir_1403.common.EXTRA_KEY_Id
import com.example.nikestore_tir_1403.common.NikeActivity
import com.example.nikestore_tir_1403.common.observer.NikeSingleObserver
import com.example.nikestore_tir_1403.common.openUrlInCustomTab
import com.example.nikestore_tir_1403.data.PurcheseDetail
import com.example.nikestore_tir_1403.data.SubmitOrderResult
import com.example.nikestore_tir_1403.feature.cart.CartItemAdapter
import com.example.nikestore_tir_1403.feature.cheackout.CheckOutActivity
import com.example.nikestore_tir_1403.feature.main.MainActivity
import com.example.nikestore_tir_1403.view.NikeToolbar
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.koin.androidx.viewmodel.ext.android.viewModel

class ShippingActivity : NikeActivity() {
    private val shippingViewModel:ShippingViewModel by viewModel ()
    val compositeDisposable=CompositeDisposable()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shipping)
        val purchaseDetail = intent.getParcelableExtra<PurcheseDetail>(EXTRA_KEY_DATA)?:throw IllegalAccessException(
            "purchase datail is null"
        )
        val purchaseDetailView = findViewById<View>(R.id.purchaseDetailShipping)
        //---views---------
        val firstNameTv = findViewById<TextView>(R.id.firstNameEt)
        val lastNameTv = findViewById<TextView>(R.id.lastNameEt)
        val addressTv = findViewById<TextView>(R.id.addressEt)
        val mobileTv = findViewById<TextView>(R.id.phoneNumberEt)
        val postalCodeTv = findViewById<TextView>(R.id.postalCodeEt)
        val codBtn = findViewById<View>(R.id.codBtn)
        val onlineBtn = findViewById<View>(R.id.onlinePaymentBtn)




        val viewHolder = CartItemAdapter.PurchaseDetailViewHolder(purchaseDetailView)
        viewHolder.bindPurchase(purchaseDetail.totalPrice,purchaseDetail.shippingCost,purchaseDetail.payablePrice)
        val onClickBtnListener = View.OnClickListener {
            shippingViewModel.submitOrder(firstNameTv.text.toString(),
                lastNameTv.text.toString(),
                postalCodeTv.text.toString(),
                mobileTv.text.toString(),addressTv.text.toString(),
                if(it.id == R.id.onlinePaymentBtn) PAYMENT_METHOD_ONLINE else PAYMENT_METHOD_COD).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(
                    object : NikeSingleObserver<SubmitOrderResult>(compositeDisposable){
                        override fun onSuccess(t: SubmitOrderResult) {
                            if(!t.bank_gateway_url.isEmpty()){
                                openUrlInCustomTab(this@ShippingActivity,t.bank_gateway_url)
                            }else
                                startActivity(Intent(this@ShippingActivity,CheckOutActivity::class.java).putExtra(
                                    EXTRA_KEY_Id,t.order_id))
                            finish()
                        }

                    }
                )
        }
        codBtn.setOnClickListener(onClickBtnListener)
        onlineBtn.setOnClickListener(onClickBtnListener)
        findViewById<NikeToolbar>(R.id.shippingToolbar  ).onBackClickListener = View.OnClickListener { finish() }
    }

}