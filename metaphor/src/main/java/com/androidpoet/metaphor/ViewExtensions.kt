/*
 *
 *  * Copyright 2022 AndroidPoet (Ranbir Singh)
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  * http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 *
 */


package com.androidpoet.metaphor

import android.animation.ValueAnimator
import android.graphics.Bitmap
import android.graphics.Bitmap.Config.ARGB_8888
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.view.View
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.annotation.Px
import androidx.annotation.RequiresApi
import androidx.core.animation.doOnEnd
import androidx.core.graphics.applyCanvas
import androidx.core.view.ViewCompat
import androidx.core.view.drawToBitmap
import androidx.transition.ArcMotion
import androidx.transition.TransitionManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.transition.MaterialContainerTransform
import com.google.android.material.transition.MaterialFade
import com.google.android.material.transition.MaterialFadeThrough
import com.google.android.material.transition.MaterialSharedAxis

/**
 * extension functions for View to make animations easier
 */

/** This method will be used for fade FadeThrough animation between two views
 * end view will be needed to perform a animation between them
 * */
public fun View.metaphorMaterialFadeThroughBetweenViews(
  endView: View
): MaterialFadeThrough {
  val parent = parent as? ViewGroup
  val fadeThrough = MaterialFadeThrough()
  fadeThrough.duration = 1000L
// Begin watching for changes in the View hierarchy.
  if (parent != null) {
    TransitionManager.beginDelayedTransition(parent, fadeThrough)
  }
// Make any changes to the hierarchy to be animated by the fade through transition.
  visibility = GONE
  endView.visibility = VISIBLE
  return fadeThrough
}

/** This method will be used for fade FadeThrough animation between two views
 * end view will be needed to perform a animation between them
 * */
public fun View.metaphorMaterialFadeBetweenViews(): MaterialFade {
  val fade = MaterialFade()
  val parent = parent as? ViewGroup
// Begin watching for changes in the View hierarchy.
  if (parent != null) {
    TransitionManager.beginDelayedTransition(parent, fade)
  }
// Make any changes to the hierarchy to be animated by the fade through transition.
  visibility = VISIBLE

  return fade
}

/** This method will be used for fade MaterialContainerTransform animation between two views
 * end view will be needed to perform a animation between them
 * */
public fun View.metaphorMaterialContainerTransformViewIntoAnotherView(

  endView: View

): MaterialContainerTransform {

  val parent = parent as? ViewGroup
  val transition = buildContainerTransformation()

  transition.startView = this
  transition.endView = endView

  transition.addTarget(endView)

  if (parent != null) {
    TransitionManager.beginDelayedTransition(parent, transition)
  }
  visibility = INVISIBLE
  endView.visibility = VISIBLE
  return transition
}

public fun View.buildContainerTransformation(): MaterialContainerTransform =
  MaterialContainerTransform().apply {
    scrimColor = Color.TRANSPARENT
    duration = 300
    setPathMotion(ArcMotion())
  }

/** This method will be used for fade MaterialContainerTransform animation between two views
 * end view will be needed to perform a animation between them
 * Axis is axis in which animation will be perform
 * forward is bool function to perform animation in up or down
 * */
public fun View.metaphorSharedAxisTransformationBetweenViews(
  endView: View,
  Axis: Int,
  forward: Boolean
): MaterialSharedAxis {
  val parent = parent as? ViewGroup
  // Set up a new MaterialSharedAxis in the specified axis and direction.
  val sharedAxis = MaterialSharedAxis(Axis, forward)

// Begin watching for changes in the View hierarchy.
  if (parent != null) {
    TransitionManager.beginDelayedTransition(parent, sharedAxis)
  }

// Make any changes to the hierarchy to be animated by the shared axis transition.
  visibility = GONE
  endView.visibility = VISIBLE
  return sharedAxis
}

/** Show view with MaterialFade */
public fun View.metaphorShowViewWithMaterialFade(): MaterialFade {
  val parent = parent as? ViewGroup
  val materialFade = MaterialFade().apply {
    duration = 550L
  }
  if (parent != null) {
    TransitionManager.beginDelayedTransition(parent, materialFade)
  }
  visibility = VISIBLE
  return materialFade
}

/** Hide view with MaterialFade */
public fun View.metaphorHideViewWithMaterialFade(): MaterialFade {
  val parent = parent as? ViewGroup
  val materialFade = MaterialFade().apply {
    duration = 550L
  }
  if (parent != null) {
    TransitionManager.beginDelayedTransition(parent, materialFade)
  }
  visibility = INVISIBLE
  return materialFade
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
