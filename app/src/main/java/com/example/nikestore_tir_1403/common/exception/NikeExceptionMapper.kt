package com.example.nikestore_tir_1403.common.exception

import androidx.annotation.RequiresApi
import com.example.nikestore_tir_1403.R
import org.json.JSONObject

class NikeExceptionMapper {
companion object{
    @RequiresApi(34)
    fun map(throwable: Throwable) : NikeException {
        if (throwable is retrofit2.HttpException) {
            try {
                val errorJsonObject = JSONObject(throwable.response()?.errorBody()!!.string())
                val errorMessage = errorJsonObject.getString("message")
                return NikeException(
                    if (throwable.code() == 401) NikeException.Type.AUTH else NikeException.Type.SIMPLE,
                    serverMessage = errorMessage
                )
            } catch (exception: Exception) {

            }


        }
        return NikeException(NikeException.Type.SIMPLE, R.string.unknown_error)
    }

}

}