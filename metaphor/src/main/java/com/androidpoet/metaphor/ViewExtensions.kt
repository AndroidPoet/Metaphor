
package com.androidpoet.metaphor

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Bitmap.Config.ARGB_8888
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.view.View
import android.view.View.GONE
import android.view.View.MeasureSpec
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.IdRes
import androidx.annotation.Px
import androidx.annotation.RequiresApi
import androidx.core.animation.doOnEnd
import androidx.core.content.res.use
import androidx.core.graphics.applyCanvas
import androidx.core.view.ViewCompat
import androidx.core.view.drawToBitmap
import androidx.core.view.forEach
import androidx.dynamicanimation.animation.DynamicAnimation.ViewProperty
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * An extension function which creates/retrieves a [SpringAnimation] and stores it in the [View]s
 * tag.
 */
public fun View.spring(
  property: ViewProperty,
  stiffness: Float = 200f,
  damping: Float = 0.3f,
  startVelocity: Float? = null
): SpringAnimation {
  val key = getKey(property)
  var springAnim = getTag(key) as? SpringAnimation?
  if (springAnim == null) {
    springAnim = SpringAnimation(this, property).apply {
      spring = SpringForce().apply {
        this.dampingRatio = damping
        this.stiffness = stiffness
        startVelocity?.let { setStartVelocity(it) }
      }
    }
    setTag(key, springAnim)
  }
  return springAnim
}

/**
 * Map from a [ViewProperty] to an `id` suitable to use as a [View] tag.
 */
@IdRes
private fun getKey(property: ViewProperty): Int {
  return when (property) {
    SpringAnimation.TRANSLATION_X -> R.id.metaphortranslation_x
    SpringAnimation.TRANSLATION_Y -> R.id.metaphortranslation_y
    SpringAnimation.TRANSLATION_Z -> R.id.metaphortranslation_z
    SpringAnimation.SCALE_X -> R.id.metaphorscale_x
    SpringAnimation.SCALE_Y -> R.id.metaphorscale_y
    SpringAnimation.ROTATION -> R.id.metaphorrotation
    SpringAnimation.ROTATION_X -> R.id.metaphorrotation_x
    SpringAnimation.ROTATION_Y -> R.id.metaphorrotation_y
    SpringAnimation.X -> R.id.metaphorx
    SpringAnimation.Y -> R.id.metaphory
    SpringAnimation.Z -> R.id.metaphorz
    SpringAnimation.ALPHA -> R.id.metaphoralpha
    SpringAnimation.SCROLL_X -> R.id.metaphorscroll_x
    SpringAnimation.SCROLL_Y -> R.id.metaphorscroll_y
    else -> throw IllegalAccessException("Unknown ViewProperty: $property")
  }
}

/**
 * Retrieve a color from the current [android.content.res.Resources.Theme].
 */
@ColorInt
public fun Context.themeColor(
  @AttrRes themeAttrId: Int
): Int {
  return obtainStyledAttributes(
    intArrayOf(themeAttrId)
  ).use {
    it.getColor(0, Color.MAGENTA)
  }
}

/**
 * Search this view and any children for a [ColorDrawable] `background` and return it's `color`,
 * else return `colorSurface`.
 */
@ColorInt
public fun View.descendantBackgroundColor(): Int {
  val bg = backgroundColor()
  if (bg != null) {
    return bg
  } else if (this is ViewGroup) {
    forEach {
      val childBg = descendantBackgroundColorOrNull()
      if (childBg != null) {
        return childBg
      }
    }
  }
  return context.themeColor(android.R.attr.colorBackground)
}

@ColorInt
private fun View.descendantBackgroundColorOrNull(): Int? {
  val bg = backgroundColor()
  if (bg != null) {
    return bg
  } else if (this is ViewGroup) {
    forEach {
      val childBg = backgroundColor()
      if (childBg != null) {
        return childBg
      }
    }
  }
  return null
}

/**
 * Check if this [View]'s `background` is a [ColorDrawable] and if so, return it's `color`,
 * otherwise `null`.
 */
@ColorInt
public fun View.backgroundColor(): Int? {
  val bg = background
  if (bg is ColorDrawable) {
    return bg.color
  }
  return null
}

/**
 * Walk up from a [View] looking for an ancestor with a given `id`.
 */
public fun View.findAncestorById(@IdRes ancestorId: Int): View {
  return when {
    id == ancestorId -> this
    parent is View -> (parent as View).findAncestorById(ancestorId)
    else -> throw IllegalArgumentException("$ancestorId not a valid ancestor")
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
@RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
public fun BottomNavigationView.show() {
  if (visibility == VISIBLE) return

  val parent = parent as ViewGroup
  // View needs to be laid out to create a snapshot & know position to animate. If view isn't
  // laid out yet, need to do this manually.
  if (!isLaidOut) {
    measure(
      MeasureSpec.makeMeasureSpec(parent.width, MeasureSpec.EXACTLY),
      MeasureSpec.makeMeasureSpec(parent.height, MeasureSpec.AT_MOST)
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
public fun View.drawToBitmap(@Px extraPaddingBottom: Int = 0): Bitmap {
  if (!ViewCompat.isLaidOut(this)) {
    throw IllegalStateException("View needs to be laid out before calling drawToBitmap()")
  }
  return Bitmap.createBitmap(width, height + extraPaddingBottom, ARGB_8888).applyCanvas {
    translate(-scrollX.toFloat(), -scrollY.toFloat())
    draw(this)
  }
}
