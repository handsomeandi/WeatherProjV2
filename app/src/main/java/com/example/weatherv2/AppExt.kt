package com.example.weatherv2

import android.app.Activity
import android.os.Build
import android.view.View
import android.view.WindowManager
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat

object AppExt {
    fun getString(@StringRes resId: Int) = HiltApp.shared.getString(resId)

    @Suppress("DEPRECATION")
    fun Activity.setStatusBarColor(@ColorRes statusBarColor: Int, lightTextColor: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = ContextCompat.getColor(this, statusBarColor)
            if (lightTextColor) window.decorView.systemUiVisibility =
                0 else window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = ContextCompat.getColor(this, statusBarColor)
        }
    }
    
    fun Activity.setNavigationBar(@ColorRes navigationBarColor: Int){
        window.navigationBarColor = ContextCompat.getColor(this, navigationBarColor)
//        if (lightTextColor) window.decorView.systemUiVisibility =
//            0 else window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
    }
}