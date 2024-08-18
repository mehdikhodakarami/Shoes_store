package com.example.nikestore_tir_1403.feature.main.product_detail.comment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nikestore_tir_1403.R
import com.example.nikestore_tir_1403.data.Comment

class CommentAdapter(var showAll : Boolean = false) : RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {
var comments =  ArrayList<Comment>()
    get() {
return field   }
    set(value) {field = value
notifyDataSetChanged()}

     class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val titleCommentTv = itemView.findViewById<TextView>(R.id.commentTitleTv)
         val authorCommentTv = itemView.findViewById<TextView>(R.id.commentAuthorTv)
         val dateCommentTv = itemView.findViewById<TextView>(R.id.commentDateTv)
         val contentCommentTv = itemView.findViewById<TextView>(R.id.commentContentTv)

         fun bindComment(comment: Comment){
titleCommentTv.setText(comment.title)

             dateCommentTv.setText(comment.date)
             contentCommentTv.setText(comment.content)
authorCommentTv.setText(comment.author.email)         }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
return CommentViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_comment,
parent,false))    }

    override fun getItemCount(): Int {
 if(comments.size>3 && !showAll) return 3 else return comments.size  }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bindComment(comments!![position])
    }

}