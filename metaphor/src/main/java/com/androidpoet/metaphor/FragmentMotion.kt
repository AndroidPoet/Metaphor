
package com.androidpoet.metaphor

import androidx.annotation.MainThread
import androidx.core.view.ViewCompat
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import com.androidpoet.metaphor.internal.toAndroidXContainerTransform
import com.androidpoet.metaphor.internal.toAndroidXTransition

/**
 * Applies [spec] to this Fragment's enter, exit, reenter, return and shared element transitions.
 *
 * Call it from `onCreate`. Slots that are `null` in [spec] are left untouched. When
 * [spec] carries a [MotionSpec.sharedElement] and [sharedElement] is a [SharedElement.Destination],
 * the destination view is given its transition name so the container transform can find it.
 */
@MainThread
public fun Fragment.applyMotion(
  spec: MotionSpec,
  sharedElement: SharedElement? = null,
) {
  spec.enter?.let { enterTransition = it.toAndroidXTransition() }
  spec.exit?.let { exitTransition = it.toAndroidXTransition() }
  spec.popEnter?.let { reenterTransition = it.toAndroidXTransition() }
  spec.popExit?.let { returnTransition = it.toAndroidXTransition() }
  allowEnterTransitionOverlap = spec.allowEnterOverlap
  allowReturnTransitionOverlap = spec.allowReturnOverlap

  val container = spec.sharedElement ?: return
  if (sharedElement is SharedElement.Destination) {
    ViewCompat.setTransitionName(sharedElement.view, sharedElement.transitionName)
  }
  sharedElementEnterTransition = container.toAndroidXContainerTransform()
}

/** DSL form of [applyMotion]. */
@MainThread
public inline fun Fragment.applyMotion(
  sharedElement: SharedElement? = null,
  block: MotionSpecBuilder.() -> Unit,
) {
  applyMotion(motionSpec(block), sharedElement)
}

/**
 * Postpones this Fragment's enter transition until its view has been laid out and is about to draw.
 *
 * Call it from `onViewCreated` on the screen a shared element transition returns to, so the
 * returning container can find the view it morphs back into (a RecyclerView item, for example).
 */
@MainThread
public fun Fragment.postponeEnterUntilDrawn() {
  postponeEnterTransition()
  view?.doOnPreDraw { startPostponedEnterTransition() }
}
