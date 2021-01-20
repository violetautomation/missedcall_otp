package com.viol8.stgvirtual.modules.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.viol8.stgvirtual.R
import kotlinx.android.synthetic.main.activity_phone_cerification_success.*


class PhoneVerificationSuccessActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone_cerification_success)

        verificationScreenBackBtn.setOnClickListener {
            finish()
        }
    }
}