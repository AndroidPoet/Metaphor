
package com.androidpoet.metaphor

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Color
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

  val enterAnimation =
    getMetaphorAnimation(metaphor.enterAnimation)?.let { addAnimationProperties(it, metaphor, metaphor.enterDuration) }
  val exitAnimation =
    getMetaphorAnimation(metaphor.exitAnimation)?.let { addAnimationProperties(it, metaphor, metaphor.exitDuration) }
  val reenterAnimation =
    getMetaphorAnimation(metaphor.reenterAnimation)?.let { addAnimationProperties(it, metaphor, metaphor.reenterDuration) }
  val returnAnimation =
    getMetaphorAnimation(metaphor.returnAnimation)?.let { addAnimationProperties(it, metaphor, metaphor.returnDuration) }

  enterTransition = enterAnimation
  exitTransition = exitAnimation
  reenterTransition = reenterAnimation
  returnTransition = returnAnimation
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
      setAllContainerColors(Color.TRANSPARENT)
      scrimColor = Color.TRANSPARENT
    }
  }

  transition.apply {
    duration = animationDuration
    setPathMotion(metaphor.motion)
  }
  return transition
}

// hold extension to use in container transform.
@JvmSynthetic
public fun Fragment.hold() {
  postponeEnterTransition()
  view?.doOnPreDraw { startPostponedEnterTransition() }
}
