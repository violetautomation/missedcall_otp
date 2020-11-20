package com.viol8.stgvirtual.progressbar

import android.app.Activity
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.viol8.stgvirtual.R

class ProgressBarHandler(private val mContext: FragmentActivity) {
    private val mProgressBar: ProgressBar
    private val rl: RelativeLayout

    init {
        val layout = (mContext as Activity).findViewById<View>(android.R.id.content).rootView as ViewGroup

        mProgressBar = ProgressBar(mContext, null, android.R.attr.progressBarStyle)
        mProgressBar.isIndeterminate = true

        val params = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.MATCH_PARENT
        )

        rl = RelativeLayout(mContext)
        rl.background = ContextCompat.getDrawable(mContext, R.color.gray_transparent)

        rl.gravity = Gravity.CENTER
        rl.addView(mProgressBar)

        layout.addView(rl, params)

        mProgressBar.visibility = View.INVISIBLE
        rl.visibility = View.INVISIBLE
    }

    fun showOrHide(isShow: Boolean) {
        if (isShow) {
            mProgressBar.visibility = View.VISIBLE
            rl.visibility = View.VISIBLE
            mContext.window.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
        } else {
            mProgressBar.visibility = View.INVISIBLE
            rl.visibility = View.INVISIBLE
            mContext.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }
    }
}