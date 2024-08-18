package com.example.nikestore_tir_1403.data.repo

import com.example.nikestore_tir_1403.data.Comment
import io.reactivex.Single

interface CommentRepo {
    fun getComments(productId : Int):Single<List<Comment>>
    fun addComment():Single<Comment>
}