package com.viol8.flash.retrofit

import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


/**
 * Api service containing all the server calls.
 */
interface ApiService {
    @POST("otp/generate")
    fun callAlert(@Body bodyParams: HashMap<String, Any?>): Deferred<Response<ResponseBody>>

    @POST("otp/verify")
    fun verifyCall(@Body bodyParams: HashMap<String, Any?>): Deferred<Response<ResponseBody>>
}
