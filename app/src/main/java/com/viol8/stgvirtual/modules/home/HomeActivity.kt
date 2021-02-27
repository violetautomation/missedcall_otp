package com.viol8.stgvirtual.modules.home

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.viol8.flash.config.Constants
import com.viol8.flash.data.ServiceData
import com.viol8.flash.isReadLogStetePermissionGranted
import com.viol8.flash.preventDoubleClick
import com.viol8.stgvirtual.R
import com.viol8.stgvirtual.util.hideKeyboard
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        initView()
        initListener()
    }

    private fun initView() {
        val localBroadcastManager = LocalBroadcastManager.getInstance(this)
        localBroadcastManager.registerReceiver(
            onNotice,
            IntentFilter(Constants.FLASH_BROADCAST_ACTION)
        )
    }

    private fun initListener() {
        verify.setOnClickListener {
            it.preventDoubleClick()
            if (isReadLogStetePermissionGranted(26)) {
                missCallVerification()
            }
        }
    }

    private fun missCallVerification() {
        hideKeyboard(verify)
        progress.visibility = View.VISIBLE
        progressTitle.visibility = View.VISIBLE
        ServiceData.callAlert(
            countryCode.text.toString().trim().replace("+", ""),
            mobile.text.toString().trim(),
            getString(R.string.api_key),
            "50000"
        ) //country code +880, mobile any valid 10 digit number
    }


    private val onNotice: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            ServiceData.verifyCall {
                if (it) {
                    runOnUiThread {
                        progress.visibility = View.GONE
                        progressTitle.visibility = View.GONE

                        startActivity(
                            Intent(
                                this@HomeActivity,
                                PhoneVerificationSuccessActivity::class.java
                            )
                        )
                    }
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            26 -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //open screen
                    missCallVerification()
                } else {
                    // Permission Denied.
                }
            }
        }
    }

    override fun onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(onNotice)
        super.onDestroy()
    }
}