package com.example.nikestore_tir_1403.data.repo

import io.reactivex.Completable

interface UserRepo {
    fun login(userName:String,password:String):Completable
    fun signUp(userName: String,password: String):Completable
    fun loadToken()
    fun getUserName():String
    fun signOut()
}