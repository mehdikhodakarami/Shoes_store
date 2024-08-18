package com.example.nikestore_tir_1403.feature.main.product_detail.comment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nikestore_tir_1403.R
import com.example.nikestore_tir_1403.common.EXTRA_KEY_Id
import com.example.nikestore_tir_1403.common.NikeActivity
import com.example.nikestore_tir_1403.data.Comment
import com.example.nikestore_tir_1403.view.NikeToolbar
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class CommentListActivity : NikeActivity() {
    val commentListViewModel : CommentListViewModel by viewModel { parametersOf(intent.extras!!.getInt(
        EXTRA_KEY_Id))  }
lateinit var commentRv : RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment_list)
        commentRv = findViewById(R.id.allCommentsRv)



commentListViewModel.progressBarLiveData.observe(this  ){
    setProgressIndicator(it)
}
commentListViewModel.commentsLiveData.observe(this){
    var commentAdapter = CommentAdapter(true)
    commentRv.layoutManager = LinearLayoutManager(this,RecyclerView.VERTICAL,false)
    commentRv.adapter = commentAdapter
    commentAdapter.comments = it as ArrayList<Comment>
}
findViewById<NikeToolbar>(R.id.commentListToolbar  ).onBackClickListener = View.OnClickListener { finish() }
    }
}