package com.example.nikestore_tir_1403.feature.cheackout

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.example.nikestore_tir_1403.R
import com.example.nikestore_tir_1403.common.EXTRA_KEY_Id
import com.example.nikestore_tir_1403.common.NikeActivity
import com.example.nikestore_tir_1403.common.formatPrice
import com.example.nikestore_tir_1403.view.NikeToolbar
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class CheckOutActivity : NikeActivity() {
    val checkoutViewModel : CheckoutViewModel by viewModel{
        var uri:Uri? = intent.data
        if(uri != null){
            parametersOf(uri.getQueryParameter("order_id")!!.toInt())
        }else
            parametersOf(intent.extras?.getInt(EXTRA_KEY_Id))
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)
        val returnHomeBtn = findViewById<View>(R.id.returnHomeBtn)
        val orderHistoryBtn = findViewById<View>(R.id.orderHistoryBtn)
        val payablePriceTv = findViewById<TextView >(R.id.priceValueTv)
        val orderStatus  = findViewById<TextView>(R.id.orderStatusTv)
        val purchaseStatus = findViewById<TextView>(R.id.purchaseStatusTv)
        checkoutViewModel.checkoutLiveData.observe(this){
            payablePriceTv.text = formatPrice(it.payable_price)
            orderStatus.text = it.payment_status
            purchaseStatus.text = if(it.purchase_success) "خرید با موفقیت انجام شد" else "خرید ناموفق بود"
        }
        findViewById<NikeToolbar>(R.id.checkOut_toolbar).onBackClickListener = View.OnClickListener { finish() }

    }
}