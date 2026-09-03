
package com.androidpoet.metaphor.internal

import androidx.transition.Transition
import com.androidpoet.metaphor.Motion
import com.androidpoet.metaphor.MotionPath
import com.google.android.material.transition.Hold
import com.google.android.material.transition.MaterialArcMotion
import com.google.android.material.transition.MaterialContainerTransform
import com.google.android.material.transition.MaterialElevationScale
import com.google.android.material.transition.MaterialFade
import com.google.android.material.transition.MaterialFadeThrough
import com.google.android.material.transition.MaterialSharedAxis

/** Renders a [Motion] with the AndroidX transition framework (Fragments and Views). */
internal fun Motion.toAndroidXTransition(): Transition {
  val transition: Transition = when (this) {
    is Motion.Fade -> MaterialFade()
    is Motion.FadeThrough -> MaterialFadeThrough()
    is Motion.SharedAxis -> MaterialSharedAxis(axis.toMaterialAxis(), forward)
    is Motion.ElevationScale -> MaterialElevationScale(growing)
    is Motion.Hold -> Hold()
    is Motion.ContainerTransform -> toAndroidXContainerTransform()
  }
  transition.duration = duration
  return transition
}

internal fun Motion.ContainerTransform.toAndroidXContainerTransform(): MaterialContainerTransform =
  MaterialContainerTransform().also {
    it.duration = duration
    it.scrimColor = scrimColor
    it.setAllContainerColors(containerColor)
    it.fadeMode = fadeMode.toMaterialFadeMode()
    if (path == MotionPath.Arc) it.setPathMotion(MaterialArcMotion())
  }

internal fun Motion.SharedAxis.Axis.toMaterialAxis(): Int = when (this) {
  Motion.SharedAxis.Axis.X -> MaterialSharedAxis.X
  Motion.SharedAxis.Axis.Y -> MaterialSharedAxis.Y
  Motion.SharedAxis.Axis.Z -> MaterialSharedAxis.Z
}

internal fun Motion.ContainerTransform.FadeMode.toMaterialFadeMode(): Int = when (this) {
  Motion.ContainerTransform.FadeMode.In -> MaterialContainerTransform.FADE_MODE_IN
  Motion.ContainerTransform.FadeMode.Out -> MaterialContainerTransform.FADE_MODE_OUT
  Motion.ContainerTransform.FadeMode.Cross -> MaterialContainerTransform.FADE_MODE_CROSS
  Motion.ContainerTransform.FadeMode.Through -> MaterialContainerTransform.FADE_MODE_THROUGH
}
