package com.viol8.stgvirtual.utils

import android.Manifest
import android.animation.ValueAnimator
import android.app.Activity
import android.content.pm.PackageManager
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.*
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getColor
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.viol8.stgvirtual.R
import java.math.BigInteger
import java.security.MessageDigest

//https://stackoverflow.com/questions/4772537/i-need-to-change-the-stroke-color-to-a-user-defined-color-nothing-to-do-with-th
//{link @https://stackoverflow.com/questions/45929687/kotlin-remove-all-non-alphanumeric-characters}
fun String.removeSpecialChars(): String {
    val re = Regex("[^0-9]")
    return re.replace(this, "")
}

fun String.md5(): String {
    val md = MessageDigest.getInstance("MD5")
    return BigInteger(1, md.digest(toByteArray())).toString(16).padStart(32, '0')
}


///////////////////////////////////////////////////////////////////////////////////////////////////
/*
* {url: https://stackoverflow.com/questions/19534454/animating-strike-through-on-a-textview}
* */
fun TextView.startStrikeThroughAnimation() {
    val span = SpannableString(text)
    val strikeSpan = StrikethroughSpan()
    val animator = ValueAnimator.ofInt(text.length)
    animator.duration = 1500
    animator.addUpdateListener {
        span.setSpan(strikeSpan, 0, it.animatedValue as Int, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        text = span
        invalidate()
    }
    animator.start()
}


/*
* {link @https://stackoverflow.com/questions/10696986/how-to-set-the-part-of-the-text-view-is-clickable}
* */
fun TextView.underlineAndColor(
        startIndex: Int,
        endIndex: Int,
        isUnderline: Boolean,
        text: String,
        textColor: Int = -1
) {
    val content = SpannableString(text)
    if (isUnderline) {
        content.setSpan(UnderlineSpan(), startIndex, endIndex, 0)
    }
    if (textColor != -1) {
        content.setSpan(
                ForegroundColorSpan(textColor),
                startIndex,
                endIndex,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }
    this.text = content
}


fun TextView.colorAndBold(
        startIndex: Int,
        endIndex: Int,
        text: String,
        textColor: Int = -1
) {
    val content = SpannableString(text)
    content.setSpan(RelativeSizeSpan(1f), startIndex, endIndex, 0)
    content.setSpan(StyleSpan(Typeface.BOLD), 0, 8, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
    content.setSpan(
            ForegroundColorSpan(textColor),
            startIndex,
            endIndex,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    this.text = content
}


fun TextView.colorBoldSize(
        count: Int,
        startIndex: Int,
        endIndex: Int,
        text: String,
        textColor: Int = -1,
        textSize: Float
) {
    val content = SpannableString(text)
    content.setSpan(RelativeSizeSpan(textSize), startIndex, endIndex, 0)
    content.setSpan(StyleSpan(Typeface.NORMAL), startIndex, endIndex, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
    if (count > 0) {
        content.setSpan(
                ForegroundColorSpan(textColor),
                startIndex,
                endIndex,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }
    this.text = content
}


////////////////////////////////////////////////////////////////////////////////////
/*custom Snackbar start*/
fun Activity.snackbar(message: String) {
    val snackbar = Snackbar.make(this.findViewById<View>(android.R.id.content), message, 4 * 1000)

    // Changing action button text color
    val sbView = snackbar.view
    sbView.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))
    val textView = sbView.findViewById<View>(R.id.snackbar_text) as TextView
    textView.setTextColor(ContextCompat.getColor(this, R.color.white))
    snackbar.setActionTextColor(ContextCompat.getColor(this, R.color.white))
    textView.maxLines = 5

    snackbar.setAction(getString(R.string.title_ok)) {
        snackbar.dismiss()
    }
    snackbar.show()
}

////////////////////////////////////////////////////////////////////////////////////
/*custom Snackbar start*/
fun Activity.snackbar(view: View, message: String) {
    val snackbar = Snackbar.make(view, message, 4 * 1000)

    // Changing action button text color
    val sbView = snackbar.view
    sbView.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))
    val textView = sbView.findViewById<View>(R.id.snackbar_text) as TextView
    textView.maxLines = 5
    textView.setTextColor(ContextCompat.getColor(this, R.color.white))
    snackbar.setActionTextColor(ContextCompat.getColor(this, R.color.white))
    snackbar.show()
}


fun Activity.snackbar(view: View, message: String, onClick: () -> Unit) {
    val snackbar = Snackbar.make(view, message, 4 * 1000)

    // Changing action button text color
    val sbView = snackbar.view
    sbView.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))
    val textView = sbView.findViewById<View>(R.id.snackbar_text) as TextView
    textView.setTextColor(ContextCompat.getColor(this, R.color.white))
    snackbar.setActionTextColor(ContextCompat.getColor(this, R.color.white))
    textView.maxLines = 5

    snackbar.setAction(getString(R.string.title_ok)) {
        onClick.invoke()
    }
    snackbar.show()
}


fun Activity.snackbar(message: String, duration: Int) {
    val snackbar = Snackbar.make(this.findViewById<View>(android.R.id.content), message, duration)
    // Changing action button text color
    val sbView = snackbar.view
    sbView.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))
    val textView = sbView.findViewById<View>(R.id.snackbar_text) as TextView
    textView.setTextColor(ContextCompat.getColor(this, R.color.white))
    snackbar.setActionTextColor(ContextCompat.getColor(this, R.color.white))
    textView.maxLines = 5

    snackbar.setAction(getString(R.string.title_ok)) {
        snackbar.dismiss()
    }
    snackbar.show()
}


//
//fun FragmentActivity.snackbar(view: View, message: String, onClick: () -> Unit) {
//    val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
//
//    val typedValue = TypedValue()
//    val theme = this.getTheme()
//    theme.resolveAttribute(R.attr.primaryIconColor, typedValue, true)
//
//    // Changing action button text color
//    val sbView = snackbar.view
//    sbView.background = ContextCompat.getDrawable(this, typedValue.resourceId)
//    val textView = sbView.findViewById<View>(R.id.snackbar_text) as TextView
//    textView.setTextColor(ContextCompat.getColor(this, R.color.white))
//    snackbar.setActionTextColor(ContextCompat.getColor(this, R.color.white))
//
//    snackbar.setAction(getString(R.string.title_ok)) {
//        onClick.invoke()
//    }
//    snackbar.show()
//}
/*custom Snackbar end*/

///////////////////////////////////////////////////////////////////////////////////////

fun View.setGradientLeftRight(colors: IntArray) {
    //create a new gradient color
    val gd = GradientDrawable(
            GradientDrawable.Orientation.LEFT_RIGHT, colors
    )
    gd.cornerRadius = 0f
    setBackground(gd)
}

fun View.setGradientLeftRight(colors: IntArray, radius: Float) {
    //create a new gradient color
    val gd = GradientDrawable(
            GradientDrawable.Orientation.LEFT_RIGHT, colors
    )
    gd.cornerRadius = radius
    setBackground(gd)
}

fun View.setGradientTopBottom(colors: IntArray) {
    //create a new gradient color
    val gd = GradientDrawable(
            GradientDrawable.Orientation.TOP_BOTTOM, colors
    )
    gd.cornerRadius = 0f
    setBackground(gd)
}


fun View.setGradientTopBottom(colors: IntArray, radius: Float) {
    //create a new gradient color
    val gd = GradientDrawable(
            GradientDrawable.Orientation.TOP_BOTTOM, colors
    )
    gd.cornerRadius = radius
    setBackground(gd)
}


fun <T> LiveData<T>.debounce(duration: Long = 1000L) = MediatorLiveData<T>().also { mld ->
    val source = this
    val handler = android.os.Handler(android.os.Looper.getMainLooper())

    val runnable = Runnable {
        mld.value = source.value
    }

    mld.addSource(source) {
        handler.removeCallbacks(runnable)
        handler.postDelayed(runnable, duration)
    }
}


fun Activity.isStoragePermissionGranted(requestCode: Int): Boolean {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Log.v(this::class.java.name, "Permission is granted")
            return true
        } else {
            Log.v(this::class.java.name, "Permission is revoked")
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), requestCode)
            return false
        }
    } else { //permission is automatically granted on sdk<23 upon installation
        Log.v(this::class.java.name, "Permission is granted")
        return true
    }
}


fun TextView.setTextViewDrawableRelativesColor(color: Int) {
    for (drawable in compoundDrawablesRelative) {
        if (drawable != null) {
            drawable.colorFilter =
                    PorterDuffColorFilter(ContextCompat.getColor(context, color), PorterDuff.Mode.SRC_IN)
        }
    }
}

fun TextView.setTextViewDrawableColor(color: Int) {
    for (drawable in compoundDrawables) {
        if (drawable != null) {
            drawable.colorFilter =
                    PorterDuffColorFilter(ContextCompat.getColor(context, color), PorterDuff.Mode.SRC_IN)
        }
    }
}

fun TextView.setDrawableColor(@ColorRes color: Int) {
    compoundDrawables.filterNotNull().forEach {
        it.colorFilter = PorterDuffColorFilter(getColor(context, color), PorterDuff.Mode.SRC_IN)
    }
}

interface OnItemClickListener {
    fun onItemClicked(position: Int, view: View)
}

fun RecyclerView.addOnItemClickListener(onClickListener: OnItemClickListener) {
    this.addOnChildAttachStateChangeListener(object : RecyclerView.OnChildAttachStateChangeListener {
        override fun onChildViewDetachedFromWindow(view: View) {
            view.setOnClickListener(null)
        }

        override fun onChildViewAttachedToWindow(view: View) {
            view.setOnClickListener {
                val holder = getChildViewHolder(view)
                onClickListener.onItemClicked(holder.adapterPosition, view)
            }
        }


    })
}

fun View.preventDoubleClick() {
    isEnabled = false
    postDelayed(Runnable {
        isEnabled = true
    }, 500)
}

