
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
import com.google.android.material.transition.MaterialSharedAxis

public fun Context.activity(): Activity? = when (this) {
  is Activity -> this
  else -> (this as? ContextWrapper)?.baseContext?.activity()
}

/** applies Metaphor form attributes to a View instance. */

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
@JvmSynthetic
public fun Fragment.applyMetaphor(metaphor: MetaphorFragment) {

  when (metaphor.animation) {
    MetaphorAnimation.ContainerTransform -> {
      val transition = buildContainerTransform()
      metaphor.view?.transitionName = metaphor.transitionName
      sharedElementEnterTransition = transition.apply {
        duration = metaphor.duration
        setPathMotion(metaphor.motion)
        startView?.let { addTarget(it) }
        setAllContainerColors(Color.TRANSPARENT)
        scrimColor = Color.TRANSPARENT
      }
    }
    MetaphorAnimation.MaterialFadeThrough -> {
      val transition = buildMaterialFadeThrough()
      applyAnimation(transition, metaphor)
    }

    MetaphorAnimation.MaterialFade -> {
      val transition = buildMaterialFade()
      applyAnimation(transition, metaphor)
    }
    MetaphorAnimation.SharedAxisXForward -> {
      val transition = buildSharedAxis(MaterialSharedAxis.X, true)
      applyAnimation(transition, metaphor)
    }

    MetaphorAnimation.SharedAxisYForward -> {
      val transition = buildSharedAxis(MaterialSharedAxis.Y, true)
      applyAnimation(transition, metaphor)
    }

    MetaphorAnimation.SharedAxisZForward -> {

      val transition = buildSharedAxis(MaterialSharedAxis.Z, true)
      applyAnimation(transition, metaphor)
    }
    MetaphorAnimation.SharedAxisXBackward -> {
      val transition = buildSharedAxis(MaterialSharedAxis.X, false)
      applyAnimation(transition, metaphor)
    }

    MetaphorAnimation.SharedAxisYBackward -> {

      val transition = buildSharedAxis(MaterialSharedAxis.Y, false)
      applyAnimation(transition, metaphor)
    }

    MetaphorAnimation.SharedAxisZBackward -> {
      val transition = buildSharedAxis(MaterialSharedAxis.Z, false)
      applyAnimation(transition, metaphor)
    }

    MetaphorAnimation.Hold -> {
      postponeEnterTransition()
      metaphor.view?.doOnPreDraw { startPostponedEnterTransition() }
    }
    MetaphorAnimation.MaterialElevationScale -> {
      val transition = buildMaterialElevationScale(false)
      applyAnimation(transition, metaphor)
    }
    MetaphorAnimation.MaterialElevationScaleGrow -> {
      val transition = buildMaterialElevationScale(true)
      applyAnimation(transition, metaphor)
    }
  }
}

/** applies Animation form attributes to a View instance. */
@JvmSynthetic
internal fun Fragment.applyAnimation(
  transition: Transition,
  metaphor: MetaphorFragment
) {

  transition.apply {
    duration = metaphor.duration
    setPathMotion(metaphor.motion)
  }

  enterTransition = transition
  exitTransition = transition
  reenterTransition = transition
  returnTransition = transition
}
