package com.viol8.stgvirtual.bottomsheet

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.viol8.stgvirtual.R
import com.viol8.stgvirtual.application.ApplicationApp
import com.viol8.stgvirtual.modules.session.LoginActivity
import com.viol8.stgvirtual.utils.UserUtils
import kotlinx.android.synthetic.main.signout_bottom_sheet.view.*

//{link @https://gist.github.com/ArthurNagy/1c4a64e6c8a7ddfca58638a9453e4aed}
class SignOutBottomDialogFragment : BottomSheetDialogFragment() {
    lateinit var v: View

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

        v = inflater.inflate(R.layout.signout_bottom_sheet, container, false)

        v.signoutBottomSheetCancelBtn.setOnClickListener { dismiss() }

        v.signoutBottomSheetConfirmBtn.setOnClickListener {
            UserUtils.saveToken(ApplicationApp.appInstance, "")
            UserUtils.saveUserData(ApplicationApp.appInstance, null)

            val intent = Intent(ApplicationApp.appInstance, LoginActivity::class.java)
            intent.flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            ContextCompat.startActivity(ApplicationApp.appInstance, intent, Bundle())
        }
        return v
    }

    companion object {

        fun newInstance(): SignOutBottomDialogFragment {
            return SignOutBottomDialogFragment()
        }
    }
}