
package com.androidpoet.metaphor

import android.os.Build
import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator
import android.view.animation.PathInterpolator
import androidx.annotation.RequiresApi
import androidx.core.graphics.PathParser
import androidx.interpolator.view.animation.FastOutLinearInInterpolator
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator
import androidx.transition.Transition
import com.google.android.material.transition.MaterialContainerTransform
import com.google.android.material.transition.MaterialElevationScale
import com.google.android.material.transition.MaterialFade
import com.google.android.material.transition.MaterialFadeThrough
import com.google.android.material.transition.MaterialSharedAxis

/** applies Metaphor form attributes to a View instance. */
@JvmSynthetic
@PublishedApi
internal fun getMetaphorAnimation(animation: MetaphorAnimation): Transition? {

  when (animation) {
    MetaphorAnimation.ContainerTransform -> {
      return MaterialContainerTransform()
    }
    MetaphorAnimation.FadeThrough -> {

      return MaterialFadeThrough()
    }

    MetaphorAnimation.Fade -> {
      return MaterialFade()
    }
    MetaphorAnimation.SharedAxisXForward -> {

      return MaterialSharedAxis(MaterialSharedAxis.X, true)
    }

    MetaphorAnimation.SharedAxisYForward -> {

      return MaterialSharedAxis(MaterialSharedAxis.Y, true)
    }

    MetaphorAnimation.SharedAxisZForward -> {

      return MaterialSharedAxis(MaterialSharedAxis.Z, true)
    }
    MetaphorAnimation.SharedAxisXBackward -> {

      return MaterialSharedAxis(MaterialSharedAxis.X, false)
    }

    MetaphorAnimation.SharedAxisYBackward -> {
      return MaterialSharedAxis(MaterialSharedAxis.Y, false)
    }

    MetaphorAnimation.SharedAxisZBackward -> {

      return MaterialSharedAxis(MaterialSharedAxis.Z, false)
    }

    MetaphorAnimation.ElevationScale -> {
      return MaterialElevationScale(false)
    }
    MetaphorAnimation.ElevationScaleGrow -> {
      return MaterialElevationScale(true)
    }

    MetaphorAnimation.None -> {
      // trick for no animations
      return null
    }
  }
}

/** applies Metaphor form attributes to a View instance. */
@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
@JvmSynthetic
@PublishedApi
internal fun getWindowMetaphorAnimation(animation: MetaphorAnimation): android.transition.Transition? {

  when (animation) {
    MetaphorAnimation.ContainerTransform -> {
      return com.google.android.material.transition.platform.MaterialContainerTransform()
    }
    MetaphorAnimation.FadeThrough -> {

      return com.google.android.material.transition.platform.MaterialFadeThrough()
    }

    MetaphorAnimation.Fade -> {
      return com.google.android.material.transition.platform.MaterialFade()
    }
    MetaphorAnimation.SharedAxisXForward -> {

      return com.google.android.material.transition.platform.MaterialSharedAxis(
        com.google.android.material.transition.platform.MaterialSharedAxis.X,
        true
      )
    }

    MetaphorAnimation.SharedAxisYForward -> {

      return com.google.android.material.transition.platform.MaterialSharedAxis(
        com.google.android.material.transition.platform.MaterialSharedAxis.Y,
        true
      )
    }

    MetaphorAnimation.SharedAxisZForward -> {

      return com.google.android.material.transition.platform.MaterialSharedAxis(
        com.google.android.material.transition.platform.MaterialSharedAxis.Z,
        true
      )
    }
    MetaphorAnimation.SharedAxisXBackward -> {

      return com.google.android.material.transition.platform.MaterialSharedAxis(
        com.google.android.material.transition.platform.MaterialSharedAxis.X,
        true
      )
    }

    MetaphorAnimation.SharedAxisYBackward -> {
      return com.google.android.material.transition.platform.MaterialSharedAxis(
        com.google.android.material.transition.platform.MaterialSharedAxis.Y,
        true
      )
    }

    MetaphorAnimation.SharedAxisZBackward -> {

      return com.google.android.material.transition.platform.MaterialSharedAxis(
        com.google.android.material.transition.platform.MaterialSharedAxis.Z,
        true
      )
    }

    MetaphorAnimation.ElevationScale -> {
      return com.google.android.material.transition.platform.MaterialElevationScale(false)
    }
    MetaphorAnimation.ElevationScaleGrow -> {
      return com.google.android.material.transition.platform.MaterialElevationScale(true)
    }

    MetaphorAnimation.None -> {
      // trick for no animations
      return null
    }
  }
}

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
@JvmSynthetic
@PublishedApi
internal fun getInterpolator(animation: MetaphorInterpolator): Interpolator {

  when (animation) {
    MetaphorInterpolator.Standard -> {
      return FastOutSlowInInterpolator()
    }
    MetaphorInterpolator.Emphasized -> {
      return fastOutExtraSlowIn()
    }
    MetaphorInterpolator.Decelerated -> {
      return LinearOutSlowInInterpolator()
    }
    MetaphorInterpolator.Accelerated -> {
      return FastOutLinearInInterpolator()
    }
    MetaphorInterpolator.Linear -> {
      return LinearInterpolator()
    }
  }
}

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
private fun fastOutExtraSlowIn(): Interpolator {
  return PathInterpolator(PathParser.createPathFromPathData("M 0,0 C 0.05, 0, 0.133333, 0.06, 0.166666, 0.4 C 0.208333, 0.82, 0.25, 1, 1, 1"))
}
