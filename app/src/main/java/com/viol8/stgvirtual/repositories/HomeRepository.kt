package com.viol8.stgvirtual.repositories

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.viol8.stgvirtual.basemodules.BaseRepository
import com.viol8.stgvirtual.model.LeadResponse
import com.viol8.stgvirtual.model.MessageResponse
import com.viol8.stgvirtual.model.ReportResponse
import com.viol8.stgvirtual.retrofit.RestClient
import com.viol8.stgvirtual.utils.UserUtils


class HomeRepository : BaseRepository() {

    fun getLeadApiWebCall(
        userId: String?,
        isLoaderRequired: Boolean,
        loader: (Boolean) -> Unit,
        onSuccess: (LeadResponse) -> Unit,
        onError: (String) -> Unit
    ) {
        if (UserUtils.isOnline()) {
            loader(isLoaderRequired)
            val request = RestClient.getApiService().getLead(userId)
            request.loadApi {
                try {
                    var response: String? = null
                    if (it.body() != null) {
                        response = String(it.body()!!.bytes())
                    } else {
                        response = String(it.errorBody()!!.bytes())
                    }
                    val data = Gson().fromJson<LeadResponse>(response, LeadResponse::class.java)
                    onSuccess(data)
                } catch (e: Exception) {
                    Log.e(HomeRepository::class.java.name, e.stackTrace.toString())
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

    fun callAgentApiWebCall(
        map: HashMap<String, Any?>,
        isLoaderRequired: Boolean,
        loader: (Boolean) -> Unit,
        onSuccess: (MessageResponse) -> Unit,
        onError: (String) -> Unit
    ) {
        if (UserUtils.isOnline()) {
            loader(isLoaderRequired)
            val request = RestClient.getApiService().callAgent(map)
            request.loadApi {
                try {
                    var response: String? = null
                    if (it.body() != null) {
                        response = String(it.body()!!.bytes())
                    } else {
                        response = String(it.errorBody()!!.bytes())
                    }
                    val data =
                        Gson().fromJson<MessageResponse>(response, MessageResponse::class.java)
                    onSuccess(data)
                } catch (e: Exception) {
                    Log.e(HomeRepository::class.java.name, e.stackTrace.toString())
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

    fun sendSmsApiWebCall(
        map: HashMap<String, Any?>,
        isLoaderRequired: Boolean,
        loader: (Boolean) -> Unit,
        onSuccess: (MessageResponse) -> Unit,
        onError: (String) -> Unit
    ) {
        if (UserUtils.isOnline()) {
            loader(isLoaderRequired)
            val request = RestClient.getApiService().sendSms(map)
            request.loadApi {
                try {
                    var response: String? = null
                    if (it.body() != null) {
                        response = String(it.body()!!.bytes())
                    } else {
                        response = String(it.errorBody()!!.bytes())
                    }
                    val data =
                        Gson().fromJson<MessageResponse>(response, MessageResponse::class.java)
                    onSuccess(data)
                } catch (e: Exception) {
                    Log.e(HomeRepository::class.java.name, e.stackTrace.toString())
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

    fun sendEmailApiWebCall(
        map: HashMap<String, Any?>,
        isLoaderRequired: Boolean,
        loader: (Boolean) -> Unit,
        onSuccess: (MessageResponse) -> Unit,
        onError: (String) -> Unit
    ) {
        if (UserUtils.isOnline()) {
            loader(isLoaderRequired)
            val request = RestClient.getApiService().sendEmail(map)
            request.loadApi {
                try {
                    var response: String? = null
                    if (it.body() != null) {
                        response = String(it.body()!!.bytes())
                    } else {
                        response = String(it.errorBody()!!.bytes())
                    }
                    val data =
                        Gson().fromJson<MessageResponse>(response, MessageResponse::class.java)
                    onSuccess(data)
                } catch (e: Exception) {
                    Log.e(HomeRepository::class.java.name, e.stackTrace.toString())
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

    fun submitRemarkApiWebCall(
        map: HashMap<String, Any?>,
        isLoaderRequired: Boolean,
        loader: (Boolean) -> Unit,
        onSuccess: (MessageResponse) -> Unit,
        onError: (String) -> Unit
    ) {
        if (UserUtils.isOnline()) {
            loader(isLoaderRequired)
            val request = RestClient.getApiService().submitRemark(map)
            request.loadApi {
                try {
                    var response: String? = null
                    if (it.body() != null) {
                        response = String(it.body()!!.bytes())
                    } else {
                        response = String(it.errorBody()!!.bytes())
                    }
                    val data =
                        Gson().fromJson<MessageResponse>(response, MessageResponse::class.java)
                    onSuccess(data)
                } catch (e: Exception) {
                    Log.e(HomeRepository::class.java.name, e.stackTrace.toString())
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

    fun insertCallDetailApiWebCall(
        map: HashMap<String, Any?>,
        isLoaderRequired: Boolean,
        loader: (Boolean) -> Unit,
        onSuccess: (MessageResponse) -> Unit,
        onError: (String) -> Unit
    ) {
        if (UserUtils.isOnline()) {
            loader(isLoaderRequired)
            val request = RestClient.getApiService().insertCallDetail(map)
            request.loadApi {
                try {
                    var response: String? = null
                    if (it.body() != null) {
                        response = String(it.body()!!.bytes())
                    } else {
                        response = String(it.errorBody()!!.bytes())
                    }
                    val data =
                        Gson().fromJson<MessageResponse>(response, MessageResponse::class.java)
                    onSuccess(data)
                } catch (e: Exception) {
                    Log.e(HomeRepository::class.java.name, e.stackTrace.toString())
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


    fun getReportApiWebCall(
        userId: String?,
        isLoaderRequired: Boolean,
        loader: (Boolean) -> Unit,
        onSuccess: (ArrayList<ReportResponse>) -> Unit,
        onError: (String) -> Unit
    ) {
        if (UserUtils.isOnline()) {
            loader(isLoaderRequired)
            val request = RestClient.getApiService().getReport(userId)
            request.loadApi {
                try {
                    var response: String? = null
                    if (it.body() != null) {
                        response = String(it.body()!!.bytes())
                    } else {
                        response = String(it.errorBody()!!.bytes())
                    }
                    val data = Gson().fromJson<ArrayList<ReportResponse>>(
                        response,
                        object : TypeToken<ArrayList<ReportResponse>>() {}.type
                    )
                    onSuccess(data)
                } catch (e: Exception) {
                    Log.e(HomeRepository::class.java.name, e.stackTrace.toString())
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