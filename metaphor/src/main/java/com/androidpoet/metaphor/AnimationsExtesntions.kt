
package com.androidpoet.metaphor

import com.google.android.material.transition.Hold
import com.google.android.material.transition.MaterialContainerTransform
import com.google.android.material.transition.MaterialElevationScale
import com.google.android.material.transition.MaterialFade
import com.google.android.material.transition.MaterialFadeThrough
import com.google.android.material.transition.MaterialSharedAxis

@JvmSynthetic
internal fun buildContainerTransform(): MaterialContainerTransform {
  return MaterialContainerTransform()
}

@JvmSynthetic
internal fun buildMaterialFadeThrough(): MaterialFadeThrough {
  return MaterialFadeThrough()
}

@JvmSynthetic
internal fun buildMaterialFade(): MaterialFade {
  return MaterialFade()
}

@JvmSynthetic
internal fun buildSharedAxis(axis: Int, forward: Boolean): MaterialSharedAxis {
  return MaterialSharedAxis(axis, forward)
}

@JvmSynthetic
internal fun buildHold(): Hold {
  return Hold()
}

@JvmSynthetic
internal fun buildMaterialElevationScale(grow: Boolean): MaterialElevationScale {
  return MaterialElevationScale(grow)
}
