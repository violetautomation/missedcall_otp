package com.viol8.stgvirtual.modules.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import com.viol8.stgvirtual.R
import com.viol8.stgvirtual.model.RemarksModel
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.remarks_list_item.view.*


class RemarksAdapter(val context: Context) :
    RecyclerView.Adapter<RemarksAdapter.RemarksAdapterViewHolder>() {

    var listItems = ArrayList<RemarksModel>()
    var onItemClick: ((RemarksModel) -> Unit)? =
        null

    class RemarksAdapterViewHolder(val view: View) : RecyclerView.ViewHolder(view),
        LayoutContainer {
        override val containerView: View
            get() = view

        val mCheckBox = containerView.remarkListItemCheckBox
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RemarksAdapterViewHolder {
        return RemarksAdapterViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.remarks_list_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RemarksAdapterViewHolder, position: Int) {
        val model = listItems[position]

        holder.mCheckBox.text = model.remark
        holder.mCheckBox.setOnCheckedChangeListener(object :
            CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                model.isSelected = isChecked
            }
        })
    }

    fun setItems(list: ArrayList<RemarksModel>) {
        listItems = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return listItems.size
    }
}