package com.viol8.flash

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat

fun Activity.isReadLogStetePermissionGranted(requestCode: Int): Boolean {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        if (checkSelfPermission(Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(
                Manifest.permission.READ_PHONE_STATE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            Log.v(this::class.java.name, "Permission is granted")
            return true
        } else {
            Log.v(this::class.java.name, "Permission is revoked")
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_CALL_LOG),
                requestCode
            )
            return false
        }
    } else { //permission is automatically granted on sdk<23 upon installation
        Log.v(this::class.java.name, "Permission is granted")
        return true
    }
}


fun View.preventDoubleClick() {
    isEnabled = false
    postDelayed(Runnable {
        isEnabled = true
    }, 500)
}

