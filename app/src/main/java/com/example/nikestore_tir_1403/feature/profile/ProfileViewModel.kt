package com.example.nikestore_tir_1403.feature.profile

import com.example.nikestore_tir_1403.common.NikeViewModel
import com.example.nikestore_tir_1403.data.TokenContainer
import com.example.nikestore_tir_1403.data.repo.UserRepo

class ProfileViewModel(private val userRepo: UserRepo) : NikeViewModel() {
val userName :String
get() {return userRepo.getUserName()}
val isSignedIn :Boolean
get() {if(TokenContainer.token != null ){
    return true
}else
    return false
}
    fun signOut()=userRepo.signOut()
}