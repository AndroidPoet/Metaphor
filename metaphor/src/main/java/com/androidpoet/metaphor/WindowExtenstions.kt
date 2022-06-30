
package com.androidpoet.metaphor

import android.os.Build
import android.widget.PopupWindow
import androidx.annotation.RequiresApi

/** applies Animation form attributes to a View instance. */
@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
@JvmSynthetic
internal fun PopupWindow.applyAnimation(
  window: MetaphorWindow
) {

  val enterAnimation =
    getWindowMetaphorAnimation(window.enterAnimation)?.let {
      addAnimationProperties(
        it,
        window,
        window.enterDuration
      )
    }
  val exitAnimation =
    getWindowMetaphorAnimation(window.exitAnimation)?.let {
      addAnimationProperties(
        it,
        window,
        window.exitDuration
      )
    }

  this.enterTransition = enterAnimation
  this.exitTransition = exitAnimation
}

/** applies Properties on Animation form attributes. */
@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
@JvmSynthetic
internal fun PopupWindow.addAnimationProperties(
  transition: android.transition.Transition,
  window: MetaphorWindow,
  animationDuration: Long
): android.transition.Transition {

  transition.apply {
    duration = animationDuration
  }
  return transition
}
