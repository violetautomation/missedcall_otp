package com.viol8.stgvirtual.utils

import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import com.viol8.stgvirtual.model.LoginResponse
import com.viol8.stgvirtual.application.ApplicationApp
import com.viol8.stgvirtual.config.Constants


object UserUtils {

    var topActivity: AppCompatActivity? = null
        get() = field
        set(value) {
            field = value
        }

    fun saveToken(context: Context, token: String) {
        SharedPrefHelper.save(context, Constants.TOKEN, token)
    }

    fun getToken(context: Context): String? {
        return SharedPrefHelper.getString(context, Constants.TOKEN, "")
    }

    fun saveUserData(context: Context, userModel: LoginResponse?) {
        if (userModel == null) {
            SharedPrefHelper.save(context, Constants.USER_DATA, "")
        }
        if (userModel != null) {
            val userModelJson = com.google.gson.Gson().toJson(userModel)
            SharedPrefHelper.save(context, Constants.USER_DATA, userModelJson)
        }
    }

    fun getUserData(context: Context): LoginResponse? {
        val userJson = SharedPrefHelper.getString(context, Constants.USER_DATA, "")
        //convert json to model
        val userModel = com.google.gson.Gson().fromJson(userJson, LoginResponse::class.java)
        return userModel
    }

    fun isOnline(): Boolean {
        val connectivityManager =
            ApplicationApp.appInstance.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = connectivityManager.activeNetworkInfo
        return netInfo != null && netInfo.isConnected
    }
}

