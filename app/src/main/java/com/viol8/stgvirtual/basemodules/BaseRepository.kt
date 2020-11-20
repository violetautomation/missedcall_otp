package com.viol8.stgvirtual.basemodules

import androidx.lifecycle.LifecycleObserver
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Response

abstract class BaseRepository : LifecycleObserver {

    val EXCEPTION_CODE = -1
    val INTERNET_ERROR_CODE = -2

    fun Deferred<Response<ResponseBody>>.loadApi(block: (Response<ResponseBody>) -> Unit): Job {
        return GlobalScope.launch {
            try {
                block(this@loadApi.await())
            } catch (e: Exception) {
                block(
                    Response.error(
                        400,
                        ResponseBody.create(null, e.printStackTrace().toString())
                    )
                )
            }
        }
    }
}
