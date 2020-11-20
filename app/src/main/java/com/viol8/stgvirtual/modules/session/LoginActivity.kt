package com.viol8.stgvirtual.modules.session

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.viol8.stgvirtual.R
import com.viol8.stgvirtual.modules.home.HomeActivity
import com.viol8.stgvirtual.modules.session.viewmodel.AuthViewModel
import com.viol8.stgvirtual.progressbar.ProgressBarHandler
import com.viol8.stgvirtual.utils.UserUtils
import com.viol8.stgvirtual.utils.preventDoubleClick
import com.viol8.stgvirtual.utils.setGradientTopBottom
import com.viol8.stgvirtual.utils.snackbar
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.android.viewmodel.ext.android.viewModel


class LoginActivity : AppCompatActivity() {
    private val mAuthViewModel: AuthViewModel by viewModel()
    private lateinit var mProgressView: ProgressBarHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mProgressView = ProgressBarHandler(this)
        initObserver()
        initView()
        loginBtn.setOnClickListener {
            it.preventDoubleClick()
            mAuthViewModel.validateLoginCredentials(
                userName.text.toString(),
                passwd.text.toString()
            )
        }
        loginBackbtn.setOnClickListener {
            it.preventDoubleClick()
            finish()
        }
    }

    private fun initView() {
        loginBtn.setGradientTopBottom(
            intArrayOf(
                ContextCompat.getColor(this, R.color.colorPrimary),
                ContextCompat.getColor(this, R.color.colorPrimary)
            ), 30f
        )
    }

    private fun initObserver() {
        mAuthViewModel.mProgressBar.observe(this, Observer {
            mProgressView.showOrHide(it)
        })
        mAuthViewModel.loginLiveData.observe(this, Observer {
            if (it.first) {
                if (it.second.token.isNullOrEmpty()) {
                    it.third?.let { it1 -> snackbar(it1) }
                } else {
                    it.second.token?.let { token ->
                        UserUtils.saveToken(this, token)
                        UserUtils.saveUserData(this, it.second)
                        startActivity(Intent(this, HomeActivity::class.java))
                        finish()
                    }
                }
            } else {
                it.third?.let { it1 -> snackbar(it1) }
            }
        })
    }
}