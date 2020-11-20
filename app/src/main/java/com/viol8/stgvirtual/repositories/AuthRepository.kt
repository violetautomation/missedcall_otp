package com.viol8.stgvirtual.repositories

import android.util.Log
import com.google.gson.Gson
import com.viol8.stgvirtual.model.LoginResponse
import com.viol8.stgvirtual.basemodules.BaseRepository
import com.viol8.stgvirtual.retrofit.RestClient
import com.viol8.stgvirtual.utils.UserUtils


class AuthRepository : BaseRepository() {

    fun loginApiWebCall(
        map: HashMap<String, Any?>,
        isLoaderRequired: Boolean,
        loader: (Boolean) -> Unit,
        onSuccess: (LoginResponse) -> Unit,
        onError: (String) -> Unit
    ) {
        if (UserUtils.isOnline()) {
            loader(isLoaderRequired)
            val request = RestClient.getApiService().login(map)
            request.loadApi {
                try {
                    var response: String? = null
                    if (it.body() != null) {
                        response = String(it.body()!!.bytes())
                    } else {
                        response = String(it.errorBody()!!.bytes())
                    }
                    val data = Gson().fromJson<LoginResponse>(response, LoginResponse::class.java)
                    onSuccess(data)
                } catch (e: Exception) {
                    Log.e(AuthRepository::class.java.name, e.stackTrace.toString())
                    onError("Something went wrong")
                } finally {
                    loader(false)
                }
            }
        } else {
            loader(false)
            onError("Please check your internet connection.")
        }
    }
}