package com.viol8.stgvirtual.bottomsheet

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.viol8.stgvirtual.R
import com.viol8.stgvirtual.utils.Validator
import com.viol8.stgvirtual.utils.preventDoubleClick
import kotlinx.android.synthetic.main.email_bottom_sheet.view.*

//{link @https://gist.github.com/ArthurNagy/1c4a64e6c8a7ddfca58638a9453e4aed}
class EmailBottomDialogFragment : BottomSheetDialogFragment() {
    lateinit var v: View
    var onItemClick: ((String) -> Unit)? = null

    override fun getTheme(): Int = R.style.BottomSheetDialogTheme

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme)

//        dialog.setOnShowListener {
//            dialog.behavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
//                override fun onStateChanged(bottomSheet: View, newState: Int) {
//                    if (newState == BottomSheetBehavior.STATE_HIDDEN) {
//                        dialog.dismiss()
//                    }
//                }
//
//                override fun onSlide(bottomSheet: View, slideOffset: Float) {
//                    if(!slideOffset.isNaN()) dialog.window?.setDimAmount(0.5f - ((slideOffset * -1)/2))
//                }
//            })
//        }

        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        v = inflater.inflate(R.layout.email_bottom_sheet, container, false)

        v.emailCancelBtn.setOnClickListener { dismiss() }
        v.sendEmailBtn.setOnClickListener {
            it.preventDoubleClick()
            if (Validator.isValidEmail(v.emailAddressEdtTxt.text.toString().trim())) {
                onItemClick?.invoke(v.emailAddressEdtTxt.text.toString().trim())
                dismiss()
            } else {
                Toast.makeText(context, "Please enter valid email", Toast.LENGTH_SHORT).show()
            }
        }
        return v
    }

    companion object {

        fun newInstance(): EmailBottomDialogFragment {
            return EmailBottomDialogFragment()
        }
    }
}