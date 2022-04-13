
package com.androidpoet.metaphor

import android.app.Activity
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.google.android.material.transition.platform.MaterialContainerTransform
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback

/** applies Animation form attributes to a View instance. */
@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
@JvmSynthetic
internal fun Activity.applyAnimation(
  metaphor: MetaphorActivity
) {

  val enterAnimation =
    getWindowMetaphorAnimation(metaphor.enterAnimation)?.let {
      addAnimationProperties(
        it,
        metaphor,
        metaphor.enterDuration
      )
    }
  val exitAnimation =
    getWindowMetaphorAnimation(metaphor.exitAnimation)?.let {
      addAnimationProperties(
        it,
        metaphor,
        metaphor.exitDuration
      )
    }
  val reenterAnimation =
    getWindowMetaphorAnimation(metaphor.reenterAnimation)?.let {
      addAnimationProperties(
        it,
        metaphor,
        metaphor.reenterDuration
      )
    }
  val returnAnimation =
    getWindowMetaphorAnimation(metaphor.returnAnimation)?.let {
      addAnimationProperties(
        it,
        metaphor,
        metaphor.returnDuration
      )
    }

  window.enterTransition = enterAnimation
  window.exitTransition = exitAnimation
  window.reenterTransition = reenterAnimation
  window.returnTransition = returnAnimation
}

/** applies Properties on Animation form attributes. */
@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
@JvmSynthetic
internal fun Activity.addAnimationProperties(
  transition: android.transition.Transition,
  metaphor: MetaphorActivity,
  animationDuration: Long
): android.transition.Transition {

  if (transition is MaterialContainerTransform) {

    if (metaphor.startActivity) {
      setExitSharedElementCallback(MaterialContainerTransformSharedElementCallback())
    } else {
      setEnterSharedElementCallback(MaterialContainerTransformSharedElementCallback())
      metaphor.view?.transitionName = metaphor.transitionName

      transition.apply {
        interpolator = FastOutSlowInInterpolator()
        fadeMode = MaterialContainerTransform.FADE_MODE_IN
      }
      window.sharedElementEnterTransition = transition.apply {
        setAllContainerColors(Color.TRANSPARENT)
        scrimColor = Color.TRANSPARENT
      }
      window.sharedElementReturnTransition = transition.apply {
        setAllContainerColors(Color.TRANSPARENT)
        scrimColor = Color.TRANSPARENT
      }
    }
  }

  transition.apply {
    duration = animationDuration
    pathMotion = metaphor.motion

    addTarget(metaphor.view)
    window.allowEnterTransitionOverlap = metaphor.enterTransitionOverlap
    window.allowReturnTransitionOverlap = metaphor.returnTransitionOverlap
  }
  return transition
}
