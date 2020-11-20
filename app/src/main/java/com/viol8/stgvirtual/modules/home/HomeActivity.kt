package com.viol8.stgvirtual.modules.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.gson.Gson
import com.viol8.stgvirtual.R
import com.viol8.stgvirtual.bottomsheet.SignOutBottomDialogFragment
import com.viol8.stgvirtual.config.Constants
import com.viol8.stgvirtual.model.LeadResponse
import com.viol8.stgvirtual.modules.home.viewmodel.HomeViewModel
import com.viol8.stgvirtual.progressbar.ProgressBarHandler
import com.viol8.stgvirtual.utils.UserUtils
import com.viol8.stgvirtual.utils.preventDoubleClick
import com.viol8.stgvirtual.utils.snackbar
import kotlinx.android.synthetic.main.activity_home.*
import org.koin.android.viewmodel.ext.android.viewModel


class HomeActivity : AppCompatActivity() {
    private val mHomeViewModel: HomeViewModel by viewModel()
    private lateinit var mProgressView: ProgressBarHandler
    private var leadInfo: LeadResponse? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        mProgressView = ProgressBarHandler(this)
        initObserver()
        initView()
        initListener()
        UserUtils.getUserData(this)?.userid?.let {
            mHomeViewModel.getLeadApiWebCall(it)
        }
    }

    private fun initView() {
    }

    private fun initListener() {
        logoutBtn.setOnClickListener {
            it.preventDoubleClick()
            val dialogFragment = SignOutBottomDialogFragment.newInstance()
            dialogFragment.show(
                supportFragmentManager,
                "sign_out_dialog_fragment"
            )
        }
        leadCallBtn.setOnClickListener {
            it.preventDoubleClick()
            leadInfo?.leadNo?.let {
                val map = HashMap<String, Any?>()
                map[Constants.LEAD_NO] = leadInfo?.leadNo
                map[Constants.ID] = leadInfo?.leadId
                UserUtils.getUserData(this)?.let {
                    map[Constants.USER_ID] = it.userid
                }
                mHomeViewModel.callAgentApiWebCall(map)

                val intent = Intent(this, RemarksActivity::class.java)
                intent.putExtra(Constants.BUNDLE_DATA, Gson().toJson(leadInfo))
                startActivity(intent)
                ////////////////////////////////
                val actionIntent = Intent(Intent.ACTION_DIAL)
                actionIntent.data = Uri.parse("tel:${it}")
                startActivity(actionIntent)
            } ?: kotlin.run {
                snackbar("No data found!")
            }
        }
    }

    private fun initObserver() {
        mHomeViewModel.mProgressBar.observe(this, Observer {
            mProgressView.showOrHide(it)
        })
        mHomeViewModel.leadsLiveData.observe(this, Observer {
            if (it.first) {
                leadInfo = it.second
                leadInfo?.let {
                    leadCallBtn.text = it.leadNo
                }
            } else {
                it.third?.let { it1 -> snackbar(it1) }
            }
        })
    }
}