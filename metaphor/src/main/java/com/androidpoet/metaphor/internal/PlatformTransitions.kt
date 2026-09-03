
package com.androidpoet.metaphor.internal

import android.os.Build
import android.transition.Transition
import androidx.annotation.RequiresApi
import com.androidpoet.metaphor.Motion
import com.androidpoet.metaphor.MotionPath
import com.google.android.material.transition.platform.Hold
import com.google.android.material.transition.platform.MaterialArcMotion
import com.google.android.material.transition.platform.MaterialContainerTransform
import com.google.android.material.transition.platform.MaterialElevationScale
import com.google.android.material.transition.platform.MaterialFade
import com.google.android.material.transition.platform.MaterialFadeThrough
import com.google.android.material.transition.platform.MaterialSharedAxis

/** Renders a [Motion] with the platform transition framework (Activities and PopupWindows). */
@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
internal fun Motion.toPlatformTransition(): Transition {
  val transition: Transition = when (this) {
    is Motion.Fade -> MaterialFade()
    is Motion.FadeThrough -> MaterialFadeThrough()
    is Motion.SharedAxis -> MaterialSharedAxis(axis.toMaterialAxis(), forward)
    is Motion.ElevationScale -> MaterialElevationScale(growing)
    is Motion.Hold -> Hold()
    is Motion.ContainerTransform -> toPlatformContainerTransform()
  }
  transition.duration = duration
  return transition
}

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
internal fun Motion.ContainerTransform.toPlatformContainerTransform(): MaterialContainerTransform =
  MaterialContainerTransform().also {
    it.duration = duration
    it.scrimColor = scrimColor
    it.setAllContainerColors(containerColor)
    it.fadeMode = fadeMode.toMaterialFadeMode()
    if (path == MotionPath.Arc) it.pathMotion = MaterialArcMotion()
  }
