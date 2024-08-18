package com.example.nikestore_tir_1403.data.repo.source

import com.example.nikestore_tir_1403.data.Comment
import io.reactivex.Single

interface CommentDataSource {
    fun getComments(productId : Int): Single<List<Comment>>
    fun addComment(): Single<Comment>
}