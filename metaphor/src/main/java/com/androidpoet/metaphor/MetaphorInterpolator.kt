
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

/** The Material easing curves. */
public enum class MetaphorInterpolator {
  Standard,
  Emphasized,
  Decelerated,
  Accelerated,
  Linear,
}

/** The platform [Interpolator] for this easing curve. */
@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
public fun MetaphorInterpolator.toInterpolator(): Interpolator = when (this) {
  MetaphorInterpolator.Standard -> FastOutSlowInInterpolator()
  MetaphorInterpolator.Emphasized -> PathInterpolator(
    PathParser.createPathFromPathData("M 0,0 C 0.05, 0, 0.133333, 0.06, 0.166666, 0.4 C 0.208333, 0.82, 0.25, 1, 1, 1"),
  )
  MetaphorInterpolator.Decelerated -> LinearOutSlowInInterpolator()
  MetaphorInterpolator.Accelerated -> FastOutLinearInInterpolator()
  MetaphorInterpolator.Linear -> LinearInterpolator()
}
