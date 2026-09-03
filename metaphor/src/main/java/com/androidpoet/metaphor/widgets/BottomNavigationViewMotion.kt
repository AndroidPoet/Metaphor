
package com.androidpoet.metaphor.widgets

import android.animation.ValueAnimator
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.annotation.Px
import androidx.annotation.RequiresApi
import androidx.core.animation.doOnEnd
import androidx.core.graphics.applyCanvas
import androidx.core.view.ViewCompat
import com.google.android.material.bottomnavigation.BottomNavigationView

/** Slides this bar up from the bottom edge of its parent and makes it visible. */
@RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
public fun BottomNavigationView.show() {
  if (visibility == View.VISIBLE) return

  val parent = parent as ViewGroup
  if (!isLaidOut) {
    measure(
      View.MeasureSpec.makeMeasureSpec(parent.width, View.MeasureSpec.EXACTLY),
      View.MeasureSpec.makeMeasureSpec(parent.height, View.MeasureSpec.AT_MOST),
    )
    layout(parent.left, parent.height - measuredHeight, parent.right, parent.height)
  }

  val drawable = BitmapDrawable(context.resources, drawToBitmap())
  drawable.setBounds(left, parent.height, right, parent.height + height)
  parent.overlay.add(drawable)
  ValueAnimator.ofInt(parent.height, top).apply {
    startDelay = 100L
    duration = 300L
    interpolator = AnimationUtils.loadInterpolator(context, android.R.interpolator.linear_out_slow_in)
    addUpdateListener {
      val newTop = it.animatedValue as Int
      drawable.setBounds(left, newTop, right, newTop + height)
    }
    doOnEnd {
      parent.overlay.remove(drawable)
      visibility = View.VISIBLE
    }
    start()
  }
}

/** Slides this bar down past the bottom edge of its parent and makes it gone. */
@RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
public fun BottomNavigationView.hide() {
  if (visibility == View.GONE) return

  val drawable = BitmapDrawable(context.resources, drawToBitmap())
  val parent = parent as ViewGroup
  drawable.setBounds(left, top, right, bottom)
  parent.overlay.add(drawable)
  visibility = View.GONE
  ValueAnimator.ofInt(top, parent.height).apply {
    startDelay = 100L
    duration = 200L
    interpolator = AnimationUtils.loadInterpolator(context, android.R.interpolator.fast_out_linear_in)
    addUpdateListener {
      val newTop = it.animatedValue as Int
      drawable.setBounds(left, newTop, right, newTop + height)
    }
    doOnEnd { parent.overlay.remove(drawable) }
    start()
  }
}

/** Draws this laid-out view into a new bitmap. */
public fun View.drawToBitmap(@Px extraPaddingBottom: Int = 0): Bitmap {
  check(ViewCompat.isLaidOut(this)) { "View needs to be laid out before calling drawToBitmap()" }
  return Bitmap.createBitmap(width, height + extraPaddingBottom, Bitmap.Config.ARGB_8888).applyCanvas {
    translate(-scrollX.toFloat(), -scrollY.toFloat())
    draw(this)
  }
}
