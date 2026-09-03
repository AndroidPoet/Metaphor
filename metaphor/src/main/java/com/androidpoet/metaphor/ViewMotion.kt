
package com.androidpoet.metaphor

import android.view.View
import android.view.ViewGroup
import androidx.annotation.MainThread
import androidx.core.view.isVisible
import androidx.transition.TransitionManager
import com.androidpoet.metaphor.internal.toAndroidXContainerTransform
import com.androidpoet.metaphor.internal.toAndroidXTransition

/**
 * Runs [changes] to this view hierarchy and animates the difference with [motion].
 *
 * This is the general primitive behind [morphInto] and [animateVisibility]: any visibility,
 * size or position change made inside [changes] is picked up by the transition.
 */
@MainThread
public inline fun ViewGroup.animateChanges(motion: Motion, changes: () -> Unit) {
  beginTransition(motion, target = null)
  changes()
}

/** Shows or hides this view, animating the change with [motion] inside its parent. */
@MainThread
public fun View.animateVisibility(visible: Boolean, motion: Motion = Motion.Fade()) {
  val parent = parent as? ViewGroup ?: run {
    isVisible = visible
    return
  }
  parent.beginTransition(motion, target = this)
  isVisible = visible
}

/** Toggles this view's visibility, animating the change with [motion]. */
@MainThread
public fun View.toggleVisibility(motion: Motion = Motion.Fade()) {
  animateVisibility(!isVisible, motion)
}

/**
 * Morphs this view into [target] with a container transform. Both views must share a parent.
 * This view is hidden and [target] is shown when the transition starts.
 */
@MainThread
public fun View.morphInto(
  target: View,
  motion: Motion.ContainerTransform = Motion.ContainerTransform(),
) {
  val parent = parent as? ViewGroup
  require(parent != null && parent === target.parent) { "morphInto needs both views to share a parent" }
  val transition = motion.toAndroidXContainerTransform().also {
    it.startView = this
    it.endView = target
    it.addTarget(target)
  }
  TransitionManager.beginDelayedTransition(parent, transition)
  isVisible = false
  target.isVisible = true
}

@PublishedApi
internal fun ViewGroup.beginTransition(motion: Motion, target: View?) {
  val transition = motion.toAndroidXTransition()
  if (target != null) transition.addTarget(target)
  TransitionManager.beginDelayedTransition(this, transition)
}
