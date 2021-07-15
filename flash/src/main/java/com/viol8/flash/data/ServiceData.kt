package com.viol8.flash.data

import com.google.gson.Gson
import com.viol8.flash.basemodules.BaseRepository
import com.viol8.flash.config.WebEndpoints
import com.viol8.flash.model.MessageResponse
import com.viol8.flash.retrofit.RestClient
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

object ServiceData : BaseRepository() {

    private var cli: String? = null
    private var uniqueId: String? = null
    private var mobile: String? = null
    private var apiKey: String? = null
    private var userId: String? = null

    fun callAlert(countryCode: String?, mobile: String?, apiKey: String?, userId: String?) {
        this.mobile = mobile
        this.apiKey = apiKey
        this.userId = userId
        val map = HashMap<String, Any?>()
        map["mobile"] = mobile
        map["cc"] = countryCode
        map["type"] = "missedcall"
        map["customer_info"] = ""
        map["apiKey"] = apiKey
        map["userId"] = userId

        val request = RestClient.getApiService().callAlertAsync(map)
        request.loadApi {
            try {
                var response: String? = null
                if (it.body() != null) {
                    response = String(it.body()!!.bytes())
                } else {
                    response = String(it.errorBody()!!.bytes())
                }
                val data =
                    Gson().fromJson(response, MessageResponse::class.java)
                cli = data.cli
                uniqueId = data.unique_id
                cli?.let { WebEndpoints.FLASH_NUMBER = it }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun verifyCall(onResponse: (Boolean) -> Unit) {
        val formatter = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH)
        val today: String = formatter.format(Date())

        val map = HashMap<String, Any?>()
        map["mobile"] = mobile
        map["unique_id"] = uniqueId
        map["cli"] = cli
        map["call_time"] = today
        map["apiKey"] = apiKey
        map["userId"] = userId

        val request = RestClient.getApiService().verifyCallAsync(map)
        request.loadApi {
            try {
                var response: String? = null
                if (it.body() != null) {
                    response = String(it.body()!!.bytes())
                } else {
                    response = String(it.errorBody()!!.bytes())
                }
                val data =
                    Gson().fromJson(response, MessageResponse::class.java)
                if (data?.response == "1") {
                    onResponse.invoke(true)
                } else {
                    onResponse.invoke(false)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                onResponse.invoke(false)
            }
        }
    }
}