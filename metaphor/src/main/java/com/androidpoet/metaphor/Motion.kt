
package com.androidpoet.metaphor

import android.graphics.Color
import androidx.annotation.ColorInt

/**
 * A Material motion pattern, described as plain data.
 *
 * A [Motion] knows nothing about Fragments, Activities or Views. It is turned into a concrete
 * transition by the target it is applied to (see [applyMotion], [morphInto], [animateChanges]).
 * Durations default to the values recommended by the Material motion spec.
 */
public sealed interface Motion {

  /** Length of the animation in milliseconds. */
  public val duration: Long

  /** Material fade: a small scale plus a fade of the whole target. */
  public data class Fade(
    override val duration: Long = 150L,
  ) : Motion

  /** Material fade through: outgoing content fades out, incoming content fades in and scales up. */
  public data class FadeThrough(
    override val duration: Long = 300L,
  ) : Motion

  /** Material shared axis: both screens move along [axis], [forward] decides the direction. */
  public data class SharedAxis(
    val axis: Axis,
    val forward: Boolean = true,
    override val duration: Long = 300L,
  ) : Motion {
    public enum class Axis { X, Y, Z }

    public companion object {
      @JvmStatic public fun x(forward: Boolean = true, duration: Long = 300L): SharedAxis = SharedAxis(Axis.X, forward, duration)

      @JvmStatic public fun y(forward: Boolean = true, duration: Long = 300L): SharedAxis = SharedAxis(Axis.Y, forward, duration)

      @JvmStatic public fun z(forward: Boolean = true, duration: Long = 300L): SharedAxis = SharedAxis(Axis.Z, forward, duration)
    }
  }

  /** Material elevation scale: the target scales and fades, [growing] picks grow or shrink. */
  public data class ElevationScale(
    val growing: Boolean,
    override val duration: Long = 300L,
  ) : Motion {
    public companion object {
      @JvmStatic public fun grow(duration: Long = 300L): ElevationScale = ElevationScale(growing = true, duration = duration)

      @JvmStatic public fun shrink(duration: Long = 300L): ElevationScale = ElevationScale(growing = false, duration = duration)
    }
  }

  /**
   * Material container transform: one container morphs into another.
   *
   * On a screen this is the shared element transition and is set through [MotionSpec.sharedElement].
   * Between two sibling views it is applied with [morphInto].
   */
  public data class ContainerTransform(
    @param:ColorInt val scrimColor: Int = Color.TRANSPARENT,
    @param:ColorInt val containerColor: Int = Color.TRANSPARENT,
    val path: MotionPath = MotionPath.Arc,
    val fadeMode: FadeMode = FadeMode.In,
    override val duration: Long = 300L,
  ) : Motion {
    public enum class FadeMode { In, Out, Cross, Through }
  }

  /**
   * Keeps the outgoing screen in place, unchanged, for [duration]. Use it as the exit motion of
   * the screen that a [ContainerTransform] starts from.
   */
  public data class Hold(
    override val duration: Long = 300L,
  ) : Motion
}

/** The path a container follows while its bounds change. */
public enum class MotionPath {
  /** Straight line between start and end bounds. */
  Linear,

  /** Material arc, the recommended path for container transforms. */
  Arc,
}
