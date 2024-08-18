package com.example.nikestore_tir_1403.data.repo.source

import com.example.nikestore_tir_1403.data.MessageResponse
import com.example.nikestore_tir_1403.data.TokenResponse
import com.example.nikestore_tir_1403.services.http.ApiServiceJava
import com.example.nikestore_tir_1403.services.http.Apiservice
import com.google.gson.JsonObject
import io.reactivex.Single
const val CLIENT_ID=2
const val CLIENT_SECRET="kyj1c9sVcksqGU4scMX7nLDalkjp2WoqQEf8PKAC"
class UserRemoteDataSource(val apiservice: Apiservice):UserDataSource{
    override fun login(userName: String, password: String): Single<TokenResponse> {
return apiservice.login(JsonObject().apply {
    addProperty("username",userName)
    addProperty("password",password)
    addProperty("grant_type","password")
    addProperty("client_id", CLIENT_ID)
    addProperty("client_secret", CLIENT_SECRET)



})   }

    override fun signUp(userName: String, password: String): Single<MessageResponse> {
return apiservice.signUp(JsonObject().apply {
    addProperty("email",userName)
    addProperty("password",password)
})
    }

    override fun loadToken() {
        TODO("Not yet implemented")
    }

    override fun saveToken(token: String, refreshToken: String) {
        TODO("Not yet implemented")
    }

    override fun saveUserName(userName: String) {
        TODO("Not yet implemented")
    }

    override fun getUserName(): String {
        TODO("Not yet implemented")
    }

    override fun signOut() {
        TODO("Not yet implemented")
    }


}