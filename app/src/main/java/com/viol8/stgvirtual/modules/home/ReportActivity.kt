package com.viol8.stgvirtual.modules.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.viol8.stgvirtual.R
import com.viol8.stgvirtual.model.ReportResponse
import com.viol8.stgvirtual.modules.home.adapter.ReportsAdapter
import com.viol8.stgvirtual.modules.home.viewmodel.HomeViewModel
import com.viol8.stgvirtual.progressbar.ProgressBarHandler
import com.viol8.stgvirtual.utils.ItemOffsetDecoration
import com.viol8.stgvirtual.utils.UserUtils
import com.viol8.stgvirtual.utils.preventDoubleClick
import com.viol8.stgvirtual.utils.snackbar
import kotlinx.android.synthetic.main.activity_report.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*


class ReportActivity : AppCompatActivity() {
    private val mHomeViewModel: HomeViewModel by viewModel()
    private lateinit var mProgressView: ProgressBarHandler

    var mReportssAdapter: ReportsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report)

        mProgressView = ProgressBarHandler(this)
        initObserver()
        initView()
        initListener()
    }

    private fun initView() {
        UserUtils.getUserData(this)?.userid?.let {
            mHomeViewModel.getReportApiWebCall(it)
        }
    }

    private fun initListener() {
        reportBackbtn.setOnClickListener {
            it.preventDoubleClick()
            finish()
        }
        reportSearchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                if (mLocationAdapter != null) mLocationAdapter?.filter?.filter(s)
                mReportssAdapter?.let {
                    it.filter.filter(s)
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    fun setupReportsAdapter(list: ArrayList<ReportResponse>) {
        if (mReportssAdapter == null) {
            val layoutManager = LinearLayoutManager(this)
            reportRecylerView?.layoutManager = layoutManager
            reportRecylerView?.addItemDecoration(
                ItemOffsetDecoration(
                    this,
                    R.dimen.margin_general_half
                )
            )
            // Access the RecyclerView Adapter and load the data into it
            mReportssAdapter = ReportsAdapter(this)
            reportRecylerView?.adapter = mReportssAdapter
            mReportssAdapter?.onItemClick = {

            }
        }
        // Collections.sort(list, ReportNumberComparator())
        mReportssAdapter?.setItems(list)
    }

    private fun initObserver() {
        mHomeViewModel.mProgressBar.observe(this, Observer {
            mProgressView.showOrHide(it)
        })
        mHomeViewModel.getReportLiveData.observe(this, Observer {
            if (it.first) {
                if (it.second.isEmpty()) {
                    reportEmptyView.visibility = View.VISIBLE
                } else {
                    reportEmptyView.visibility = View.GONE
                }
                setupReportsAdapter(it.second)
            } else {
                reportEmptyView.visibility = View.VISIBLE
                it.third?.let { it1 -> snackbar(it1) }
            }
        })
    }
}