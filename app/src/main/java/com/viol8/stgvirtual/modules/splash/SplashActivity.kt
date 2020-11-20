package com.viol8.stgvirtual.modules.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.viol8.stgvirtual.R
import com.viol8.stgvirtual.modules.home.HomeActivity
import com.viol8.stgvirtual.modules.session.LoginActivity
import com.viol8.stgvirtual.utils.UserUtils


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(mainLooper).postDelayed({
            if (UserUtils.getToken(this).isNullOrEmpty()) {
                startActivity(Intent(this, LoginActivity::class.java))
            } else {
                startActivity(Intent(this, HomeActivity::class.java))
            }
            finish()
        }, 2000)
    }
}