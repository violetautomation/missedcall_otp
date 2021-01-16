package com.viol8.flash.basemodules

import androidx.lifecycle.LifecycleObserver
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Response

abstract class BaseRepository : LifecycleObserver {

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
