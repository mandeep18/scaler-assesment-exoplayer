package com.example.scaler.network.api

import retrofit2.Response
import com.example.scaler.util.Result

open class BaseRepository {

    suspend fun <T : Any> doSafeAPIRequest(call: suspend () -> Response<T>): T? {
        val result : Result<T> = returnSafeAPIResponse(call)
        var data : T? = null

        when(result) {
            is Result.Success ->
                data = result.data
            is Result.Error -> {
                throw Exception(result.error ?: "")
            }
        }
        return data
    }


    private suspend fun <T: Any> returnSafeAPIResponse(call: suspend ()-> Response<T>) : Result<T> {
        try{
            val response = call.invoke()
            if(response.isSuccessful) {
                return Result.Success(response.body()!!)
            }
            return Result.Error(error = response.errorBody()?.string().toString())
        }catch (e:Exception){
            return Result.Error("Something went wrong!")
        }
    }

}
