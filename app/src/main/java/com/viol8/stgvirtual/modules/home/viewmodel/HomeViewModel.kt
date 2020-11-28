package com.viol8.stgvirtual.modules.home.viewmodel

import androidx.lifecycle.MutableLiveData
import com.viol8.stgvirtual.basemodules.BaseViewModel
import com.viol8.stgvirtual.model.LeadResponse
import com.viol8.stgvirtual.model.MessageResponse
import com.viol8.stgvirtual.model.ReportResponse
import com.viol8.stgvirtual.repositories.HomeRepository


class HomeViewModel(val repo: HomeRepository) : BaseViewModel() {

    val leadsLiveData = MutableLiveData<Triple<Boolean, LeadResponse, String?>>()
    val callAgentLiveData = MutableLiveData<Triple<Boolean, MessageResponse, String?>>()
    val sendSmsLiveData = MutableLiveData<Triple<Boolean, MessageResponse, String?>>()
    val sendEmailLiveData = MutableLiveData<Triple<Boolean, MessageResponse, String?>>()
    val submitRemarksLiveData = MutableLiveData<Triple<Boolean, MessageResponse, String?>>()
    val insertCallDetailLiveData = MutableLiveData<Triple<Boolean, MessageResponse, String?>>()
    val getReportLiveData = MutableLiveData<Triple<Boolean, ArrayList<ReportResponse>, String?>>()

    fun getLeadApiWebCall(userId: String?) {
        repo.getLeadApiWebCall(userId, true, {
            mProgressBar.postValue(it)
        }, {
            leadsLiveData.postValue(Triple(true, it, null))
        }, {
            leadsLiveData.postValue(Triple(false, LeadResponse(), it))
        })
    }

    fun callAgentApiWebCall(map: HashMap<String, Any?>) {
        repo.callAgentApiWebCall(map, false, {
            mProgressBar.postValue(it)
        }, {
            callAgentLiveData.postValue(Triple(true, it, null))
        }, {
            callAgentLiveData.postValue(Triple(false, MessageResponse(), it))
        })
    }

    fun sendSmsApiWebCall(map: HashMap<String, Any?>) {
        repo.sendSmsApiWebCall(map, true, {
            mProgressBar.postValue(it)
        }, {
            sendSmsLiveData.postValue(Triple(true, it, null))
        }, {
            sendSmsLiveData.postValue(Triple(false, MessageResponse(), it))
        })
    }

    fun sendEmailApiWebCall(map: HashMap<String, Any?>) {
        repo.sendEmailApiWebCall(map, true, {
            mProgressBar.postValue(it)
        }, {
            sendEmailLiveData.postValue(Triple(true, it, null))
        }, {
            sendEmailLiveData.postValue(Triple(false, MessageResponse(), it))
        })
    }

    fun submitRemarkApiWebCall(map: HashMap<String, Any?>) {
        repo.submitRemarkApiWebCall(map, true, {
            mProgressBar.postValue(it)
        }, {
            submitRemarksLiveData.postValue(Triple(true, it, null))
        }, {
            submitRemarksLiveData.postValue(Triple(false, MessageResponse(), it))
        })
    }

    fun insertCallDetailApiWebCall(map: HashMap<String, Any?>) {
        repo.insertCallDetailApiWebCall(map, false, {
            mProgressBar.postValue(it)
        }, {
            insertCallDetailLiveData.postValue(Triple(true, it, null))
        }, {
            insertCallDetailLiveData.postValue(Triple(false, MessageResponse(), it))
        })
    }

    fun getReportApiWebCall(userId: String?) {
        repo.getReportApiWebCall(userId, true, {
            mProgressBar.postValue(it)
        }, {
            getReportLiveData.postValue(Triple(true, it, null))
        }, {
            getReportLiveData.postValue(Triple(false, ArrayList(), it))
        })
    }
}