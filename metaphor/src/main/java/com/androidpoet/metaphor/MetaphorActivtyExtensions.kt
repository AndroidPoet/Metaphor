
package com.androidpoet.metaphor

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.Window
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.transition.ArcMotion
import androidx.transition.TransitionManager
import com.google.android.material.transition.MaterialContainerTransform
import com.google.android.material.transition.MaterialFadeThrough
import com.google.android.material.transition.MaterialSharedAxis
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback

// get currant Fragment from FragmentManager
public fun AppCompatActivity.currentFragmentFragmentManager(): Fragment {
  val fragments: List<Fragment> = supportFragmentManager.fragments
  return fragments[fragments.size - 1]
}

// get currant Fragment from FragmentManager
public fun AppCompatActivity.currentFragmentFragment(): Fragment? {
  val navHostFragment: Fragment? = supportFragmentManager.primaryNavigationFragment
  val fragment = navHostFragment?.childFragmentManager?.fragments?.get(0)
  return fragment
}

// get currant Fragment ChildFragmentManager
public fun AppCompatActivity.currentFragmentChildFragmentManager(int: Int): Fragment? {

  return supportFragmentManager.findFragmentById(int)
    ?.childFragmentManager
    ?.fragments
    ?.first()
}

// add  Fragment FragmentManager
public fun AppCompatActivity.addFragment(frameId: Int, fragment: Fragment, tag: String) {
  supportFragmentManager.commit {
    add(frameId, fragment, tag)
  }
}

// add  Fragment FragmentManager
public fun AppCompatActivity.replaceFragment(frameId: Int, fragment: Fragment, tag: String) {
  supportFragmentManager.commit {
    replace(frameId, fragment, tag)
  }
}

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

// ////Views//////
/** This method will be used for fade FadeThrough animation between two views
 * it will take root view basically CoordinatorLayout
 * start view and end view will be needed to perform a animation between them
 * */
public fun Activity.metaphorMaterialFadeThroughBetweenViews(
  root: CoordinatorLayout,
  startView: View,
  endView: View
): MaterialFadeThrough {
  val fadeThrough = MaterialFadeThrough()
// Begin watching for changes in the View hierarchy.
  TransitionManager.beginDelayedTransition(root, fadeThrough)
// Make any changes to the hierarchy to be animated by the fade through transition.
  startView.visibility = View.GONE
  endView.visibility = View.VISIBLE
  return fadeThrough
}

/** This method will be used for fade MaterialContainerTransform animation between two views
 * it will take root view basically CoordinatorLayout
 * start view and end view will be needed to perform a animation between them
 * */
public fun Activity.metaphorMaterialContainerTransformViewIntoAnotherView(
  root: CoordinatorLayout,
  startView: View,
  endView: View

):MaterialContainerTransform{
  val transition = buildContainerTransformation()

  transition.startView = startView
  transition.endView = endView

  transition.addTarget(endView)

  TransitionManager.beginDelayedTransition(root, transition)
  startView.visibility = View.INVISIBLE
  endView.visibility = View.VISIBLE
  return transition
}

/**
 * builds MaterialContainerTransform object
 * */

public fun Activity.buildContainerTransformation(): MaterialContainerTransform =
  MaterialContainerTransform().apply {
    scrimColor = Color.TRANSPARENT
    duration = 300
    setPathMotion(ArcMotion())
    interpolator = FastOutSlowInInterpolator()
    fadeMode = MaterialContainerTransform.FADE_MODE_IN
  }

/** This method will be used for fade MaterialContainerTransform animation between two views
 * it will take root view basically CoordinatorLayout
 * start view and end view will be needed to perform a animation between them
 * Axis is axis in which animation will be perform
 * forward is bool function to perform animation in up or down
 * */
public fun Activity.metaphorSharedAxisTransformationBetweenViews(
  root: CoordinatorLayout,
  startView: View,
  endView: View,
  Axis: Int,
  forward: Boolean
) :MaterialSharedAxis{
  // Set up a new MaterialSharedAxis in the specified axis and direction.
  val sharedAxis = MaterialSharedAxis(Axis, forward)

// Begin watching for changes in the View hierarchy.
  TransitionManager.beginDelayedTransition(root, sharedAxis)

// Make any changes to the hierarchy to be animated by the shared axis transition.
  startView.visibility = View.GONE
  endView.visibility = View.VISIBLE
  return sharedAxis
}

public inline fun <reified T : Activity> Context.openActivity(noinline extra: Intent.() -> Unit) {
  val intent = Intent(this, T::class.java)
  intent.extra()
  startActivity(intent)
}
