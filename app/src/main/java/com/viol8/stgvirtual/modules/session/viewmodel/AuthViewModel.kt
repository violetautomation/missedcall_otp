package com.viol8.stgvirtual.modules.session.viewmodel

import androidx.lifecycle.MutableLiveData
import com.viol8.stgvirtual.model.LoginResponse
import com.viol8.stgvirtual.basemodules.BaseViewModel
import com.viol8.stgvirtual.repositories.AuthRepository


class AuthViewModel(val repo: AuthRepository) : BaseViewModel() {

    val loginLiveData = MutableLiveData<Triple<Boolean, LoginResponse, String?>>()

    fun validateLoginCredentials(userName: String, password: String) {
        if (userName.trim().isEmpty()) {
            loginLiveData.postValue(Triple(false, LoginResponse(), "Please enter username"))
        } else if (password.trim().isEmpty()) {
            loginLiveData.postValue(Triple(false, LoginResponse(), "Please enter password"))
        } else {
            val map = HashMap<String, Any?>()
            map["username"] = userName
            map["password"] = password
            tokensAuthApiWebCall(map)
        }
    }


    fun tokensAuthApiWebCall(map: HashMap<String, Any?>) {
        repo.loginApiWebCall(map, true, {
            mProgressBar.postValue(it)
        }, {
            loginLiveData.postValue(Triple(true, it, null))
        }, {
            loginLiveData.postValue(Triple(false, LoginResponse(), it))
        })
    }
}