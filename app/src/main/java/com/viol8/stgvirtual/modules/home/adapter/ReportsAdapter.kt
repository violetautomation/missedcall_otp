package com.viol8.stgvirtual.modules.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.viol8.stgvirtual.R
import com.viol8.stgvirtual.model.ReportResponse
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.reports_list_item.view.*


class ReportsAdapter(val context: Context) :
    RecyclerView.Adapter<ReportsAdapter.ReportsAdapterViewHolder>(), Filterable {

    var listItems = ArrayList<ReportResponse>()
    var onItemClick: ((ReportResponse) -> Unit)? =
        null

    private var mArrayList = ArrayList<ReportResponse>()
    private var mFilteredList = ArrayList<ReportResponse>()

    class ReportsAdapterViewHolder(val view: View) : RecyclerView.ViewHolder(view),
        LayoutContainer {
        override val containerView: View
            get() = view

        val mCustomerNo = containerView.customerNo
        val mAgentNo = containerView.agentNo
        val mCallTime = containerView.callTime
        val mCallDuration = containerView.callDuration
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ReportsAdapterViewHolder {
        return ReportsAdapterViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.reports_list_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ReportsAdapterViewHolder, position: Int) {
        val model = listItems[position]

        holder.mCustomerNo.text = "Customer No. - ${model.customerNo}"
        holder.mAgentNo.text = "Agent No. - ${model.agentNo}"
        holder.mCallTime.text = "Call Time - ${model.ansTime}"
        holder.mCallDuration.text = "Call Duration - ${model.leadTalkDuration}"
    }

    fun setItems(list: ArrayList<ReportResponse>) {
        listItems = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    override fun getFilter(): Filter {
        if (mArrayList.isEmpty()) {
            mArrayList = listItems
        }
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): Filter.FilterResults {
                val charString = charSequence.toString()
                if (charString.isEmpty()) {
                    mFilteredList = mArrayList
                } else {
                    val filteredList = ArrayList<ReportResponse>()

                    for (locationModel in mArrayList) {
                        if (locationModel.customerNo!!.toLowerCase().contains(charString)) {
                            filteredList.add(locationModel)
                        }
                    }
                    mFilteredList = filteredList
                }
                val filterResults = Filter.FilterResults()
                filterResults.values = mFilteredList
                return filterResults
            }

            override fun publishResults(
                charSequence: CharSequence,
                filterResults: Filter.FilterResults
            ) {
                mFilteredList = filterResults.values as ArrayList<ReportResponse>
                listItems = mFilteredList
                notifyDataSetChanged()
            }
        }
    }
}