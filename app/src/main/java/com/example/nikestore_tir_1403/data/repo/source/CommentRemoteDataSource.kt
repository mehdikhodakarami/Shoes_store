package com.example.nikestore_tir_1403.data.repo.source

import com.example.nikestore_tir_1403.data.Comment
import com.example.nikestore_tir_1403.services.http.Apiservice
import io.reactivex.Single

class CommentRemoteDataSource(val apiservice: Apiservice) : CommentDataSource {
    override fun getComments(productId : Int): Single<List<Comment>> {
return apiservice.getComments(productId)   }

    override fun addComment(): Single<Comment> {
        TODO("Not yet implemented")
    }
}