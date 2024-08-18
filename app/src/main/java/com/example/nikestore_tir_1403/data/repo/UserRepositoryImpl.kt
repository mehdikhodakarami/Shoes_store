package com.example.nikestore_tir_1403.data.repo

import com.example.nikestore_tir_1403.data.TokenContainer
import com.example.nikestore_tir_1403.data.TokenResponse
import com.example.nikestore_tir_1403.data.repo.source.UserDataSource
import io.reactivex.Completable

class UserRepositoryImpl(val userRemoteDataSource: UserDataSource,
                         val userLocalDataSource: UserDataSource):UserRepo {
    override fun login(userName: String, password: String): Completable {
      return  userRemoteDataSource.login(userName,password).doOnSuccess {
        onSuccessLogin(userName,it)

      }.ignoreElement()
    }

    override fun signUp(userName: String, password: String): Completable {
        return userRemoteDataSource.signUp(userName,password).flatMap {
            userRemoteDataSource.login(userName, password)
        }.doOnSuccess{
            onSuccessLogin(userName,it)
        }.ignoreElement()
    }

    override fun loadToken() {
        userLocalDataSource.loadToken()
    }

    override fun getUserName(): String {
return userLocalDataSource.getUserName()
    }

    override fun signOut() {
        userLocalDataSource.signOut()
        TokenContainer.update(null,null)
    }

    fun onSuccessLogin(userName:String,tokenResponse: TokenResponse){
TokenContainer.update(tokenResponse.access_token,tokenResponse.refresh_token)
        userLocalDataSource.saveToken(tokenResponse.access_token,tokenResponse.refresh_token)
        userLocalDataSource.saveUserName(userName)
    }

}