
package com.androidpoet.metaphordemo

import com.androidpoet.metaphor.Motion
import com.androidpoet.metaphor.MotionSpec
import com.androidpoet.metaphor.motionSpec

/** App-wide motion specs, declared once and applied by whichever screen needs them. */
object Motions {

  /** A list screen that a container transform starts from. */
  val listOrigin: MotionSpec = motionSpec {
    exit = Motion.ElevationScale.shrink()
    popEnter = Motion.ElevationScale.grow()
  }

  /** A detail screen that a container transform lands on. */
  fun detailDestination(duration: Long = 300L): MotionSpec = motionSpec {
    sharedElement = Motion.ContainerTransform(duration = duration)
  }

  /** A top-level tab reached from the bottom bar. */
  val tab: MotionSpec = motionSpec {
    enter = Motion.ElevationScale.shrink()
    exit = Motion.SharedAxis.x(forward = false)
  }
}
