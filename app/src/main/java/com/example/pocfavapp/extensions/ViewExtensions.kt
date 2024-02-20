package com.example.pocfavapp.extensions

import android.graphics.Rect
import android.view.View

val View.screenLocation
    get(): IntArray {
        val point = IntArray(2)
        getLocationInWindow(point)
        return point
    }

val View.boundingBox
    get(): Rect {
        val (x, y) = screenLocation
        return Rect(x, y, x + width, y + height)
    }