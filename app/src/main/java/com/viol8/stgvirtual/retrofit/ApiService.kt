package com.viol8.stgvirtual.retrofit

import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


/**
 * Api service containing all the server calls.
 */
interface ApiService {

    @POST("login")
    fun login(@Body bodyParams: HashMap<String, Any?>): Deferred<Response<ResponseBody>>

    @GET("app/leads/{userId}")
    fun getLead(@Path("userId") userId: String?): Deferred<Response<ResponseBody>>

    @POST("app/lead")
    fun callAgent(@Body bodyParams: HashMap<String, Any?>): Deferred<Response<ResponseBody>>

    @POST("app/sendsms")
    fun sendSms(@Body bodyParams: HashMap<String, Any?>): Deferred<Response<ResponseBody>>

    @POST("app/sendemail")
    fun sendEmail(@Body bodyParams: HashMap<String, Any?>): Deferred<Response<ResponseBody>>

    @POST("app/calldispose")
    fun submitRemark(@Body bodyParams: HashMap<String, Any?>): Deferred<Response<ResponseBody>>

    @POST("app/agent/report")
    fun insertCallDetail(@Body bodyParams: HashMap<String, Any?>): Deferred<Response<ResponseBody>>

    @GET("app/agent/report/{userId}")
    fun getReport(@Path("userId") userId: String?): Deferred<Response<ResponseBody>>
}
