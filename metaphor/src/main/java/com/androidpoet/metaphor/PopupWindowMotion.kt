
package com.androidpoet.metaphor

import android.os.Build
import android.widget.PopupWindow
import androidx.annotation.MainThread
import androidx.annotation.RequiresApi
import com.androidpoet.metaphor.internal.toPlatformTransition

/**
 * Applies the [MotionSpec.enter] and [MotionSpec.exit] slots of [spec] to this PopupWindow.
 * The pop slots and the shared element do not apply to popups and are ignored.
 */
@RequiresApi(Build.VERSION_CODES.M)
@MainThread
public fun PopupWindow.applyMotion(spec: MotionSpec) {
  spec.enter?.let { enterTransition = it.toPlatformTransition() }
  spec.exit?.let { exitTransition = it.toPlatformTransition() }
}

/** DSL form of [applyMotion]. */
@RequiresApi(Build.VERSION_CODES.M)
@MainThread
public inline fun PopupWindow.applyMotion(block: MotionSpecBuilder.() -> Unit) {
  applyMotion(motionSpec(block))
}
