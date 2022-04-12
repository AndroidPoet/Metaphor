
package com.androidpoet.metaphor

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Bitmap.Config.ARGB_8888
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.annotation.Px
import androidx.annotation.RequiresApi
import androidx.core.animation.doOnEnd
import androidx.core.graphics.applyCanvas
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import androidx.transition.TransitionManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.transition.MaterialContainerTransform

/** applies Animation form attributes to a View instance. */
@JvmSynthetic
internal fun View.applyAnimation(
  metaphor: MetaphorView
) {
  val parent = parent as? ViewGroup
  // Set up a new MaterialSharedAxis in the specified axis and direction.

// Begin watching for changes in the View hierarchy.
  if (parent != null) {
    val transition = getMetaphorAnimation(metaphor.animation)
    if (transition != null) {
      transition.duration = metaphor.duration
      transition.setPathMotion(metaphor.motion)
    }

    if (transition is MaterialContainerTransform) {
      transition.scrimColor = Color.TRANSPARENT
      transition.startView = this
      transition.endView = metaphor.endView

      metaphor.endView?.let { transition.addTarget(it) }
    }
    TransitionManager.beginDelayedTransition(
      parent,
      transition
    )

// Make any changes to the hierarchy to be animated by the shared axis transition.

    if (this == metaphor.endView) {

      if (metaphor.endView.isVisible) {
        this.visibility = GONE
      } else {
        this.visibility = VISIBLE
      }
    } else {

      visibility = GONE
      if (metaphor.endView != null) {
        metaphor.endView.visibility = VISIBLE
      }
    }
  }
}
/**
 * Potentially animate showing a [BottomNavigationView].
 *
 * Abruptly changing the visibility leads to a re-layout of main content, animating
 * `translationY` leaves a gap where the view was that content does not fill.
 *
 * Instead, take a snapshot of the view, and animate this in, only changing the visibility (and
 * thus layout) when the animation completes.
 */
@SuppressLint("NewApi")
@RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
@JvmSynthetic
public fun BottomNavigationView.show() {
  if (visibility == VISIBLE) return

  val parent = parent as ViewGroup
  // View needs to be laid out to create a snapshot & know position to animate. If view isn't
  // laid out yet, need to do this manually.
  if (!isLaidOut) {
    measure(
      View.MeasureSpec.makeMeasureSpec(parent.width, View.MeasureSpec.EXACTLY),
      View.MeasureSpec.makeMeasureSpec(parent.height, View.MeasureSpec.AT_MOST)
    )
    layout(parent.left, parent.height - measuredHeight, parent.right, parent.height)
  }

  val drawable = BitmapDrawable(context.resources, drawToBitmap())
  drawable.setBounds(left, parent.height, right, parent.height + height)
  parent.overlay.add(drawable)
  ValueAnimator.ofInt(parent.height, top).apply {
    startDelay = 100L
    duration = 300L
    interpolator = AnimationUtils.loadInterpolator(
      context,
      android.R.interpolator.linear_out_slow_in
    )
    addUpdateListener {
      val newTop = it.animatedValue as Int
      drawable.setBounds(left, newTop, right, newTop + height)
    }
    doOnEnd {
      parent.overlay.remove(drawable)
      visibility = VISIBLE
    }
    start()
  }
}

/**
 * Potentially animate hiding a [BottomNavigationView].
 *
 * Abruptly changing the visibility leads to a re-layout of main content, animating
 * `translationY` leaves a gap where the view was that content does not fill.
 *
 * Instead, take a snapshot, instantly hide the view (so content lays out to fill), then animate
 * out the snapshot.
 */
@RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
@JvmSynthetic
public fun BottomNavigationView.hide() {
  if (visibility == GONE) return

  val drawable = BitmapDrawable(context.resources, drawToBitmap())
  val parent = parent as ViewGroup
  drawable.setBounds(left, top, right, bottom)
  parent.overlay.add(drawable)
  visibility = GONE
  ValueAnimator.ofInt(top, parent.height).apply {
    startDelay = 100L
    duration = 200L
    interpolator = AnimationUtils.loadInterpolator(
      context,
      android.R.interpolator.fast_out_linear_in
    )
    addUpdateListener {
      val newTop = it.animatedValue as Int
      drawable.setBounds(left, newTop, right, newTop + height)
    }
    doOnEnd {
      parent.overlay.remove(drawable)
    }
    start()
  }
}

/**
 * A copy of the KTX method, adding the ability to add extra padding the bottom of the [Bitmap];
 * useful when it will be used in a [android.graphics.BitmapShader][BitmapShader] with
 * a [android.graphics.Shader.TileMode.CLAMP][CLAMP tile mode].
 */
@JvmSynthetic
public fun View.drawToBitmap(@Px extraPaddingBottom: Int = 0): Bitmap {
  if (!ViewCompat.isLaidOut(this)) {
    throw IllegalStateException("View needs to be laid out before calling drawToBitmap()")
  }
  return Bitmap.createBitmap(width, height + extraPaddingBottom, ARGB_8888).applyCanvas {
    translate(-scrollX.toFloat(), -scrollY.toFloat())
    draw(this)
  }
}
