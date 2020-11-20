package com.viol8.stgvirtual.model

data class LoginResponse(var message: String? = null) {

    var userid: String? = null
    var token: String? = null
    var role: ArrayList<String>? = null
}