package com.example.nikestore_tir_1403.common.observer

import androidx.annotation.RequiresApi
import com.example.nikestore_tir_1403.common.exception.NikeExceptionMapper
import io.reactivex.CompletableObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.greenrobot.eventbus.EventBus
import org.koin.java.KoinJavaComponent.inject

abstract class NikeCompletableObserver(val compositeDisposable: CompositeDisposable) : CompletableObserver {

    override fun onSubscribe(d: Disposable) {
compositeDisposable.add(d)

    }



    @RequiresApi(34)
    override fun onError(e: Throwable) {
        EventBus.getDefault().post(NikeExceptionMapper.map(e))
    }
}