package com.example.nikestore_tir_1403.data.repo.source

import com.example.nikestore_tir_1403.data.MessageResponse
import com.example.nikestore_tir_1403.data.TokenResponse
import io.reactivex.Completable
import io.reactivex.Single

interface UserDataSource{
  fun login(userName:String,password:String): Single<TokenResponse>
  fun signUp(userName: String,password: String): Single<MessageResponse>
  fun loadToken()
  fun saveToken(token:String,refreshToken:String)
  fun saveUserName(userName:String)
  fun getUserName():String
  fun signOut()
  }