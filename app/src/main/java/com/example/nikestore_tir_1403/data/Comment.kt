package com.example.nikestore_tir_1403.data

data class Comment(
    val author: Author,
    val content: String,
    val date: String,
    val id: Int,
    val title: String
)