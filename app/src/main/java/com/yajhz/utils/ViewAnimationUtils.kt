package com.yajhz.utils

import android.view.View
import android.view.animation.Animation
import android.view.animation.ScaleAnimation

object ViewAnimationUtils {
    fun scaleAnimateView(view: View) {
        val animation =
            ScaleAnimation(
                1.15f, 1F, 1.15f, 1F,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
            )
        view.animation = animation
        animation.duration = 100
        animation.start()
    }
}
