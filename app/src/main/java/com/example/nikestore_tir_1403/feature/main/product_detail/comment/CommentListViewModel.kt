package com.example.nikestore_tir_1403.feature.main.product_detail.comment

import androidx.lifecycle.MutableLiveData
import com.example.nikestore_tir_1403.common.observer.NikeSingleObserver
import com.example.nikestore_tir_1403.common.NikeViewModel
import com.example.nikestore_tir_1403.data.Comment
import com.example.nikestore_tir_1403.data.repo.CommentRepo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CommentListViewModel(val commentRepo: CommentRepo,val productId : Int) : NikeViewModel() {
val commentsLiveData = MutableLiveData<List<Comment>>()
    val progressBarLiveData = MutableLiveData<Boolean>()
    init {
        progressBarLiveData.postValue(true)
        commentRepo.getComments(productId).observeOn(AndroidSchedulers.mainThread()).doFinally { progressBarLiveData.postValue(false) }.subscribeOn(Schedulers.io())

            .subscribe(object : NikeSingleObserver<List<Comment>>(compositeDisposable) {
                override fun onSuccess(t: List<Comment>) {
commentsLiveData.value = t                }

            })
    }
}