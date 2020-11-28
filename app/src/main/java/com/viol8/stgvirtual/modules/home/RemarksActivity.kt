package com.viol8.stgvirtual.modules.home

import android.content.ComponentName
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.provider.CallLog
import android.telephony.PhoneNumberUtils
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.viol8.stgvirtual.R
import com.viol8.stgvirtual.bottomsheet.EmailBottomDialogFragment
import com.viol8.stgvirtual.config.Constants
import com.viol8.stgvirtual.model.LeadResponse
import com.viol8.stgvirtual.model.RemarksModel
import com.viol8.stgvirtual.modules.home.adapter.RemarksAdapter
import com.viol8.stgvirtual.modules.home.viewmodel.HomeViewModel
import com.viol8.stgvirtual.progressbar.ProgressBarHandler
import com.viol8.stgvirtual.utils.*
import kotlinx.android.synthetic.main.activity_remarks.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.lang.Long
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class RemarksActivity : AppCompatActivity() {
    private val mHomeViewModel: HomeViewModel by viewModel()
    private lateinit var mProgressView: ProgressBarHandler

    var mRemarksAdapter: RemarksAdapter? = null
    private var leadInfo: LeadResponse? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_remarks)

        mProgressView = ProgressBarHandler(this)
        initObserver()
        initView()
        initListener()
    }

    private fun initView() {
        submitRemarkBtn.setGradientTopBottom(
            intArrayOf(
                ContextCompat.getColor(this, R.color.colorPrimary),
                ContextCompat.getColor(this, R.color.colorPrimary)
            ), 30f
        )

        val data = intent.getStringExtra(Constants.BUNDLE_DATA)
        data?.let {
            leadInfo = Gson().fromJson(it, LeadResponse::class.java)
        }

        val list = ArrayList<RemarksModel>()
        val firstValue = RemarksModel()
        firstValue.id = 1
        firstValue.remark = "Highly Interested"
        list.add(firstValue)

        val secondValue = RemarksModel()
        secondValue.id = 2
        secondValue.remark = "Medium Interest"
        list.add(secondValue)

        val thirdValue = RemarksModel()
        thirdValue.id = 3
        thirdValue.remark = "Low Interest"
        list.add(thirdValue)

        val fourthValue = RemarksModel()
        fourthValue.id = 4
        fourthValue.remark = "Highly Priced"
        list.add(fourthValue)

        val fifthValue = RemarksModel()
        fifthValue.id = 5
        fifthValue.remark = "Call not picked"
        list.add(fifthValue)

        val sixthValue = RemarksModel()
        sixthValue.id = 6
        sixthValue.remark = "Call Later"
        list.add(sixthValue)

        setupRemarksAdapter(list)
    }

    private fun initListener() {
        sendSmsBtn.setOnClickListener {
            it.preventDoubleClick()

            val map = HashMap<String, Any?>()
            map[Constants.LEAD_NO] = leadInfo?.leadNo
            UserUtils.getUserData(this)?.let {
                map[Constants.USER_ID] = it.userid
            }
            mHomeViewModel.sendSmsApiWebCall(map)
        }
        sendEmailBtn.setOnClickListener {
            it.preventDoubleClick()

            val dialogFragment = EmailBottomDialogFragment.newInstance()
            dialogFragment.show(
                supportFragmentManager,
                "email_dialog_fragment"
            )
            dialogFragment.onItemClick = {
                val map = HashMap<String, Any?>()
                map[Constants.EMAIL_ID] = it
                UserUtils.getUserData(this)?.let {
                    map[Constants.USER_ID] = it.userid
                }
                mHomeViewModel.sendEmailApiWebCall(map)
            }
        }
        whatsappBtn.setOnClickListener {
            it.preventDoubleClick()
            leadInfo?.leadNo?.let { it1 -> openWhatsApp(it1) }
        }
        submitRemarkBtn.setOnClickListener {
            it.preventDoubleClick()
            val remarks = StringBuilder()
            mRemarksAdapter?.listItems?.let {
                it.forEach {
                    if (it.isSelected) {
                        remarks.append(it.id).append(",")
                    }
                }
            }
            if (remarks.isEmpty()) {
                snackbar("Please select a remarks.")
                return@setOnClickListener
            }

            val map = HashMap<String, Any?>()
            map[Constants.REMARKS] = remarks.deleteCharAt(remarks.length - 1).toString()
            map[Constants.LEAD_NO] = leadInfo?.leadNo
            map[Constants.ID] = leadInfo?.leadId
            UserUtils.getUserData(this)?.let {
                map[Constants.USER_ID] = it.userid
            }
            map[Constants.ADDITIONAL_REMARKS] = additionalRemarkEditTxt.text.toString().trim()
            mHomeViewModel.submitRemarkApiWebCall(map)
        }
    }

    fun setupRemarksAdapter(list: ArrayList<RemarksModel>) {
        if (mRemarksAdapter == null) {
            val layoutManager = LinearLayoutManager(this)
            remarksRecylerView?.layoutManager = layoutManager
            remarksRecylerView?.addItemDecoration(
                ItemOffsetDecoration(
                    this,
                    R.dimen.margin_general_half
                )
            )
            // Access the RecyclerView Adapter and load the data into it
            mRemarksAdapter = RemarksAdapter(this)
            remarksRecylerView?.adapter = mRemarksAdapter
            mRemarksAdapter?.onItemClick = {

            }
        }
        mRemarksAdapter?.setItems(list)
    }

    private fun openWhatsApp(number: String) {
        var number = number
        try {
            number = number.replace(" ", "").replace("+", "")
            val sendIntent = Intent("android.intent.action.MAIN")
            sendIntent.component = ComponentName("com.whatsapp", "com.whatsapp.Conversation")
            sendIntent.putExtra("jid", PhoneNumberUtils.stripSeparators(number) + "@s.whatsapp.net")
            startActivity(sendIntent)
        } catch (e: Exception) {
            Log.e("RemarksActivity", "ERROR_OPEN_MESSANGER$e")
        }
    }

    private fun initObserver() {
        mHomeViewModel.mProgressBar.observe(this, Observer {
            mProgressView.showOrHide(it)
        })
        mHomeViewModel.sendSmsLiveData.observe(this, Observer {
            if (it.first) {
                it.second.message?.let { snackbar(it) }
            } else {
                it.third?.let { it1 -> snackbar(it1) }
            }
        })
        mHomeViewModel.sendEmailLiveData.observe(this, Observer {
            if (it.first) {
                it.second.message?.let { snackbar(it) }
            } else {
                it.third?.let { it1 -> snackbar(it1) }
            }
        })
        mHomeViewModel.submitRemarksLiveData.observe(this, Observer {
            if (it.first) {
                it.second.message?.let { snackbar(it) }
                Handler(mainLooper).postDelayed({ finish() }, 1000)
            } else {
                it.third?.let { it1 -> snackbar(it1) }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        getLastNumber()
    }

    private fun getLastNumber() {
        //this help you to get recent call
        val contacts: Uri = CallLog.Calls.CONTENT_URI
        val managedCursor: Cursor? = getContentResolver().query(
            contacts, null, null,
            null, CallLog.Calls.DATE + " DESC limit 1;"
        )
        managedCursor?.let {
            val number: Int = managedCursor.getColumnIndex(CallLog.Calls.NUMBER)
            val type: Int = managedCursor.getColumnIndex(CallLog.Calls.TYPE)
            val date: Int = managedCursor.getColumnIndex(CallLog.Calls.DATE)
            val duration: Int = managedCursor.getColumnIndex(CallLog.Calls.DURATION)
            val sb = StringBuffer()
            managedCursor.moveToNext()
            val phNumber: String = managedCursor.getString(number)
            val callType: String = managedCursor.getString(type)
            val callDate: String = managedCursor.getString(date)
            //   val callDayTime = Date(java.lang.Long.valueOf(callDate)).toString()
            val callDayTime = SimpleDateFormat(":yyyy-mm-dd hh:mm:ss").format(
                Date(
                    Long.valueOf(
                        callDate
                    )
                )
            )
            val callDuration: Int = managedCursor.getInt(duration)
            managedCursor.close()
            val dircode = callType.toInt()
            if (CallLog.Calls.OUTGOING_TYPE == dircode) {
                leadInfo?.let {
                    if (it.leadNo == phNumber || phNumber.contains(it.leadNo.toString())) {
                        val map = HashMap<String, Any?>()
                        UserUtils.getUserData(this)?.let {
                            map[Constants.USER_ID] = it.userid
                        }
                        map[Constants.CUSTOMER_NO] = phNumber
                        map[Constants.LEAD_TALK_DURATION] = callDuration
                        map[Constants.START_TIME] = callDayTime
                        mHomeViewModel.insertCallDetailApiWebCall(map)
                    }
                }
            }
        }
    }
}