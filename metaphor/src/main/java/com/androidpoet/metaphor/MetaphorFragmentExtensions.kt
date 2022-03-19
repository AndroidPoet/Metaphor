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

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Color
import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.transition.ArcMotion
import androidx.transition.Slide
import com.google.android.material.transition.MaterialArcMotion
import com.google.android.material.transition.MaterialContainerTransform
import com.google.android.material.transition.MaterialElevationScale
import com.google.android.material.transition.MaterialFade
import com.google.android.material.transition.MaterialFadeThrough
import com.google.android.material.transition.MaterialSharedAxis

// ////Fragments//////

/**MaterialSharedAxis extension function witch returns MaterialSharedAxis Object
parma axis which will decide the the axis from the animation
foreword is used the decide the direction of animation*/

public fun Fragment.metaphorMaterialSharedAxisInFragment(
  axis: Int,
  forword: Boolean
): MaterialSharedAxis {
  var materialSharedAxis = MaterialSharedAxis(axis, forword)
  enterTransition = materialSharedAxis
  exitTransition = materialSharedAxis
  if (forword) {
    materialSharedAxis = MaterialSharedAxis(axis, true)
  } else {
    materialSharedAxis = MaterialSharedAxis(axis, false)
  }
  reenterTransition = materialSharedAxis
  returnTransition = materialSharedAxis
  return materialSharedAxis
}

/** MaterialFade will create fade effects between two fragments*/

public fun Fragment.metaphorMaterialFadeInFragment(): MaterialFade {
  val materialFade = MaterialFade()
  materialFade.duration = 500L
  enterTransition = materialFade
  exitTransition = materialFade
  reenterTransition = materialFade
  returnTransition = materialFade
  return materialFade
}

/** MaterialFadeThrough will create fade effects between two fragments*/

public fun Fragment.metaphorMaterialFadeThroughInFragment(): MaterialFadeThrough {
  val materialFadeThrough = MaterialFadeThrough()
  materialFadeThrough.duration = 550L
  enterTransition = materialFadeThrough
  exitTransition = materialFadeThrough
  reenterTransition = materialFadeThrough
  returnTransition = materialFadeThrough
  return materialFadeThrough
}

/** We need three function to perform a MaterialContainerTransform between two fragments*/

/** 1st method for creating MaterialContainerTransform inside detail fragment and will be called inside
 * OnViewCreated*/
@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
public fun Fragment.metaphorDestinationFragmentMaterialContainerTransform(
  view: View,
  transitionName: String
): MaterialContainerTransform {
  val materialContainerTransform = MaterialContainerTransform()
  view.transitionName = transitionName
  sharedElementEnterTransition = materialContainerTransform.apply {
    duration = 300L
    setAllContainerColors(Color.TRANSPARENT)
    scrimColor = Color.TRANSPARENT
    setPathMotion(MaterialArcMotion())
  }
  return materialContainerTransform
}

/** MaterialElevationScale will bes used as Hold to manage change int he component size it be merge size changes smoothly*/

public fun Fragment.metaphorStartFragmentMaterialContainerTransform(view: View) {
  var materialElevationScale = MaterialElevationScale(false)
  exitTransition = materialElevationScale.apply {
    duration = resources.getInteger(R.integer.metaphorreply_motion_duration_large).toLong()
  }
  materialElevationScale = MaterialElevationScale(true)
  reenterTransition = materialElevationScale.apply {
    duration = resources.getInteger(R.integer.metaphorreply_motion_duration_large).toLong()
  }
  postponeEnterTransition()
  view.doOnPreDraw { startPostponedEnterTransition() }
}

/** will be used as Hold to manage change int he component size it be merge size changes smoothly*/
public fun Fragment.metaphorStartFragmentWithoutAnimation(view: View) {
  postponeEnterTransition()
  view.doOnPreDraw { startPostponedEnterTransition() }
}

/**
 * builds MaterialContainerTransform object
 * */

public fun Fragment.buildContainerTransformation(): MaterialContainerTransform =
  MaterialContainerTransform().apply {
    scrimColor = Color.TRANSPARENT
    duration = 300
    setPathMotion(ArcMotion())
  }

/** transform view into Fragment with MaterialContainerTransform */
public fun Fragment.metaphorMaterialContainerTransformViewIntoFragment(
  viewStart: View,
  viewEnd: View
): MaterialContainerTransform {
// Set transitions here so we are able to access Fragment's binding views.
  val materialFade = MaterialContainerTransform()
  enterTransition = materialFade.apply {
    // Manually add the Views to be shared since this is not a standard Fragment to
    // Fragment shared element transition.

    startView = viewStart
    endView = viewEnd
    duration = resources.getInteger(R.integer.metaphorreply_motion_duration_large).toLong()
    scrimColor = Color.TRANSPARENT
    containerColor = Color.TRANSPARENT
    startContainerColor = Color.TRANSPARENT
    endContainerColor = Color.TRANSPARENT
    setPathMotion(MaterialArcMotion())
  }
  returnTransition = Slide().apply {
    duration = resources.getInteger(R.integer.metaphorreply_motion_duration_medium).toLong()
    addTarget(viewEnd)
  }
  return materialFade
}

public fun Context.activity(): Activity? = when (this) {
  is Activity -> this
  else -> (this as? ContextWrapper)?.baseContext?.activity()
}

/** hide view extensions*/
