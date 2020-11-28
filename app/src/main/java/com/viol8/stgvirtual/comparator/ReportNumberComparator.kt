package com.viol8.stgvirtual.comparator

import com.viol8.stgvirtual.model.ReportResponse

class ReportNumberComparator : Comparator<ReportResponse> {

    override fun compare(l1: ReportResponse?, l2: ReportResponse?): Int {
        return l1?.customerNo!!.compareTo(l2?.customerNo!!)
    }
}