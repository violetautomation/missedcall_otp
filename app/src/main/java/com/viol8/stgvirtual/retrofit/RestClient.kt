package com.viol8.stgvirtual.retrofit


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.viol8.stgvirtual.application.ApplicationApp
import com.viol8.stgvirtual.config.WebEndpoints
import com.viol8.stgvirtual.modules.session.LoginActivity
import com.viol8.stgvirtual.utils.UserUtils
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.IOException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.X509TrustManager

/**
 * Get rest clients for server requests.
 */
object RestClient {

    private var apiService: ApiService? = null

    private val unsafeOkHttpClient: OkHttpClient
        get() {
            try {
                val trustAllCerts = arrayOf<X509TrustManager>(object : X509TrustManager {
                    override fun checkClientTrusted(
                        chain: Array<X509Certificate>,
                        authType: String
                    ) {
                    }

                    override fun checkServerTrusted(
                        chain: Array<X509Certificate>,
                        authType: String
                    ) {
                    }

                    override fun getAcceptedIssuers(): Array<X509Certificate?> {
                        return arrayOfNulls(0)
                    }
                })
                val sslContext = SSLContext.getInstance("SSL")
                sslContext.init(null, trustAllCerts, java.security.SecureRandom())
                val sslSocketFactory = sslContext.socketFactory

                val builder = OkHttpClient.Builder()
                builder.readTimeout(5, TimeUnit.MINUTES)
                builder.writeTimeout(5, TimeUnit.MINUTES)
                builder.connectTimeout(10, TimeUnit.SECONDS)
                builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0])
                builder.hostnameVerifier { _, _ -> true }
                builder.interceptors().add(object : Interceptor {
                    @Throws(IOException::class)
                    override fun intercept(chain: Interceptor.Chain): Response {
                        val request = chain.request()
                        // try the request
                        var response = chain.proceed(request)
//                        var tryCount = 0
//                        while (!response.isSuccessful && tryCount < 3) {
//                            Log.d("intercept", "Request is not successful - $tryCount")
//                            tryCount++
//                            // retry the request
//                            response.close()
//                            response = chain.proceed(request)
//                        }
                        // otherwise just pass the original response on
                        return response
                    }
                })
                builder.addInterceptor {
                    val original = it.request()
                    var request: Request? = null
                    if (!UserUtils.getToken(ApplicationApp.appInstance).isNullOrEmpty()) {
                        request = original.newBuilder()
                            .header("Content-Type", "application/json")
                            .header(
                                "Authorization",
                                "Bearer ${UserUtils.getToken(ApplicationApp.appInstance)}"
                            )
                            .method(original.method, original.body)
                            .build()
                    } else {
                        request = original.newBuilder()
                            .header("Content-Type", "application/json")
                            .method(original.method, original.body)
                            .build()
                    }

                    val interceptor = it.proceed(request)
                    if (interceptor.code == 401) {
                        Log.d("401", "Session Expired")

                        UserUtils.saveToken(ApplicationApp.appInstance, "")
                        UserUtils.saveUserData(ApplicationApp.appInstance, null)

                        val intent = Intent(ApplicationApp.appInstance, LoginActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(ApplicationApp.appInstance, intent, Bundle())
                    }
                    interceptor
                }
                if (WebEndpoints.IS_LOG_REQUIRED) {
                    val interceptor = HttpLoggingInterceptor()
                    interceptor.level = HttpLoggingInterceptor.Level.BODY
                    builder.addInterceptor(interceptor)
                }

                return builder.build()
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        }

    fun getApiService(): ApiService {
        if (apiService == null) {
            try {

                //                WebConstants.SERVER_URL = aesCrypt.decrypt("taUk9zKjn9E4p2J8rvxz0KW1PidqKnOIown80WoJgMQ=\n");
                //                WebConstants.SERVER_URL = "http://54.186.88.104:3003/";
            } catch (e: Exception) {
                e.printStackTrace()
            }

            val restAdapter = Retrofit.Builder()
                .baseUrl(WebEndpoints.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
                .addConverterFactory(StringConverter())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .client(unsafeOkHttpClient)
                .build()
            apiService = restAdapter.create(ApiService::class.java)
        }
        return apiService!!
    }

    fun getApiService(baseUrl: String): ApiService {
        val restAdapter = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
            .addConverterFactory(StringConverter())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(unsafeOkHttpClient)
            .build()
        return restAdapter.create(ApiService::class.java)
    }
}