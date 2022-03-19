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
import android.os.Build
import android.view.View
import android.view.Window
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback

// Perform SharedAxis  animation
public fun AppCompatActivity.MaterialSharedAxisAnimation(
  frameId: Int,
  b: Boolean,
  fragment: Fragment,
  axis: Int
) {

//    currentFragmentFragmentManager().exitTransition = buildTransitionMaterialSharedAxis(b, axis)
//    fragment.enterTransition = buildTransitionMaterialSharedAxis(b, axis)
  supportFragmentManager.commit {
    replace(frameId, fragment)
  }
}

// ////Activtys//////
@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
public fun Activity.metaphorMaterialContainerStartActivity() {

// theme with <item name=”android:windowActivityTransitions”>true</item>.
  window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)

// Attach a callback used to capture the shared elements from this Activity to be used
// by the container transform transition
  setExitSharedElementCallback(MaterialContainerTransformSharedElementCallback())

// Keep system bars (status bar, navigation bar) persistent throughout the transition.
  window.sharedElementsUseOverlay = false
  findViewById<View>(android.R.id.content).transitionName = "shared_element_container"
}

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
public fun Activity.metaphorMaterialContainerDestinationActivity() {

  // Enable Activity Transitions. Optionally enable Activity transitions in your
  // theme with <item name=”android:windowActivityTransitions”>true</item>.
  window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)

  // Set the transition name, which matches Activity A’s start view transition name, on
  // the root view.
  findViewById<View>(android.R.id.content).transitionName = "shared_element_container"

  // Attach a callback used to receive the shared elements from Activity A to be
  // used by the container transform transition.
  setEnterSharedElementCallback(MaterialContainerTransformSharedElementCallback())

  // Set this Activity’s enter and return transition to a MaterialContainerTransform
  window.sharedElementEnterTransition =
    com.google.android.material.transition.platform.MaterialContainerTransform().apply {
      addTarget(android.R.id.content)
      duration = 300L
    }
  window.sharedElementReturnTransition =
    com.google.android.material.transition.platform.MaterialContainerTransform().apply {
      addTarget(android.R.id.content)
      duration = 250L
    }
}

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
public fun Activity.metaphorSharedAxisStartActivity() {

  window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
  val exit = com.google.android.material.transition.platform.MaterialSharedAxis(
    com.google.android.material.transition.platform.MaterialSharedAxis.X,
    true
  ).apply {

    // Only run the transition on the contents of this activity, excluding
    // system bars or app bars if provided by the app’s theme.
    addTarget(android.R.id.content)
  }
  window.exitTransition = exit

  // TODO: Add a reenter transition in the backwards direction to animate
  // Activity B out and Activity A back in in the opposite direction.
}

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
public fun Activity.metaphorSharedAxisDestinationActivity() {

  window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)

  val enter = com.google.android.material.transition.platform.MaterialSharedAxis(
    com.google.android.material.transition.platform.MaterialSharedAxis.X,
    true
  ).apply {
    addTarget(android.R.id.content)
  }
  window.enterTransition = enter
  // TODO: Configure a return transition in the backwards direction.

  // Allow Activity A’s exit transition to play at the same time as this Activity’s
  // enter transition instead of playing them sequentially.
  window.allowEnterTransitionOverlap = true
}

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
public fun Activity.metaphorMaterialFadeThroughStartActivity() {
  window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
  val exit = com.google.android.material.transition.platform.MaterialFadeThrough().apply {

    // Only run the transition on the contents of this activity, excluding
    // system bars or app bars if provided by the app’s theme.
    addTarget(android.R.id.content)
  }
  window.exitTransition = exit
}

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
public fun Activity.metaphorMaterialFadeThroughDestinationActivity() {

  window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)

  val enter = com.google.android.material.transition.platform.MaterialFadeThrough().apply {
    addTarget(android.R.id.content)
  }
  window.enterTransition = enter

  // Allow Activity A’s exit transition to play at the same time as this Activity’s
  // enter transition instead of playing them sequentially.
  window.allowEnterTransitionOverlap = true
}
