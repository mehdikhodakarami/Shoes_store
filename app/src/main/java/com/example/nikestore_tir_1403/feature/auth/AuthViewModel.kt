package com.example.nikestore_tir_1403.feature.auth

import com.example.nikestore_tir_1403.common.NikeViewModel
import com.example.nikestore_tir_1403.data.repo.UserRepo
import io.reactivex.Completable

class AuthViewModel(private val userRepo: UserRepo):NikeViewModel() {
    fun login(email:String,password:String):Completable{
        progressLiveData.postValue(true)
        return userRepo.login(email,password).doFinally{
            progressLiveData.postValue(false)
        }
    }

    fun signUp(email:String,password:String):Completable{
        progressLiveData.postValue(true)
        return userRepo.signUp(email,password).doFinally{
            progressLiveData.postValue(false)
        }
    }
}