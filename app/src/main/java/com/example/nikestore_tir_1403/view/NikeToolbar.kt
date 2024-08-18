package com.example.nikestore_tir_1403.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.example.nikestore_tir_1403.R

class NikeToolbar(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {
    var onBackClickListener :View.OnClickListener? = null
    set(value) {field = value
    findViewById<ImageView>(R.id.backBtnToolbar).setOnClickListener(onBackClickListener)}
    init {
        inflate(context, R.layout.view_toolbar,this)
        if (attrs != null){
            val a=context.obtainStyledAttributes(attrs,R.styleable.NikeToolbar)
            val title = a.getString(R.styleable.NikeToolbar_nt_title)
            if (title != null && !title.isEmpty()){
                findViewById<TextView>(R.id.toolbartitleTv).setText(title)
            }
            a.recycle()
        }
    }
}