package com.viol8.flash.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.TelephonyManager
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.viol8.flash.config.Constants
import com.viol8.flash.config.WebEndpoints

class CallReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val state = intent.getStringExtra(TelephonyManager.EXTRA_STATE)
        if (state == TelephonyManager.EXTRA_STATE_RINGING) {
            val number = intent.extras!!.getString(TelephonyManager.EXTRA_INCOMING_NUMBER)
            if (number == WebEndpoints.FLASH_NUMBER) {
                val intent = Intent(Constants.FLASH_BROADCAST_ACTION)
                intent.putExtra("phone", number)
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent)
            }
        }
    }
}