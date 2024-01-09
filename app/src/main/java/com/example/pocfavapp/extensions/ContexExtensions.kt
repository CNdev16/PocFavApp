package com.example.pocfavapp.extensions

import android.content.Context

fun Context.getStatusBarHeight(): Int {
    return resources.getIdentifier("status_bar_height", "dimen", "android")
        .let { resourceId ->
            if (resourceId > 0) resources.getDimensionPixelSize(resourceId)
            else 0
        }
}
