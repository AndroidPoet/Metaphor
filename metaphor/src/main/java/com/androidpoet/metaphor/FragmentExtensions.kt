
package com.androidpoet.metaphor

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.transition.Transition
import com.google.android.material.transition.MaterialContainerTransform

public fun Context.activity(): Activity? = when (this) {
  is Activity -> this
  else -> (this as? ContextWrapper)?.baseContext?.activity()
}

/** applies Animation form attributes to a View instance. */
@JvmSynthetic
internal fun Fragment.applyAnimation(
  metaphor: MetaphorFragment
) {
  enterTransition = getMetaphorAnimation(metaphor.enterAnimation)?.let { addAnimationProperties(it, metaphor, metaphor.enterDuration) }
  exitTransition = getMetaphorAnimation(metaphor.exitAnimation)?.let { addAnimationProperties(it, metaphor, metaphor.exitDuration) }
  reenterTransition =  getMetaphorAnimation(metaphor.reenterAnimation)?.let { addAnimationProperties(it, metaphor, metaphor.reenterDuration) }
  returnTransition = getMetaphorAnimation(metaphor.returnAnimation)?.let { addAnimationProperties(it, metaphor, metaphor.returnDuration) }
}

/** applies Properties on Animation form attributes. */
@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
@JvmSynthetic
internal fun Fragment.addAnimationProperties(
  transition: Transition,
  metaphor: MetaphorFragment,
  animationDuration: Long
): Transition {

  if (transition is MaterialContainerTransform) {
    metaphor.view?.transitionName = metaphor.transitionName
    sharedElementEnterTransition = transition.apply {
      startView?.let { addTarget(it) }
      setAllContainerColors(metaphor.containerColors)
      scrimColor = metaphor.scrimColor
    }
  }

  transition.apply {
    duration = animationDuration
    setPathMotion(metaphor.motion)
    allowEnterTransitionOverlap = metaphor.enterTransitionOverlap
    allowReturnTransitionOverlap = metaphor.returnTransitionOverlap
  }
  return transition
}

// hold extension to use in container transform.
@JvmSynthetic
public fun Fragment.hold() {
  postponeEnterTransition()
  view?.doOnPreDraw { startPostponedEnterTransition() }
}
