package com.example.nikestore_tir_1403.data.repo

import com.example.nikestore_tir_1403.data.Comment
import com.example.nikestore_tir_1403.data.repo.source.CommentDataSource
import io.reactivex.Single

class CommentRepositoryImpl(val commentDataSource: CommentDataSource) : CommentRepo {
    override fun getComments(productId : Int): Single<List<Comment>> {
return commentDataSource.getComments(productId)    }

    override fun addComment(): Single<Comment> {
        TODO("Not yet implemented")
    }
}