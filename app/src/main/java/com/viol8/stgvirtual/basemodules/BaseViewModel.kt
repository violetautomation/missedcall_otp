package com.viol8.stgvirtual.basemodules

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {

    var mProgressBar = MutableLiveData<Boolean>()
}