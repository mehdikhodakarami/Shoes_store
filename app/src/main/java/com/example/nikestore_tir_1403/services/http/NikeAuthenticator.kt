package com.example.nikestore_tir_1403.services.http

import com.example.nikestore_tir_1403.data.TokenContainer
import com.example.nikestore_tir_1403.data.TokenResponse
import com.example.nikestore_tir_1403.data.repo.source.CLIENT_ID
import com.example.nikestore_tir_1403.data.repo.source.CLIENT_SECRET
import com.example.nikestore_tir_1403.data.repo.source.UserDataSource
import com.example.nikestore_tir_1403.data.repo.source.UserLocalDataSource
import com.google.gson.JsonObject
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.java.KoinJavaComponent.inject
import retrofit2.Retrofit

class NikeAuthenticator : Authenticator,KoinComponent {
    val apiservice : Apiservice by inject()
    val userLocalDataSource : UserDataSource by inject()
    override fun authenticate(route: Route?, response: Response): Request? {
        if (TokenContainer.token != null && TokenContainer.refreshToken!= null && !response.request().url().pathSegments().last().equals("token",false) ){
            try {
val token  = refreshToken()
                if (token.isEmpty()){
                    return null
                }
                return response.request().newBuilder().header("Authorization","Bearer ${token}").build()
            }catch (exception:Exception){

            }
        }
        return null
    }
    fun refreshToken():String{
        val response : retrofit2.Response<TokenResponse> = apiservice.refreshToken(JsonObject().apply {
            addProperty("grant_type","refresh_token")
            addProperty("refresh_token",TokenContainer.refreshToken)
            addProperty("client_id", CLIENT_ID)
            addProperty("client_secret", CLIENT_SECRET)



        }).execute() //TODO
        response.body()?.let { TokenContainer.update(it.access_token,it.refresh_token)
            userLocalDataSource.saveToken(it.access_token,it.refresh_token)
            return it.access_token
        }
        return ""
    }
}