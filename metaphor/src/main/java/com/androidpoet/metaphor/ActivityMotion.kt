
package com.androidpoet.metaphor

import android.app.Activity
import android.os.Build
import androidx.annotation.MainThread
import androidx.annotation.RequiresApi
import com.androidpoet.metaphor.internal.toPlatformContainerTransform
import com.androidpoet.metaphor.internal.toPlatformTransition
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback

/**
 * Applies [spec] to this Activity's window transitions.
 *
 * Call it from `onCreate`. Window transitions need `Window.FEATURE_ACTIVITY_TRANSITIONS`, which
 * is enabled by default for Material themes and can otherwise be requested with
 * `window.requestFeature(...)` before `setContentView`.
 *
 * For a container transform between Activities, the launching Activity passes
 * [SharedElement.Origin] and the launched Activity passes a [SharedElement.Destination].
 */
@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
@MainThread
public fun Activity.applyMotion(
  spec: MotionSpec,
  sharedElement: SharedElement? = null,
) {
  spec.enter?.let { window.enterTransition = it.toPlatformTransition() }
  spec.exit?.let { window.exitTransition = it.toPlatformTransition() }
  spec.popEnter?.let { window.reenterTransition = it.toPlatformTransition() }
  spec.popExit?.let { window.returnTransition = it.toPlatformTransition() }
  window.allowEnterTransitionOverlap = spec.allowEnterOverlap
  window.allowReturnTransitionOverlap = spec.allowReturnOverlap

  val container = spec.sharedElement ?: return
  window.sharedElementsUseOverlay = false
  when (sharedElement) {
    is SharedElement.Origin, null -> {
      setExitSharedElementCallback(MaterialContainerTransformSharedElementCallback())
    }
    is SharedElement.Destination -> {
      setEnterSharedElementCallback(MaterialContainerTransformSharedElementCallback())
      sharedElement.view.transitionName = sharedElement.transitionName
      window.sharedElementEnterTransition = container.toPlatformContainerTransform()
      window.sharedElementReturnTransition = container.toPlatformContainerTransform()
    }
  }
}

/** DSL form of [applyMotion]. */
@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
@MainThread
public inline fun Activity.applyMotion(
  sharedElement: SharedElement? = null,
  block: MotionSpecBuilder.() -> Unit,
) {
  applyMotion(motionSpec(block), sharedElement)
}
